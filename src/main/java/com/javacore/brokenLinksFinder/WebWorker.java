package com.javacore.brokenLinksFinder;

import okhttp3.*;

final class WebWorker {
    private static final int BAD_REQUEST = 400;

    static int getCode(final String urlString) {
        final OkHttpClient client = new OkHttpClient();
        final HttpUrl url = HttpUrl.parse(urlString);

        if (url == null) {
            return BAD_REQUEST;
        }

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            return client.newCall(request)
                    .execute()
                    .code();
        } catch (Exception ex) {
            return BAD_REQUEST;
        }
    }
}
