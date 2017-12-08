package com.javacore.brokenLinksFinder;

import javafx.util.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class HTMLBrokenLinksFinder {
    private static final String HTML_FILES_FLAG = "--files";
    private static final String REPORT_FILE_FLAG = "--out";
    private static final String PROPERTIES_FILE = "properties.cfg";
    private static final String THREADS_COUNT_KEY = "threadsCount";
    private static final Pattern HTML_FILE_PATTERN = Pattern.compile("(.+)[.](html)$");
    private static final Pattern REPORT_FILE_PATTERN = Pattern.compile("(.+)[.](csv)$");
    private static final int MIN_THREADS_COUNT = 1;

    private static HashSet<String> filesToParse = new HashSet<>();
    private static String reportFile;
    private static int threadsCount;

    public static void main(String[] args) {
        try {
            readPropertiesFile();
            readCommandLine(args);
            enterProcess();
            writeReport();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void readPropertiesFile() {
        PropertiesParser parser = new PropertiesParser();

        if (parser.load(PROPERTIES_FILE) || parser.isContainsKey(THREADS_COUNT_KEY)) {
            threadsCount = MIN_THREADS_COUNT;

            return;
        }

        try {
            final int count = Integer.parseInt(parser.get(THREADS_COUNT_KEY));
            threadsCount = Integer.max(count, MIN_THREADS_COUNT);
        } catch (Exception ex) {
            threadsCount = MIN_THREADS_COUNT;
        }
    }

    private static void readCommandLine(final String[] args) {
        final CommandParser parser = new CommandParser()
                .addFlag(HTML_FILES_FLAG, HTML_FILE_PATTERN)
                .addFlag(REPORT_FILE_FLAG, REPORT_FILE_PATTERN)
                .parse(args);

        if (!parser.isSuccess()) {
            throw new IllegalArgumentException(parser.getErrorMessage());
        }

        filesToParse.addAll(parser.getArgsForFlag(HTML_FILES_FLAG));
        reportFile = parser.getArgsForFlag(REPORT_FILE_FLAG).get(0);
    }

    private static void enterProcess() {
        ExecutorService service = Executors.newFixedThreadPool(threadsCount);
    }

    private static void writeReport() {

    }
}
