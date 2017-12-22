package com.javacore.brokenLinksFinder.exception;

import org.junit.Test;

import static org.junit.Assert.*;

public class PropsHelperExceptionTest {
    private static final String MESSAGE = "Message";
    private static final String FULL_MESSAGE = PropsHelperException.class.getSimpleName() + ": " + MESSAGE;

    @Test
    public void exceptionReturnCorrectMessage() {
        try {
            throw new PropsHelperException(MESSAGE);
        } catch (PropsHelperException ex) {
            assertEquals(FULL_MESSAGE, ex.getMessage());
        }
    }
}
