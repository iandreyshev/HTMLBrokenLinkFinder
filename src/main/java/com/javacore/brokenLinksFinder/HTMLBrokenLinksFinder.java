package com.javacore.brokenLinksFinder;

import java.util.List;
import java.util.regex.Pattern;

public class HTMLBrokenLinksFinder {
    private static final String HTML_FILES_FLAG = "--files";
    private static final String REPORT_FILE_FLAG = "--out";
    private static final Pattern HTML_FILE_PATTERN = Pattern.compile("(.+)[.](html)$");
    private static final Pattern REPORT_FILE_PATTERN = Pattern.compile("(.+)[.](csv)$");

    private static List<String> filesToParse;
    private static String reportFile;

    public static void main(String[] args) {
        try {
            readCommandLine(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void readCommandLine(final String[] args) {
        CommandParser parser = new CommandParser()
                .addFlag(HTML_FILES_FLAG, HTML_FILE_PATTERN)
                .addFlag(REPORT_FILE_FLAG, REPORT_FILE_PATTERN)
                .parse(args);

        if (!parser.isSuccess()) {
            throw new IllegalArgumentException(parser.getErrorMessage());
        }

        filesToParse = parser.getArgsForFlag(HTML_FILES_FLAG);
        reportFile = parser.getArgsForFlag(REPORT_FILE_FLAG).get(0);

        for (String value : filesToParse) {
            System.out.println(value);
        }

        System.out.println(reportFile);
    }
}
