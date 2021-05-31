package com.wak.libs.utils;

import android.webkit.MimeTypeMap;


import androidx.collection.ArrayMap;

import com.wak.libs.progressbody.ProgressRequestBody;
import com.wak.libs.progressbody.ProgressRequestListener;
import com.wak.libs.progressbody.ProgressResponseBody;
import com.wak.libs.progressbody.ProgressResponseListener;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by JiangKe on 2017/6/5.
 */

public class OKHttpUtils {

    /**
     * 描述
     * <p>
     * 传输过程类型限定：数据类型：byte、char、int、short、long、float、double、boolean、File、String
     * <p>
     * 集合：List
     */

    public static final MediaType CONTENT_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/ic_close_white_circle_x-markdown; charset=utf-8");
    public static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream; charset=utf-8");


    public final static long CONNECT_TIME_OUT = 8 * 1000;
    public final static long READ_TIME_OUT = 8 * 1000;
    public final static long WRITE_TIME_OUT = 8 * 1000;

    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
            .writeTimeout(WRITE_TIME_OUT, TimeUnit.MILLISECONDS)
            .build();

    /**
     * Get 请求
     *
     * @param url
     * @return
     */
    public static Response reqGet(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        LogUtil.i("Test", "url:" + url);
        return requestFromNetwork(request);
    }


    public static Response reqGet(String url, String tag) {
        Request request = new Request.Builder()
                .url(url)
                .tag(tag)
                .build();
        LogUtil.i("Test", "url:" + url);
        return requestFromNetwork(request);
    }

    /**
     * 同步Post请求 该不会开启异步线程。
     *
     * @param url
     * @param json 参数字符串
     * @return
     * @throws IOException
     */
    public static Response reqPost(String url, String json) {
        LogUtil.i("url:" + url);
        LogUtil.i("param:" + json);
        RequestBody body = RequestBody.create(CONTENT_TYPE, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return requestFromNetwork(request);
    }


    /**
     * 同步Post请求 该不会开启异步线程。
     *
     * @param url
     * @param json 参数字符串
     * @return
     * @throws IOException
     */
    public static Response reqPost(String url, JSONObject json) {
        LogUtil.i("url:" + url);
        LogUtil.i("param:" + json);
        RequestBody body = RequestBody.create(CONTENT_TYPE, json.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return requestFromNetwork(request);
    }


    /**
     * post请求 发送键值对 Form 请求
     *
     * @param url
     * @param map
     * @return
     * @throws IOException
     */
    public static Response reqPost(String url, ArrayMap<String, Object> map) {
        if (null == map || map.size() <= 0) {
            return null;
        }
        RequestBody formBody = getRequestBody(map);
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        LogUtil.i("Test", "url:" + url);
        LogUtil.i("Test", "params:" + getParamString(map));
        return requestFromNetwork(request);
    }

    /**
     * post文件
     *
     * @param url
     * @param files
     * @return
     * @throws IOException
     */
    public static Response postFile(String url, File... files) {
        if (null == files || files.length <= 0) {
            return null;
        }
        LogUtil.i("Test", "url:" + url);
        MultipartBody.Builder requestBodybuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (null != files) {
            for (File file : files) {
                addFileParams(requestBodybuilder, file, "file");
            }
        }
        //主请求
        RequestBody requestBody = requestBodybuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return requestFromNetwork(request);
    }

    /**
     * post文件
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static Response postFile(String url, ArrayMap<String, Object> map, String fileName) {
        LogUtil.i("Test", "url:" + url);
        if (null == map || map.size() <= 0) {
            return null;
        }
        //主请求
        RequestBody requestBody = getRequestBody(map, fileName);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return requestFromNetwork(request);
    }

    /**
     * @param url                     请求地址
     * @param map                     请求参数
     * @param progressRequestListener 上传进度监听器
     * @return
     */
    public static Response postFile(String url, ArrayMap<String, Object> map, ProgressRequestListener progressRequestListener) {
        LogUtil.i("Test", "url:" + url);

        if (null == map || map.size() <= 0) {
            return null;
        }
        //主请求
        RequestBody requestBody = getRequestBody(map);
        ProgressRequestBody progressRequestBody = new ProgressRequestBody(requestBody, progressRequestListener);
        Request request = new Request.Builder()
                .url(url)
                .post(progressRequestBody)
                .build();
        return requestFromNetwork(request);
    }

    /**
     * post图片
     *
     * @param url
     * @param files
     * @return
     */
    public static Response postPicture(String url, File... files) {
        LogUtil.i("Test", "url:" + url);
        if (null == files || files.length <= 0) {
            return null;
        }
        MultipartBody.Builder requestBodybuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (null != files) {
            for (File file : files) {
                addFileParams(requestBodybuilder, file, "Picture");
            }
        }
        //主请求
        RequestBody requestBody = requestBodybuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return requestFromNetwork(request);
    }

    /**
     * 请求网络
     *
     * @param request
     * @return
     */
    private static Response requestFromNetwork(Request request) {
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            LogUtil.e(e.toString());
        }
        return response;
    }

    /**
     * 将hashMap中的数据封装到 RequestBody中
     *
     * @param map
     * @return
     */
    private static RequestBody getRequestBody(ArrayMap<String, Object> map, String fileName) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            if (null != entry.getValue()) {
                if (entry.getValue() instanceof List) {
                    addList(builder, (List) entry.getValue(), String.valueOf(entry.getKey()));
                } else if (entry.getValue() instanceof Object[]) {
                    addArray(builder, (Object[]) entry.getValue(), String.valueOf(entry.getKey()));
                } else if (entry.getValue() instanceof File) {
                    addFileParams(builder, (File) entry.getValue(), fileName);
                } else {
                    builder.addFormDataPart(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                }
            }
        }
        return builder.build();
    }

