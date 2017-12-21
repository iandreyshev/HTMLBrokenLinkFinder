package com.javacore.brokenLinksFinder.logger;

import com.javacore.brokenLinksFinder.exception.FinderException;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.*;

public class HtmlBrokenLinksFinderLoggerTest {
    private final static String CSV_SEPARATOR = ";";
    private HtmlBrokenLinksFinderLogger logger;
    private ByteArrayOutputStream stream;

    @Before
    public void setup() {
        stream = new ByteArrayOutputStream();
        logger = new HtmlBrokenLinksFinderLogger(stream);
    }

    @Test
    public void printExceptionMessageToStream() {
        final String exMessage = "Exception";
        final String exTitle = "Title";

        logger.exception(new FinderException(exMessage) {
            @Override
            public String getMessageTitle() {
                return exTitle;
            }
        });

        assertEquals((exTitle + ": " + exMessage + "\n"), stream.toString());
    }

    @Test
    public void reportInCsvFormat() {
        final String url = "url_string";
        final Integer code = 0;

        logger.report(url, code);

        assertEquals((url + CSV_SEPARATOR + code.toString() + "\n"), stream.toString());
    }
}