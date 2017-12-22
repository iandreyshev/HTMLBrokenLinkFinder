package com.javacore.brokenLinksFinder.exception;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReportExceptionTest {
    private static final String MESSAGE = "Message";
    private static final String FULL_MESSAGE = ReportException.class.getSimpleName() + ": " + MESSAGE;

    @Test
    public void exceptionReturnCorrectMessage() {
        try {
            throw new ReportException(MESSAGE);
        } catch (ReportException ex) {
            assertEquals(FULL_MESSAGE, ex.getMessage());
        }
    }
}
