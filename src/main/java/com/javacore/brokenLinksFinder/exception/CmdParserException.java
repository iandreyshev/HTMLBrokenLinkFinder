package com.javacore.brokenLinksFinder.exception;

public class CmdParserException extends FinderException {
    private final static String MESSAGE_TITLE = "CmdParserException: ";

    public CmdParserException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return MESSAGE_TITLE + super.getMessage();
    }
}
