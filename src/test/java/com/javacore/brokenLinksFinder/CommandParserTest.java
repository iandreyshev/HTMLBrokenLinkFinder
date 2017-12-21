package com.javacore.brokenLinksFinder;

import com.javacore.brokenLinksFinder.exception.CmdParserException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public class CommandParserTest {
    private static final String validFlagOne = "--flagOne";
    private static final String validFlagTwo = "--flagTwo";
    private static final String invalidFlag = "flag";
    private CommandParser validWithOneFlag;
    private CommandParser validWithTwoFlags;
    private CommandParser validWithOneFlagAndNumberPattern;

    @Before
    public void updateParser() throws CmdParserException {
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

        try {
            validWithOneFlag.parse(args);
        } catch (CmdParserException ex) {
            fail();
        }
    }

    @Test
    public void getResultForCompleteParserWithOneFlagAndZeroArgs() throws CmdParserException {
        final String[] args = { validFlagOne };
        validWithOneFlag.parse(args);

        assertTrue(validWithOneFlag.getArgsForFlag(validFlagOne).isEmpty());
    }

    @Test
    public void getResultForCompleteParserWithOneFlagAndTwoArgs() {
        final String firstArg = "arg1";
        final String secondArg = "arg2";
        final String[] args = {validFlagOne, firstArg, secondArg };

        try {
            validWithOneFlag.parse(args);
        } catch (Exception ex) {
            fail();
        }
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

        try {
            validWithTwoFlags.parse(args);
        } catch (Exception ex) {
            fail();
        }

        List<String> result = validWithTwoFlags.getArgsForFlag(validFlagOne);

        assertEquals(result.size(), 2);
        assertEquals(result.get(0), firstArg);
        assertEquals(result.get(1), secondArg);

        result = validWithTwoFlags.getArgsForFlag(validFlagTwo);
        assertEquals(result.size(), 2);
        assertEquals(result.get(0), secondArg);
        assertEquals(result.get(1), firstArg);
    }

    @Test (expected = CmdParserException.class)
    public void failedToParseArgsWithDuplicateFlags() throws CmdParserException {
        final String[] args = {validFlagOne, "arg1", validFlagOne, "arg2" };
        validWithOneFlag.parse(args);
    }

    @Test (expected = CmdParserException.class)
    public void failedToParseArgsWithWithPattern() throws CmdParserException {
        final String[] args = {validFlagOne, "valid1", "valid2", "invalid" };
        validWithOneFlagAndNumberPattern.parse(args);
    }

    @Test (expected = CmdParserException.class)
    public void throwExceptionIfParseArgsWithoutFlagAtFirstPosition() throws CmdParserException {
        final String[] args = { "arg1", "arg2", "arg3" };
        validWithOneFlag.parse(args);
    }

    @Test (expected = CmdParserException.class)
    public void throwExceptionIfTryAddFlagWithInvalidFormat() throws CmdParserException {
        new CommandParser.Builder()
                .addFlag(invalidFlag)
                .build();
    }

    @Test (expected = CmdParserException.class)
    public void throwExceptionIfParseWithoutFlags() throws CmdParserException {
        final String[] args = { "arg1", "arg2", "arg3" };
        final CommandParser parser = new CommandParser.Builder()
                .build();
        parser.parse(args);
    }
}
