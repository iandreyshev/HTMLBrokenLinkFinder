package com.javacore.brokenLinksFinder;

class HtmlUrlInfo {
    private String fileName;
    private String url;
    private int code;

    HtmlUrlInfo(final String filename, final String url) {
        this.fileName = filename;
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public String getUrl() {
        return url;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
