package com.javacore.brokenLinksFinder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.regex.Pattern;

public class CommandParserTest {
    private static final String validFlagOne = "--flagOne";
    private static final String validFlagTwo = "--flagTwo";
    private static final String invalidFlag = "flag";
    private CommandParser parser;

    @Before
    public void updateParser() {
        parser = new CommandParser();
    }

    @Test
    public void getResultsFromNotCompletedParser() {
        parser.addFlag(validFlagOne);
        Assert.assertEquals(parser.getArgsForFlag(validFlagOne).size(), 0);
    }

    @Test
    public void getResultForCompleteParserWithOneFlagAndZeroArgs() {
        final String[] args = {validFlagOne};
        parser.addFlag(validFlagOne);
        parser.parse(args);

        final List<String> result = parser.getArgsForFlag(validFlagOne);
        Assert.assertEquals(result.size(), 0);
    }

    @Test
    public void getResultForCompleteParserWithOneFlagAndTwoArgs() {
        final String firstArg = "arg1";
        final String secondArg = "arg2";
        final String[] args = {validFlagOne, firstArg, secondArg };
        parser.addFlag(validFlagOne);
        parser.parse(args);

        final List<String> result = parser.getArgsForFlag(validFlagOne);
        Assert.assertEquals(result.size(), 2);
        Assert.assertEquals(result.get(0), firstArg);
        Assert.assertEquals(result.get(1), secondArg);
    }

    @Test
    public void getResultForCompleteParserWithTwoFlagsAndTwoArgs() {
        final String firstArg = "arg1";
        final String secondArg = "arg2";
        final String[] args = {validFlagOne, firstArg, secondArg, validFlagTwo, secondArg, firstArg };
        parser.addFlag(validFlagOne).addFlag(validFlagTwo);
        parser.parse(args);

        List<String> result = parser.getArgsForFlag(validFlagOne);
        System.out.println(result.size());
        Assert.assertEquals(result.size(), 2);
        Assert.assertEquals(result.get(0), firstArg);
        Assert.assertEquals(result.get(1), secondArg);

        result = parser.getArgsForFlag(validFlagTwo);
        Assert.assertEquals(result.size(), 2);
        Assert.assertEquals(result.get(0), secondArg);
        Assert.assertEquals(result.get(1), firstArg);
    }

    @Test
    public void failedToParseArgsWithDuplicateFlags() {
        final String[] args = {validFlagOne, "arg1", validFlagOne, "arg2" };
        parser.addFlag(validFlagOne);

        Assert.assertEquals(parser.parse(args), false);
        Assert.assertEquals("Duplicate flag is not allowed", parser.getErrorMessage());
    }

    @Test
    public void failedToParseArgsWithWithPattern() {
        final String[] args = {validFlagOne, "valid1", "valid2", "invalid" };
        parser.addFlag(validFlagOne, Pattern.compile("valid[0-9]"));

        Assert.assertEquals(parser.parse(args), false);
    }

    @Test
    public void failedToParseArgsWithoutFirstFlag() {
        final String[] args = { "arg1", "arg2", "arg3" };
        parser.addFlag(validFlagOne);
        Assert.assertEquals(parser.parse(args), false);
    }

    @Test (expected = IllegalArgumentException.class)
    public void catchExceptionWhenTryAddFlagWithInvalidFormat() {
        parser.addFlag(invalidFlag);
    }

    @Test (expected = IllegalStateException.class)
    public void catchExceptionIfParseWithoutAssignedFlags() {
        final String[] args = {validFlagOne, "arg1" };
        parser.parse(args);
    }
}
