package com.javacore.brokenLinksFinder.exception;

public class ThreadsException extends FinderException {
    private final static String MESSAGE_TITLE = "ThreadsException: ";

    public ThreadsException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return MESSAGE_TITLE + super.getMessage();
    }
}
