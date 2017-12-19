package com.javacore.brokenLinksFinder;

import com.javacore.brokenLinksFinder.exception.CmdParserException;

import java.util.*;
import java.util.regex.Pattern;

class CommandParser {
    private static final Pattern FLAG_REGEX = Pattern.compile("--[a-zA-Z]+");
    private static final String FLAGS_NOT_ASSIGNED = "Flags not assigned";
    private static final String INVALID_FLAG_FORMAT = "Invalid flag format";
    private static final String FLAG_EXPECTED = "Invalid argument '%s', flag expected";
    private static final String INVALID_ARG_FOR_FLAG = "Invalid argument '%s' for flag '%s'";
    private static final String DUPLICATE_ARGS = "Duplicate flag is not allowed";
    private static final String ARGS_NOT_FOUND_FOR_FLAG = "Arguments for flag '%s' not found";

    private final HashMap<String, List<String>> flagArgs = new HashMap<>();
    private final HashMap<String, Pattern> flagRegex = new HashMap<>();

    void parse(final String[] args) throws CmdParserException {
        if (flagArgs.isEmpty()) {
            throw new CmdParserException(FLAGS_NOT_ASSIGNED);
        }

        cleanup();
        enterParse(args);
    }

    List<String> getArgsForFlag(final String flag) {
        return flagArgs.getOrDefault(flag, new ArrayList<>());
    }

    private CommandParser() {}

    private void enterParse(final String[] args) throws CmdParserException {
        HashSet<String> passedFlags = new HashSet<>();
        int pos = 0;

        while (pos < args.length) {
            final String flag = args[pos];

            if (!isFlag(flag)) {
                throw new CmdParserException(String.format(FLAG_EXPECTED, flag));
            } else if (passedFlags.contains(flag)) {
                throw new CmdParserException(DUPLICATE_ARGS);
            }

            passedFlags.add(flag);
            final boolean isRegexExist = flagRegex.containsKey(flag);
            ++pos;

            while (pos < args.length) {
                final String arg = args[pos];

                if (isFlag(arg)) {
                    break;
                } else if (isRegexExist && !getRegex(flag).matcher(arg).matches()) {
                    throw new CmdParserException(String.format(INVALID_ARG_FOR_FLAG, arg, flag));
                }

                flagArgs.get(flag).add(arg);
                ++pos;
            }
        }
    }

    private Pattern getRegex(final String flag) {
        return flagRegex.get(flag);
    }

    private boolean isFlag(final String flag) {
        return flagArgs.containsKey(flag);
    }

    private void cleanup() {
        for (Map.Entry<String, List<String>> entry : flagArgs.entrySet()) {
            entry.getValue().clear();
        }
    }

    public static class Builder implements javafx.util.Builder<CommandParser> {
        private final CommandParser parser = new CommandParser();

        Builder addFlag(final String flag) throws IllegalArgumentException {
            if (!FLAG_REGEX.matcher(flag).matches()) {
                throw new IllegalArgumentException(INVALID_FLAG_FORMAT);
            }
            parser.flagArgs.put(flag, new ArrayList<>());

            return this;
        }

        Builder addFlag(final String flag, Pattern argsRegex) throws IllegalArgumentException {
            addFlag(flag);
            parser.flagRegex.put(flag, argsRegex);

            return this;
        }

        @Override
        public CommandParser build() {
            return parser;
        }
    }
}
