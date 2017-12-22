package com.javacore.brokenLinksFinder.exception;

public class PropsHelperException extends FinderException {
    public PropsHelperException(String message) {
        super(message);
    }

    @Override
    protected String getMessageTitle() {
        return getClass().getSimpleName();
    }
}
