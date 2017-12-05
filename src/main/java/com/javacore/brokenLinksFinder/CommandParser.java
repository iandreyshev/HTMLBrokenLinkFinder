package com.javacore.brokenLinksFinder;

import java.util.*;
import java.util.regex.Pattern;

public class CommandParser {
    private static final Pattern FLAG_REGEX = Pattern.compile("--[a-zA-Z]+");

    private HashMap<String, List<String>> flagArgs;
    private HashMap<String, Pattern> flagRegex;
    private String errorMessage;

    public CommandParser() {
        flagArgs = new HashMap<>();
        flagRegex = new HashMap<>();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public CommandParser addFlag(final String flag) throws IllegalArgumentException {
        if (!FLAG_REGEX.matcher(flag).matches()) {
            throw new IllegalArgumentException("Invalid flag format");
        }
        flagArgs.put(flag, new ArrayList<>());

        return this;
    }

    public CommandParser addFlag(final String flag, Pattern argsRegex) throws IllegalArgumentException {
        addFlag(flag);
        flagRegex.put(flag, argsRegex);

        return this;
    }

    public boolean parse(final String[] args) throws IllegalStateException {
        if (flagArgs.isEmpty()) {
            throw new IllegalStateException("Flags not assigned");
        }

        cleanup();
        return enterParse(args);
    }

    public List<String> getArgsForFlag(final String flag) {
        return flagArgs.getOrDefault(flag, new ArrayList<>());
    }

    private boolean enterParse(final String[] args) {
        HashSet<String> passedFlags = new HashSet<>();
        int pos = 0;

        while (pos < args.length) {
            final String flag = args[pos];

            if (!isFlag(flag)) {
                errorMessage = "Invalid arguments: '" + flag + "' is not a flag";
                return false;
            } else if (passedFlags.contains(flag)) {
                errorMessage = "Duplicate flag is not allowed";
                return false;
            }

            passedFlags.add(flag);
            final boolean isRegexExist = flagRegex.containsKey(flag);
            ++pos;

            while (pos < args.length) {
                final String arg = args[pos];

                if (isFlag(arg)) {
                    break;
                }

                if (isRegexExist && !getRegex(flag).matcher(arg).matches()) {
                    errorMessage = "Invalid argument format for '" + flag + "' flag";
                    return false;
                }

                flagArgs.get(flag).add(arg);
                ++pos;
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
