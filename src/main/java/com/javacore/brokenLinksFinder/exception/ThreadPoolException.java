package com.javacore.brokenLinksFinder.exception;

public class ThreadPoolException extends FinderException {
    private final static String MESSAGE_TITLE = "ThreadPoolException: ";

    public ThreadPoolException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return MESSAGE_TITLE + super.getMessage();
    }
}
