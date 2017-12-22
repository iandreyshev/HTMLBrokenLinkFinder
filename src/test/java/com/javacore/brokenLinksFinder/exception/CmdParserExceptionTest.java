package com.javacore.brokenLinksFinder.exception;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CmdParserExceptionTest {
    private static final String MESSAGE = "Message";
    private static final String FULL_MESSAGE = CmdParserException.class.getSimpleName() + ": " + MESSAGE;

    @Test
    public void exceptionReturnCorrectMessage() {
        try {
            throw new CmdParserException(MESSAGE);
        } catch (CmdParserException ex) {
            assertEquals(FULL_MESSAGE, ex.getMessage());
        }
    }
}
