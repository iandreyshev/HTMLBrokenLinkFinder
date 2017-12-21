package com.javacore.brokenLinksFinder.exception;

import org.junit.Test;

import static org.junit.Assert.*;

public class ThreadsExceptionTest {
    private static final String MESSAGE = "Message";
    private static final String FULL_MESSAGE = ThreadsException.class.getSimpleName() + ": " + MESSAGE;

    @Test
    public void exceptionReturnCorrectMessage() {
        try {
            throw new ThreadsException(MESSAGE);
        } catch (ThreadsException ex) {
            assertEquals(FULL_MESSAGE, ex.getMessage());
        }
    }
}