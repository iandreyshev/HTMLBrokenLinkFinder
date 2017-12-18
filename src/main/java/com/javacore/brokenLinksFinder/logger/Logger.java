package com.javacore.brokenLinksFinder.logger;

import java.io.OutputStream;

public class Logger {
    private OutputStream output = System.out;
    private OutputStream errOutput = System.err;
    private boolean isEnable;

    public final void setStream(final OutputStream stream) {
        output = stream;
    }

    public final void setEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public final void optionalPrint(final String message) {
        optionalPrint(output, message.getBytes());
    }

    public final void optionalPrintln(final String message) {
        optionalPrint(output, (message + "\n").getBytes());
    }

    public final void requiredPrint(final String message) {
        requiredPrint(output, message.getBytes());
    }

    public final void requiredPrintln(final String message) {
        requiredPrint(message + "\n");
    }

    public final void printErr(final String message) {
        requiredPrint(errOutput, (message + "\n").getBytes());
    }

    private void optionalPrint(final OutputStream stream, final byte[] bytes) {
        if (!isEnable) {
            return;
        }
        requiredPrint(stream, bytes);
    }

    private void requiredPrint(final OutputStream stream, final byte[] bytes) {
        try {
            stream.write(bytes);
        } catch (Exception ex) {
            System.err.print(ex.getMessage());
        }
    }
}
