package com.javacore.brokenLinksFinder;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Container {
    private String filename;
    private List<Pair<String, Integer>> codes = new ArrayList<>();

    Container(final String filename) {
        this.filename = filename;
    }

    public void add(final String url, int code) {
        codes.add(new Pair<>(url, code));
    }

    public List<Pair<String, Integer>> getCodes() {
        return codes;
    }
}
