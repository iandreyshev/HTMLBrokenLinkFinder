package com.javacore.brokenLinksFinder.exception;

public class ThreadsException extends FinderException {
    public ThreadsException(String message) {
        super(message);
    }

    @Override
    protected String getMessageTitle() {
        return getClass().getSimpleName();
    }
}
