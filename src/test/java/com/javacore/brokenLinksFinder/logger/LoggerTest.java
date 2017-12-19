package com.javacore.brokenLinksFinder.logger;

import com.sun.webkit.dom.XPathNSResolverImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import static org.junit.Assert.*;

public class LoggerTest {
    private static final String MESSAGE = "Hello, world!";
    private static final String ERROR_MESSAGE = "Hello, Error!";

    private ByteArrayOutputStream stream;
    private ByteArrayOutputStream errorStream;
    private Logger logger;

    @Before
    public void setup() {
        stream = new ByteArrayOutputStream();
        errorStream = new ByteArrayOutputStream();

        logger = new Logger();
        logger.setStream(stream);
        logger.setErrorStream(errorStream);
    }

    @Test
    public void canPrintToStream() {
        logger.print(MESSAGE);

        assertEquals(stream.toString(), MESSAGE + "\n");
    }

    @Test
    public void canPrintIfSetEnable() {
        logger.setEnable(false);
        logger.print(MESSAGE);

        assertEquals(stream.toString(), MESSAGE + "\n");
    }

    @Test
    public void canPrintOptionalIfEnable() {
        logger.setEnable(true);
        logger.optionalPrint(MESSAGE);

        assertEquals(stream.toString(), MESSAGE + "\n");
    }

    @Test
    public void notPrintToOptionalIfSetEnable() {
        logger.setEnable(false);
        logger.optionalPrint(MESSAGE);

        assertEquals(stream.toString(), "");
    }

    @Test
    public void notCrashIfStreamIsNull() {
        logger.setStream(null);

        try {
            logger.print(MESSAGE);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void alwaysPrintToErrStream() {
        logger.setEnable(false);
        logger.printErr(ERROR_MESSAGE);

        assertEquals(errorStream.toString(), ERROR_MESSAGE + "\n");
    }
}