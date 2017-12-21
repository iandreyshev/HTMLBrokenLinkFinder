package com.javacore.brokenLinksFinder;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HttpCodeHandlerTest {
    private static final String INVALID_URL = "";
    private static final int INVALID_CODE = 0;
    private static final int RESPONSE_ERROR_CODE = 1;

    private HttpCodeHandler handler;

    @Before
    public void setup() {
        handler = new HttpCodeHandler.Builder()
                .setInvalidUrlCode(INVALID_CODE)
                .build();
    }

    @Test
    public void returnInvalidCodeIfUrlIsInvalid() {
        assertEquals(INVALID_CODE, handler.getCode(INVALID_URL));
    }

    @Test
    public void returnInvalidCodeIfUrlIsNull() {
        assertEquals(INVALID_CODE, handler.getCode(null));
    }
}