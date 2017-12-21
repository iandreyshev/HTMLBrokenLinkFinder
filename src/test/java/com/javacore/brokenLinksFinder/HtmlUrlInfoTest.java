package com.javacore.brokenLinksFinder;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HtmlUrlInfoTest {
    private static final String FILENAME = "file";
    private static final String URL = "url";
    private static final int DEFAULT_CODE = 0;
    private static final int CUSTOM_CODE = 1;
    private HtmlUrlInfo info;

    @Before
    public void setup() {
        info = new HtmlUrlInfo(FILENAME, URL);
    }

    @Test
    public void infoHaveGettersAndSetters() {
        assertEquals(URL, info.getUrl());
        assertEquals(FILENAME, info.getFileName());
        assertEquals(DEFAULT_CODE, info.getCode());

        info.setCode(CUSTOM_CODE);

        assertEquals(CUSTOM_CODE, info.getCode());
    }
}