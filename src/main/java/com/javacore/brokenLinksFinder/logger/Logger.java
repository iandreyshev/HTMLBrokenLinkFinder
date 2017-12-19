package com.javacore.brokenLinksFinder.logger;

import java.io.OutputStream;

public class Logger {
    private final static String ERROR_PRINT_PATTERN = "Can not print to stream '%s'";
    private OutputStream stream = System.out;
    private OutputStream errorStream = System.err;
    private boolean isEnable = true;

    public final void setStream(final OutputStream stream) {
        this.stream = stream;
    }

    public final void setErrorStream(final OutputStream stream) {
        errorStream = stream;
    }

    public final void setEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public final void print(final String message) {
        print(stream, message);
    }

    public final void optionalPrint(final String message) {
        optionalPrint(stream, message);
    }

    public final void printErr(final String message) {
        print(errorStream, message);
    }

    private void optionalPrint(final OutputStream stream, final String message) {
        if (!isEnable) {
            return;
        }
        print(stream, message);
    }

    private void print(final OutputStream stream, final String message) {
        try {
            stream.write((message + "\n").getBytes());
        } catch (Exception ex) {
            System.err.println(String.format(
                    ERROR_PRINT_PATTERN,
                    stream)
            );
        }
    }
}
