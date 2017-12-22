package com.javacore.brokenLinksFinder.exception;

public abstract class FinderException extends Exception {
    private final static String TITLE_PATTERN = "%s: %s";

    private Exception instance;
    private final String message;

    public FinderException(final String message) {
        this.message = message;
    }
    public FinderException(final Exception instance) {
        this.instance = instance;
        this.message = instance.getMessage();
    }

    public Exception getInstance() {
        return instance;
    }

    @Override
    public final String getMessage() {
        return String.format(TITLE_PATTERN, getMessageTitle(), message);
    }

    protected abstract String getMessageTitle();
}
