package com.javacore.brokenLinksFinder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

final class HTMLParser {
    static List<String> getValues(final String html, final Attribute attribute) {
        List<String> result = new ArrayList<>();

        if (html == null) {
            return result;
        }

        Document doc = Jsoup.parse(html);

        for (final Element node : doc.getElementsByAttribute(attribute.toString())) {
            result.add(node.attr(attribute.toString()));
        }

        return result;
    }

    public enum Attribute {
        HREF("href"),
        SRC("src");

        private final String string;

        Attribute(final String string) {
            this.string = string;
        }

        @Override
        public String toString() {
            return string;
        }
    }
}
