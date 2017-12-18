package com.javacore.brokenLinksFinder;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.regex.Pattern;

public class CommandParserTest {
    private static final String validFlagOne = "--flagOne";
    private static final String validFlagTwo = "--flagTwo";
    private static final String invalidFlag = "flag";
    private CommandParser validWithOneFlag;
    private CommandParser validWithTwoFlags;
    private CommandParser validWithOneFlagAndNumberPattern;

    @Before
    public void updateParser() {
        validWithOneFlag = new CommandParser.Builder()
                .addFlag(validFlagOne)
                .build();
        validWithTwoFlags = new CommandParser.Builder()
                .addFlag(validFlagOne)
                .addFlag(validFlagTwo)
                .build();
        validWithOneFlagAndNumberPattern = new CommandParser.Builder()
                .addFlag(validFlagOne, Pattern.compile("valid[0-9]"))
                .build();
    }

    @Test
    public void getResultsFromNotCompletedParser() {
        assertTrue(validWithOneFlag.getArgsForFlag(validFlagOne).isEmpty());
    }

    @Test
    public void parseEmptyArgsArray() {
        String[] args = {};
        validWithOneFlag.parse(args);

        assertFalse(validWithOneFlag.isSuccess());
    }

    @Test
    public void getResultForCompleteParserWithOneFlagAndZeroArgs() {
        final String[] args = {validFlagOne};
        validWithOneFlag.parse(args);

        assertTrue(validWithOneFlag.getArgsForFlag(validFlagOne).isEmpty());
    }

    @Test
    public void getResultForCompleteParserWithOneFlagAndTwoArgs() {
        final String firstArg = "arg1";
        final String secondArg = "arg2";
        final String[] args = {validFlagOne, firstArg, secondArg };
        validWithOneFlag.parse(args);
        final List<String> result = validWithOneFlag.getArgsForFlag(validFlagOne);

        assertEquals(result.size(), 2);
        assertEquals(result.get(0), firstArg);
        assertEquals(result.get(1), secondArg);
    }

    @Test
    public void getResultForCompleteParserWithTwoFlagsAndTwoArgs() {
        final String firstArg = "arg1";
        final String secondArg = "arg2";
        final String[] args = {validFlagOne, firstArg, secondArg, validFlagTwo, secondArg, firstArg };
        validWithTwoFlags.parse(args);
        List<String> result = validWithTwoFlags.getArgsForFlag(validFlagOne);

        assertEquals(result.size(), 2);
        assertEquals(result.get(0), firstArg);
        assertEquals(result.get(1), secondArg);

        result = validWithTwoFlags.getArgsForFlag(validFlagTwo);
        assertEquals(result.size(), 2);
        assertEquals(result.get(0), secondArg);
        assertEquals(result.get(1), firstArg);
    }

    @Test
    public void failedToParseArgsWithDuplicateFlags() {
        final String[] args = {validFlagOne, "arg1", validFlagOne, "arg2" };
        validWithOneFlag.parse(args);

        assertFalse(validWithOneFlag.isSuccess());
        assertEquals("Duplicate flag is not allowed", validWithOneFlag.getErrorMessage());
    }

    @Test
    public void failedToParseArgsWithWithPattern() {
        final String[] args = {validFlagOne, "valid1", "valid2", "invalid" };
        validWithOneFlagAndNumberPattern.parse(args);

        assertFalse(validWithOneFlagAndNumberPattern.isSuccess());
    }

    @Test
    public void failedToParseArgsWithoutFirstFlag() {
        final String[] args = { "arg1", "arg2", "arg3" };
        validWithOneFlag.parse(args);

        assertFalse(validWithOneFlag.isSuccess());
    }

    @Test (expected = IllegalArgumentException.class)
    public void catchExceptionWhenTryAddFlagWithInvalidFormat() {
        CommandParser parser = new CommandParser.Builder()
                .addFlag(invalidFlag)
                .build();
    }
}
