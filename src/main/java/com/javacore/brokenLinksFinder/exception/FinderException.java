package com.javacore.brokenLinksFinder.exception;

public abstract class FinderException extends Exception {
    private Exception instance;
    private String message;

    FinderException(final String message) {
        this.message = message;
    }
    FinderException(final Exception instance, final String message) {
        this.instance = instance;
        this.message = message;
    }

    public Exception getInstance() {
        return instance;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

