package com.javacore.brokenLinksFinder.logger;

import com.javacore.brokenLinksFinder.exception.FinderException;

import java.io.OutputStream;

public class HtmlBrokenLinksFinderLogger extends Logger {
    private static final String REPORT_PATTERN = "%s;%s";

    public HtmlBrokenLinksFinderLogger(final OutputStream stream) {
        setStream(stream);
    }

    public void exception(final FinderException ex) {
        print(ex.getMessage());
    }

    public void report(final String url, int code) {
        print(String.format(REPORT_PATTERN, url, code));
    }
}
