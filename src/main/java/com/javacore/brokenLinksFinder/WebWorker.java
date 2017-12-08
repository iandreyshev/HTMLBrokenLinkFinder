package com.javacore.brokenLinksFinder;

import okhttp3.*;

final class WebWorker {
    static int getCode(final String urlString) {
        final OkHttpClient client = new OkHttpClient();
        final HttpUrl url = HttpUrl.parse(urlString);

        if (url == null) {
            return 0;
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
            return 0;
        }
    }
}
