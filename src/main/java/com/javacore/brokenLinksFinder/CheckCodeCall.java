package com.javacore.brokenLinksFinder;

import java.util.List;
import java.util.concurrent.Callable;

class CheckCodeCall implements Callable<LinksCodeContainer> {
    private LinksCodeContainer container;
    private List<String> links;

    CheckCodeCall(final String filename, final List<String> links) {
        container = new LinksCodeContainer(filename);
        this.links = links;
    }

    @Override
    public LinksCodeContainer call() throws Exception {
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
