package com.wak.libs.progressbody;

/**
 * Created by JiangKe on 2018/2/11.
 * 响应体进度回调接口，比如用于文件下载中
 */

public interface ProgressResponseListener {
    void onResponseProgress(long bytesRead, long contentLength, boolean done);
}
