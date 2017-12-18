package com.javacore.brokenLinksFinder;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

import static com.javacore.brokenLinksFinder.logger.HtmlBrokenLinksFinderLogger.Message.*;

public class HtmlBrokenLinksFinder {
    private static final String HTML_FILES_FLAG = "--files";
    private static final String REPORT_FILE_FLAG = "--out";
    private static final String PROPERTIES_FILE = "properties.cfg";
    private static final String THREADS_COUNT_KEY = "threadsCount";
    private static final Pattern HTML_FILE_PATTERN = Pattern.compile("(.+)[.](html)$");
    private static final Pattern REPORT_FILE_PATTERN = Pattern.compile("(.+)[.](csv)$");
    private static final Integer MIN_THREADS_COUNT = 25;
    private static final HtmlParser.Attribute[] LINK_ATTRIBUTES = {
            HtmlParser.Attribute.HREF,
            HtmlParser.Attribute.SRC
    };

    private static final HtmlBrokenLinksFinderLogger log = new HtmlBrokenLinksFinderLogger(System.out);
    private static final HashSet<String> filesToParse = new HashSet<>();
    private static final List<HttpCodeCall> calls = new ArrayList<>();
    private static List<Future<HttpCodeContainer>> callsResult;
    private static String reportFile;
    private static Integer threadsCount;

    public static void main(String[] args) {
        log.setEnable(true);

        readPropertiesFile();

        if (!readCommandLine(args) || !readHtmlFiles() || !requestCodes()) {
            return;
        }

        writeReport();
    }

    private static void readPropertiesFile() throws IOException {
        final PropertiesHelper parser = new PropertiesHelper(new File(PROPERTIES_FILE));
        threadsCount = parser.getInteger(THREADS_COUNT_KEY);
        if (threadsCount == null) {
            threadsCount = MIN_THREADS_COUNT;
        }

        log.threadsCount(threadsCount);
    }

    private static boolean readCommandLine(final String[] args) {
        final CommandParser parser = new CommandParser.Builder()
                .addFlag(HTML_FILES_FLAG, HTML_FILE_PATTERN)
                .addFlag(REPORT_FILE_FLAG, REPORT_FILE_PATTERN)
                .build();

        if (!parser.parse(args)) {
            log.errorParseCommands(parser.getErrorMessage());

            return false;
        }

        filesToParse.addAll(parser.getArgsForFlag(HTML_FILES_FLAG));
        reportFile = parser.getArgsForFlag(REPORT_FILE_FLAG).get(0);

        return true;
    }

    private static boolean readHtmlFiles() {
        for (final String file : filesToParse) {
            final String html;

            try {
                html = new String(Files.readAllBytes(Paths.get(file)));
            } catch (IOException ex) {
                log.errorReadFile(file);
                continue;
            }

            final List<String> links = HtmlParser.getValues(html, LINK_ATTRIBUTES);
            calls.add(new HttpCodeCall(file, links));
            log.fileLinks(file, links.size());
        }

        if (calls.isEmpty()) {
            log.requiredMessage(ERROR_CALLS_LIST_IS_EMPTY);

            return false;
        }

        return true;
    }

    private static boolean requestCodes() {
        final ExecutorService service = Executors.newFixedThreadPool(threadsCount);

        try {
            callsResult = service.invokeAll(calls);

            return true;

        } catch (InterruptedException ex) {
            log.requiredMessage(ERROR_THREADS_WORK);
            ex.printStackTrace();

            return false;

        } finally {
            service.shutdown();
        }
    }

    private static void writeReport() {
        try (final OutputStream output = new FileOutputStream(reportFile)) {

            for (final Future<HttpCodeContainer> containerFuture : callsResult) {
                try {

                    final HttpCodeContainer container = containerFuture.get();
                    final HtmlBrokenLinksFinderLogger logger = new HtmlBrokenLinksFinderLogger(output);
                    logger.requiredPrintln(container.getFilename());

                    for (final Map.Entry<String, Integer> urlState : container.getCodes().entrySet()) {
                        logger.report(urlState.getKey(), urlState.getValue());
                    }
                    logger.requiredPrintln("\n");

                } catch (Exception ex) {
                    log.requiredMessage(ERROR_TAKE_CALLS_RESULT);
                    ex.printStackTrace();
                }
            }

        } catch (FileNotFoundException ex) {
            log.requiredMessage(ERROR_REPORT_FILE_NOT_FOUND);
            ex.printStackTrace();
        } catch (IOException ex) {
            log.requiredMessage(ERROR_CLOSE_REPORT_FILE);
            ex.printStackTrace();
        }
    }
}
