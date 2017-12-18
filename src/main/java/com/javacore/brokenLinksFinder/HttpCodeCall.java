package com.javacore.brokenLinksFinder;

import java.util.List;
import java.util.concurrent.Callable;

class HttpCodeCall implements Callable<HttpCodeContainer> {
    private final HttpCodeContainer container;
    private final List<String> links;

    HttpCodeCall(final String filename, final List<String> links) {
        container = new HttpCodeContainer(filename);
        this.links = links;
    }

    @Override
    public HttpCodeContainer call() throws Exception {
        for (final String url : links) {
            final int code = new HttpCodeHandler.Builder()
                    .setInvalidUrlCode(0)
                    .setResponseErrorCode(0)
                    .build()
                    .getCode(url);

            container.add(url, code);
        }

        return container;
    }
}
