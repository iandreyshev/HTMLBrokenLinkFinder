package com.javacore.brokenLinksFinder.exception;

public class CmdParserException extends FinderException {
    public CmdParserException(final String message) {
        super(message);
    }
    public CmdParserException(final Exception ex) {
        super(ex);
    }

    @Override
    protected String getMessageTitle() {
        return getClass().getSimpleName();
    }
}
