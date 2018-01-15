package com.javacore.brokenLinksFinder;

import org.junit.Test;

import static org.junit.Assert.*;

public class HtmlBrokenLinksFinderTest {
    @Test
    public void main() {
        try {
            String[] strings = {"--files", "samples/sample1.html" ,"--out", "log.csv"};
            HtmlBrokenLinksFinder.main(strings);
        } catch (Exception e) {
            fail();
        }
    }

}