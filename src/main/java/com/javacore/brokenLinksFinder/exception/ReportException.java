package com.javacore.brokenLinksFinder.exception;

public class ReportException extends FinderException {
    private final static String MESSAGE_TITLE = "ReportException: ";

    public ReportException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return MESSAGE_TITLE + super.getMessage();
    }
}
