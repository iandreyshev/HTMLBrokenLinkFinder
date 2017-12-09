package com.javacore.brokenLinksFinder;

import java.util.HashMap;

public class LinksCodeContainer {
    private String filename;
    private HashMap<String, Integer> codes = new HashMap<>();

    LinksCodeContainer(final String filename) {
        this.filename = filename;
    }

    void add(final String url, int code) {
        codes.put(url, code);
    }

    public HashMap<String, Integer> getCodes() {
        return codes;
    }

    public String getFilename() {
        return filename;
    }
}
