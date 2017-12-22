package com.javacore.brokenLinksFinder;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class HTMLParserTest {
    private List<String> list;

    @Before
    public void resetList() {
        list = new ArrayList<>();
    }

    @Test
    public void parserWorkWithNullString() {
        list = HtmlParser.getValues(null, HtmlParser.Attribute.SRC);
        assertEquals(list.size(), 0);
    }

    @Test
    public void parserWorkWithEmptyString() {
        final String html = "";
        list = HtmlParser.getValues(html, HtmlParser.Attribute.SRC);

        assertEquals(list.size(), 0);
    }

    @Test
    public void getValueSrcFromHtml() {
        final String html = "<div><img src=\"value\" /></div>";
        list = HtmlParser.getValues(html, HtmlParser.Attribute.SRC);

        assertEquals(list.size(), 1);
        assertEquals(list.get(0), "value");
    }

    @Test
    public void getValueHrefFromHtml() {
        final String html = "<div><img href=\"value\" /></div>";
        list = HtmlParser.getValues(html, HtmlParser.Attribute.HREF);

        assertEquals(list.size(), 1);
        assertEquals(list.get(0), "value");
    }

    @Test
    public void parserDoesNotGetAttributesFromComments() {
        final String html = "<div><!--<img href=\"value\" />--></div>";
        list = HtmlParser.getValues(html, HtmlParser.Attribute.HREF);

        assertEquals(list.size(), 0);
    }
}
