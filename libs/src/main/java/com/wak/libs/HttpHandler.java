package com.wak.libs;

import android.os.Handler;
import android.os.Looper;

import com.wak.libs.io.NetProcess;
import com.wak.libs.thread.ThreadManager;
import com.wak.libs.utils.OKHttpUtils;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by JiangKe on 2017/5/5.
 */

/**
 * 网络请求处理器
 */
public class HttpHandler extends Handler {

    public static HttpHandler getInstance() {
        return Holder.Instance;
    }

    private static class Holder {
        private final static HttpHandler Instance = new HttpHandler();
    }

    private HttpHandler() {
        super(Looper.getMainLooper());
        ThreadManager.getInstance();
    }

    public void execute(final NetProcess<?> process) {
        process.onStart();
        ThreadManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                Response response = process.doingBackground();
                String result = null;
                int resultCode = 0;
                if (null != response) {
                    try {
                        result = response.body().string();
                    } catch (final IOException e) {
                        runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                e.printStackTrace();
                            }
                        });
                    }
                    resultCode = response.code();
                }
                process.handleResponse(result, resultCode);
            }
        });
    }

    public void runOnUIThread(Runnable runnable) {
        this.post(runnable);
    }

    /**
     * 取消某个请求
     *
     * @param tag
     */
    public void cancelRequest(String tag) {
        OKHttpUtils.cancalReq(tag);
    }

    /**
     * 取消所有缓存请求
     */
    public void cancelBlockThread() {
        ThreadManager.getInstance().removeBlockTask();
    }

}
