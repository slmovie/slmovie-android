package com.xunlei.downloadlib;

/**
 * Created by 包俊 on 2018/6/15.
 */
public class DownloadException extends Exception {
    DownloadException() {
        super();
    }

    public DownloadException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public DownloadException(String detailMessage) {
        super(detailMessage);
    }

    public DownloadException(Throwable throwable) {
        super(throwable);
    }
}
