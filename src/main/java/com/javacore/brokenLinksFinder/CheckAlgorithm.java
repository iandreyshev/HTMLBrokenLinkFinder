package com.javacore.brokenLinksFinder;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

class CheckAlgorithm implements Callable<Container> {
    private Container container;
    private List<String> links;

    CheckAlgorithm(final String filename, final List<String> links) {
        container = new Container(filename);
        this.links = links;
    }

    @Override
    public Container call() throws Exception {
        List<Pair<String, Integer>> result = new ArrayList<>();

        for(final String url : links) {
            final int code = WebWorker.getCode(url);
            container.add(url, code);
        }

        return container;
    }
}
