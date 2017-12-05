package com.javacore.brokenLinksFinder;

import java.util.*;
import java.util.regex.Pattern;

public class CommandParser {
    private static final Pattern FLAG_REGEX = Pattern.compile("--[a-zA-Z]+");
    private static final String FLAGS_NOT_ASSIGNED = "Flags not assigned";
    private static final String INVALID_FLAG_FORMAT = "Invalid flag format";
    private static final String EMPTY_ARGS_ARRAY = "Arguments array is empty";
    private static final String FLAG_EXPECTED = "Invalid argument '%s', flag expected";
    private static final String INVALID_ARG_FOR_FLAG = "Invalid argument '%s' for flag '%s'";
    private static final String DUPLICATE_ARGS = "Duplicate flag is not allowed";
    private static final String ARGS_NOT_FOUND_FOR_FLAG = "Arguments for flag '%s' not found";

    private HashMap<String, List<String>> flagArgs = new HashMap<>();
    private HashMap<String, Pattern> flagRegex = new HashMap<>();
    private String errorMessage;
    private boolean isSuccess;

    public String getErrorMessage() {
        return errorMessage;
    }

    public CommandParser addFlag(final String flag) throws IllegalArgumentException {
        if (!FLAG_REGEX.matcher(flag).matches()) {
            throw new IllegalArgumentException(INVALID_FLAG_FORMAT);
        }
        flagArgs.put(flag, new ArrayList<>());

        return this;
    }

    public CommandParser addFlag(final String flag, Pattern argsRegex) throws IllegalArgumentException {
        addFlag(flag);
        flagRegex.put(flag, argsRegex);

        return this;
    }

    public CommandParser parse(final String[] args) throws IllegalStateException {
        if (flagArgs.isEmpty()) {
            throw new IllegalStateException(FLAGS_NOT_ASSIGNED);
        }

        cleanup();
        errorMessage = EMPTY_ARGS_ARRAY;
        isSuccess = args.length > 0 && enterParse(args);

        return this;
    }

    public List<String> getArgsForFlag(final String flag) {
        return flagArgs.getOrDefault(flag, new ArrayList<>());
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    private boolean enterParse(final String[] args) {
        HashSet<String> passedFlags = new HashSet<>();
        int pos = 0;

        while (pos < args.length) {
            final String flag = args[pos];

            if (!isFlag(flag)) {
                errorMessage = String.format(FLAG_EXPECTED, flag);
                return false;
            } else if (passedFlags.contains(flag)) {
                errorMessage = DUPLICATE_ARGS;
                return false;
            }

            passedFlags.add(flag);
            final boolean isRegexExist = flagRegex.containsKey(flag);
            ++pos;

            while (pos < args.length) {
                final String arg = args[pos];

                if (isFlag(arg)) {
                    break;
                } else if (isRegexExist && !getRegex(flag).matcher(arg).matches()) {
                    errorMessage = String.format(INVALID_ARG_FOR_FLAG, arg, flag);
                    return false;
                }

                flagArgs.get(flag).add(arg);
                ++pos;
            }

            if (flagArgs.get(flag).size() < 1) {
                errorMessage = String.format(ARGS_NOT_FOUND_FOR_FLAG, flag);
                return false;
            }
        }

        return true;
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
}
