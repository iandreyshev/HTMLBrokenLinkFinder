package com.javacore.brokenLinksFinder;

import com.javacore.brokenLinksFinder.exception.*;
import com.javacore.brokenLinksFinder.logger.HtmlBrokenLinksFinderLogger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

public class HtmlBrokenLinksFinder {
    private static final String HTML_FILES_FLAG = "--files";
    private static final String REPORT_FILE_FLAG = "--out";
    private static final String PROPERTIES_FILE = "properties.cfg";
    private static final String THREADS_COUNT_KEY = "threadsCount";
    private static final String SHOW_LOG_KEY = "showLog";
    private static final Pattern HTML_FILE_PATTERN = Pattern.compile("(.+)[.](html)$");
    private static final Pattern REPORT_FILE_PATTERN = Pattern.compile("(.+)[.](csv)$");
    private static final Integer MIN_THREADS_COUNT = 25;
    private static final HtmlParser.Attribute[] LINK_ATTRIBUTES = {
            HtmlParser.Attribute.HREF,
            HtmlParser.Attribute.SRC
    };

    private static final HtmlBrokenLinksFinderLogger logger = new HtmlBrokenLinksFinderLogger(System.out);
    private static final HashSet<String> filesToParse = new HashSet<>();
    private static final List<HttpCodeCall> calls = new ArrayList<>();
    private static List<Future<HtmlUrlInfo>> callsResult;
    private static String reportFile;
    private static Integer threadsCount = MIN_THREADS_COUNT;

    public static void main(String[] args) {
        try {
            readPropertiesFile();
            readCommandLine(args);
            readHtmlFiles();
            requestCodes();
            writeReport();
        } catch (FinderException ex) {
            logger.exception(ex);
        } catch (Exception ex) {
            logger.print(ex.getMessage());
        }
    }

    private static void readPropertiesFile() throws PropsHelperException {
        final PropertiesHelper parser = new PropertiesHelper(new File(PROPERTIES_FILE));
        threadsCount = parser.getInteger(THREADS_COUNT_KEY);
        logger.setEnable(parser.getBoolean(SHOW_LOG_KEY));
    }

    private static void readCommandLine(final String[] args) throws CmdParserException {
        final CommandParser parser = new CommandParser.Builder()
                .addFlag(HTML_FILES_FLAG, HTML_FILE_PATTERN)
                .addFlag(REPORT_FILE_FLAG, REPORT_FILE_PATTERN)
                .build();

        parser.parse(args);

        filesToParse.addAll(parser.getArgsForFlag(HTML_FILES_FLAG));
        reportFile = parser.getArgsForFlag(REPORT_FILE_FLAG).get(0);
    }

    private static void readHtmlFiles() {
        for (final String file : filesToParse) {
            final String html;

            try {
                html = new String(Files.readAllBytes(Paths.get(file)));
            } catch (Exception ex) {
                continue;
            }

            for (final String url : HtmlParser.getValues(html, LINK_ATTRIBUTES)) {
                calls.add(new HttpCodeCall(file, url));
            }
        }
    }

    private static void requestCodes() throws ThreadsException {
        final ExecutorService service = Executors.newFixedThreadPool(threadsCount);

        try {
            callsResult = service.invokeAll(calls);
        } catch (InterruptedException ex) {
            throw new ThreadsException("Catch InterruptedException during invoke HttpCode calls");
        } finally {
            service.shutdown();
        }
    }

    private static void writeReport() throws ReportException, ThreadsException {
        try (final OutputStream output = new FileOutputStream(reportFile)) {

            for (final Future<HtmlUrlInfo> containerFuture : callsResult) {
                try {
                    final HtmlUrlInfo urlInfo = containerFuture.get();
                    final HtmlBrokenLinksFinderLogger logger = new HtmlBrokenLinksFinderLogger(output);
                    logger.report(urlInfo.getUrl(), urlInfo.getCode());
                } catch (InterruptedException | ExecutionException ex) {
                    throw new ThreadsException("Catch exception during read list of HttpCodes futures");
                }
            }
        } catch (IOException ex) {
            throw new ReportException("Catch close stream exception during report");
        }
    }
}
