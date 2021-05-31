package com.wak.libs.io;


import okhttp3.Response;

/**
 * Created by JiangKe on 2017/5/4.
 * 网络请求过程回调
 */

public interface NetCallback<T> {

    /**
     * 网络请求开始执行（同步）
     */
    void onStart();

    /**
     * 异步请求中(异步)
     */
    Response doingBackground();

    /**
     * 数据解析
     *
     * @param jsonStr
     * @return
     */
    T onParseJson(String jsonStr);


    /**
     * 数据本地化(异步)
     */
//    void onSaveResult(T result, ArrayMap<String, Object> params);
//    void onSaveResult(T result);

    /**
     * 获取本地数据(异步)
     *
     * @return
     */
    T onGetLocalModel();

    /**
     * 网络请求成功（同步）
     */
//    void onSuccess(T result, ArrayMap<String, Object> params);
    void onSuccess(T result, String msg);

//    /**
//     * 加载信息返回
//     *
//     * @param msg
//     */
//    void onMsg(String msg);

    /**
     * 请求出错（同步）
     *
     * @param msg
     */
    void onFailed(String msg, String status);

    /**
     * 网络请求失败（同步）
     */
    void onError(int resError);

    /**
     * 网络请求完成（同步）
     */
    void onFinish();


}
