package com.javacore.brokenLinksFinder;

import java.util.HashMap;

class HttpCodeContainer {
    private final String filename;
    private final HashMap<String, Integer> codes = new HashMap<>();

    HttpCodeContainer(final String filename) {
        this.filename = filename;
    }

    void add(final String url, int code) {
        codes.put(url, code);
    }

    HashMap<String, Integer> getCodes() {
        return codes;
    }

    String getFilename() {
        return filename;
    }
}
