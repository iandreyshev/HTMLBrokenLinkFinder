package com.javacore.brokenLinksFinder.exception;

public class ReportException extends FinderException {
    public ReportException(String message) {
        super(message);
    }

    @Override
    protected String getMessageTitle() {
        return getClass().getSimpleName();
    }
}