    /**
     * 将hashMap中的数据封装到 RequestBody中
     *
     * @param map
     * @return
     */
    private static RequestBody getRequestBody(ArrayMap<String, Object> map) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            if (null != entry.getValue()) {
                if (entry.getValue() instanceof List) {
                    addList(builder, (List) entry.getValue(), String.valueOf(entry.getKey()));
                } else if (entry.getValue() instanceof Object[]) {
                    addArray(builder, (Object[]) entry.getValue(), String.valueOf(entry.getKey()));
                } else if (entry.getValue() instanceof File) {
                    addFileParams(builder, (File) entry.getValue(), "avatar");
                } else {
                    builder.addFormDataPart(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                }
            }
        }
        return builder.build();
    }

    private static void addList(MultipartBody.Builder builder, List list, String key) {
        for (Object o : list) {
            if (o instanceof List) {
                addList(builder, (List) o, key);
            }
            if (o instanceof Object[]) {
                addArray(builder, (Object[]) o, key);
            }
            if (o instanceof File) {
                addFileParams(builder, (File) o, String.valueOf(key));
            } else {
                builder.addFormDataPart(String.valueOf(key), String.valueOf(o));
            }
        }
    }

    private static void addArray(MultipartBody.Builder builder, Object[] array, String key) {
        for (Object o : array) {
            if (o instanceof List) {
                addList(builder, (List) o, key);
            }
            if (o instanceof Object[]) {
                addArray(builder, (Object[]) o, key);
            }
            if (o instanceof File) {
                addFileParams(builder, (File) o, String.valueOf(key));
            } else {
                builder.addFormDataPart(String.valueOf(key), String.valueOf(o));
            }
        }
    }

    private static void addFileParams(MultipartBody.Builder builder, File file, String paramsKey) {
        String mimetype = getMimeType(file);
        RequestBody body = RequestBody.create(MediaType.parse(mimetype), file);
        try {
            builder.addFormDataPart(paramsKey, URLEncoder.encode(file.getName(), "utf-8"), body);
        } catch (Exception e) {
            builder.addFormDataPart(paramsKey, new Date().getTime() + "", body);
        }
    }

    /**
     * 将参数连接成字符串
     *
     * @param map
     * @return
     */
    public static String getParamString(ArrayMap<String, Object> map) {
        StringBuffer params = new StringBuffer();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry element = (Map.Entry) it.next();
            params.append(element.getKey());
            params.append("=");
            params.append(element.getValue());
            params.append("#");
        }
        if (params.length() > 0) {
            params.deleteCharAt(params.length() - 1);
        }
        return params.toString();
    }

    public static String getMimeType(File file) {
        String extension = getExtension(file);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    /**
     * 获取文件类型
     *
     * @param file
     * @return
     */
    private static String getExtension(File file) {
        String suffix = "";
        String name = file.getName();
        final int idx = name.lastIndexOf(".");
        if (idx > 0) {
            suffix = name.substring(idx + 1);
        }
        return suffix;
    }

    /**
     * 取消某个请求
     *
     * @param tag
     */
    public static void cancalReq(String tag) {
        for (Call call : client.dispatcher().runningCalls()) {
            if (call.request().tag().equals(tag)) {
                call.cancel();
            }
        }
        for (Call call : client.dispatcher().queuedCalls()) {
            if (call.request().tag().equals(tag)) {
                call.cancel();
            }
        }
    }

    /**
     * Get 请求
     *
     * @param url
     * @return
     */
    public static Response reqGet(String url, String tag, final ProgressResponseListener progressResponseListener) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.MILLISECONDS)
                .build();
        final Request request = new Request.Builder()
                .url(url)
                .tag(tag)
                .build();
        okHttpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder()
                        .body(new ProgressResponseBody(originalResponse.body(), progressResponseListener))
                        .build();
            }
        });
        LogUtil.i("Test", "url:" + url);
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            LogUtil.e(e.toString());
        }
        return response;
    }

}
