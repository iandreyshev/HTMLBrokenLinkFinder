package com.javacore.brokenLinksFinder.exception;

public class PropsHelperException extends FinderException {
    private final static String MESSAGE_TITLE = "PropsHelperException: ";

    public PropsHelperException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return MESSAGE_TITLE + super.getMessage();
    }
}
