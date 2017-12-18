package com.javacore.brokenLinksFinder.logger;

import java.io.OutputStream;

public class HtmlBrokenLinksFinderLogger extends Logger {
    private static final String FILE_LINKS_REPORT = "Found %s links in file '%s'";
    private static final String THREADS_COUNT = "Threads count is %s";
    private static final String ERROR_WHEN_PARSE_COMMAND = "Can not parse command\nError message: %s";
    private static final String ERROR_WHEN_READ_FILE = "Error when read file '%s'";
    private static final String REPORT_PATTERN = "%s %s";

    public HtmlBrokenLinksFinderLogger(OutputStream stream) {
        setStream(stream);
    }

    public void optionalMessage(Message message) {
        optionalPrintln(message.toString());
    }

    public void requiredMessage(Message message) {
        requiredPrintln(message.toString());
    }

    public void fileLinks(String fileName, int count) {
        optionalPrintln(String.format(FILE_LINKS_REPORT, count, fileName));
    }

    public void threadsCount(int count) {
        optionalPrintln(String.format(THREADS_COUNT, count));
    }

    public void errorParseCommands(String errorMessage) {
        printErr(String.format(ERROR_WHEN_PARSE_COMMAND, errorMessage));
    }

    public void errorReadFile(String fileName) {
        printErr(String.format(ERROR_WHEN_READ_FILE, fileName));
    }

    public void report(String url, int code) {
        requiredPrintln(String.format(REPORT_PATTERN, url, code));
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
