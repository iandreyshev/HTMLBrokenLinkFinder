package com.javacore.brokenLinksFinder.exception;

import org.junit.Test;

import static org.junit.Assert.*;

public class FinderExceptionTest {
    @Test
    public void returnInstantException() {
        final Exception exception = new Exception("Message");
        final FinderException finderException = new FinderException(exception) {
            @Override
            protected String getMessageTitle() {
                return getClass().getSimpleName();
            }
        };

        assertEquals(exception, finderException.getInstance());
    }
}