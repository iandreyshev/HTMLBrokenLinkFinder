package com.javacore.brokenLinksFinder.logger;

import com.javacore.brokenLinksFinder.exception.FinderException;

import java.io.OutputStream;

public class HtmlBrokenLinksFinderLogger extends Logger {
    private static final String REPORT_PATTERN = "%s;%s";

    public HtmlBrokenLinksFinderLogger(OutputStream stream) {
        setStream(stream);
    }

    public void exception(FinderException ex) {
        print(ex.getMessage());
        ex.getInstance().printStackTrace();
    }

    public void report(final String url, int code) {
        print(String.format(REPORT_PATTERN, url, code));
    }
}
