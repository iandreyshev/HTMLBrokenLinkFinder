package com.javacore.brokenLinksFinder.logger;

import com.javacore.brokenLinksFinder.exception.FinderException;

import java.io.OutputStream;

public class HtmlBrokenLinksFinderLogger extends Logger {
    private static final String REPORT_PATTERN = "%s;%s";

    public HtmlBrokenLinksFinderLogger(OutputStream stream) {
        setStream(stream);
    }

    public void optionalMessage(Message message) {
        optionalPrint(message.toString());
    }

    public void message(Message message) {
        optionalPrint(message.toString());
    }

    public void exception(FinderException ex) {
        print(ex.getMessage());
    }

    public void report(final String url, int code) {
        print(String.format(REPORT_PATTERN, url, code));
    }

    public enum Message {
        ERROR_OPEN_PROPS("Can not open properties file"),
        ERROR_PARSE_THREADS_COUNT("Can not parse threads count"),
        ERROR_CALLS_LIST_IS_EMPTY("Calls list is empty"),
        ERROR_THREADS_WORK("Catch interrupted exception"),
        ERROR_REPORT_FILE_NOT_FOUND("Report file not found"),
        ERROR_CLOSE_REPORT_FILE("Catch exception then close report file"),
        ERROR_TAKE_CALLS_RESULT("Catch exception when get results from future object");

        private final String string;

        Message(final String string) {
            this.string = string;
        }

        @Override
        public String toString() {
            return string;
        }
    }
}
