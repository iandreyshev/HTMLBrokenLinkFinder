package com.javacore.brokenLinksFinder;

import okhttp3.*;

final class HttpCodeHandler {
    private int invalidUrlCode;
    private int responseErrorCode;

    int getCode(final String urlString) {
        final OkHttpClient client = new OkHttpClient();
        final HttpUrl url = HttpUrl.parse(urlString);

        if (url == null) {
            return invalidUrlCode;
        }

        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.code();
        } catch (Exception ex) {
            return responseErrorCode;
        }
    }

    private HttpCodeHandler() {}

    public static class Builder implements javafx.util.Builder<HttpCodeHandler> {
        private final HttpCodeHandler handler = new HttpCodeHandler();

        Builder setInvalidUrlCode(int value) {
            handler.invalidUrlCode = value;

            return this;
        }

        Builder setResponseErrorCode(int value) {
            handler.responseErrorCode = value;

            return this;
        }

        @Override
        public HttpCodeHandler build() {
            return handler;
        }
    }
}
