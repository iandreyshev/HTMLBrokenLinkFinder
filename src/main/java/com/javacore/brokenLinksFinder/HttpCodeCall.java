package com.javacore.brokenLinksFinder;

import java.util.concurrent.Callable;

class HttpCodeCall implements Callable<HtmlUrlInfo> {
    private final HtmlUrlInfo container;

    HttpCodeCall(final String fileName, final String url) {
        this.container = new HtmlUrlInfo(fileName, url);
    }

    @Override
    public HtmlUrlInfo call() throws Exception {
        container.setCode(new HttpCodeHandler.Builder()
                .setInvalidUrlCode(0)
                .setResponseErrorCode(0)
                .build()
                .getCode(container.getUrl())
        );

        return container;
    }
}
