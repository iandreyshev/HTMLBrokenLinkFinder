package com.javacore.brokenLinksFinder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

final class HtmlParser {
    static List<String> getValues(final String html, final Attribute... attributes) {
        final List<String> result = new ArrayList<>();

        if (html == null) {
            return result;
        }

        for (final Attribute attribute : attributes) {
            final Document doc = Jsoup.parse(html);
            final Elements nodes = doc.getElementsByAttribute(attribute.toString());

            for (final Element node : nodes) {
                final String value = node.attr(attribute.toString());
                result.add(value);
            }
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
