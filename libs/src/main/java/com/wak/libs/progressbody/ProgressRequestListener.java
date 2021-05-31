package com.wak.libs.progressbody;

/**
 * Created by JiangKe on 2018/2/11.
 */

public interface ProgressRequestListener {
    void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}
