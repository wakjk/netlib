package com.wak.libs.io;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wak.libs.HttpHandler;
import com.wak.libs.R;
import com.wak.libs.model.NetException;
import com.wak.libs.model.NetModel;
import com.wak.libs.utils.LogUtil;
import com.wak.libs.utils.TextUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * Created by JiangKe on 2017/5/5.
 */

public abstract class NetProcess<T> implements NetCallback<T> {

    public static final String TAG = "Test";

    /**
     * 请求结果处理
     */
    public final void handleResponse(final String result, int resultCode) {
        LogUtil.d(TAG, "result:" + result);
        int code = NetException.switchException(resultCode);
        switch (code) {
            case NetException.EXCEPTION_LEVEL_B: //异常B级，请求成功
                if (NetException.RESPONSE_CODE == NetException.SUCCESS) {
                    NetModel model = null;
                    if (TextUtil.isNotBlank(result)) {
                        try {
                            //解析数据
                            model = new Gson().fromJson(result, NetModel.class);
                        } catch (final Exception e) {
                            LogUtil.d(TAG, e.toString());
                        }
                    }
                    if (null != model) {
                        //解析成功
                        //"数据"处理
                        handlerModel(model);
                    } else {
                        //解析失败
                        HttpHandler.getInstance().runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                onFailed("数据解析错误", NetModel.STATUS_ANALYSIS_ERROR);
                            }
                        });
                    }
                }
                break;
            case NetException.EXCEPTION_LEVEL_D: //异常判断 D 级，客户端请求出错
            case NetException.EXCEPTION_LEVEL_E: //异常判断 E 级，服务器端出错
            case NetException.EXCEPTION_ILLEGAL: //异常判断不合法
            default:                             //网络请求失败，从本地读取数据
                NetModel localModel = new NetModel();
                localModel.setStatus(NetModel.STATUS_LOCAL);
                handlerModel(localModel);
                break;
        }
        HttpHandler.getInstance().runOnUIThread(new Runnable() {
            @Override
            public void run() {
                onFinish();
            }
        });
    }

    /**
     * 请求"数据"处理
     *
     * @param model
     */
    private void handlerModel(final NetModel model) {
        if (model != null
                && TextUtil.isNotBlank(model.getStatus())) {
            switch (model.getStatus()) {
                case NetModel.STATUS_SUCCESS:
                    //请求成功
                    T t;
                    try {
                        t = onParseJson(model.getData().toString());
                    } catch (Exception e) {
                        LogUtil.d(TAG, e.toString());
                        t = null;
                    }
                    //本地保存，Bean类型
                    final T finalT = t;
                    HttpHandler.getInstance().runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            onSuccess(finalT, model.getMsg());
                        }
                    });
                    break;
                case NetModel.STATUS_LOCAL:
                    //请求失败，应用本地数据
                    T local;
                    try {
                        local = onGetLocalModel();
                    } catch (Exception e) {
                        LogUtil.d(TAG, e.toString());
                        local = null;
                    }
                    if (null != local) {
                        //本地有缓存数据，判定为请求状态出错
                        final T finalLocal = local;
                        HttpHandler.getInstance().runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                onSuccess(finalLocal, "");
                                onFailed(model.getMsg(), model.getStatus());
                            }
                        });
                    } else {
                        //本地无缓存数据，判定为请求状态出错
                        HttpHandler.getInstance().runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                onError(R.string.net_error_null_body);
                            }
                        });
                    }
                    break;
                default:
                    //请求失败，且本地无缓存
                    HttpHandler.getInstance().runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            onFailed(model.getMsg(), model.getStatus());
                        }
                    });
                    break;
            }
        } else {
            HttpHandler.getInstance().runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    onError(R.string.tip_status_code_error);
                }
            });
        }
    }



    public final T fromGson(String jsonStr, Type type) {
        Gson gson = new Gson();
        T t = null;
        try {
            //解析数据
            t = gson.fromJson(jsonStr, type);
        } catch (final Exception e) {
            HttpHandler.getInstance().post(new Runnable() {
                @Override
                public void run() {
                    onError(R.string.tip_json_parse_error);
                }
            });
        }
        return t;
    }

    public final T fromGson(String jsonStr) {
        Gson gson = new Gson();
        T t = null;
        try {
            //解析数据
            t = gson.fromJson(jsonStr, ((ParameterizedType) NetProcess.class.getGenericSuperclass()).getActualTypeArguments()[0]);
        } catch (final Exception e) {
            HttpHandler.getInstance().post(new Runnable() {
                @Override
                public void run() {
                    onError(R.string.tip_json_parse_error);
                }
            });
        }
        return t;
    }

    public final Type getType(TypeToken<T> type) {
        return type.getType();
    }

    @Override
    public void onFailed(String msg, String status) {
    }

    @Override
    public void onError(int resError) {
    }

    @Override
    public T onGetLocalModel() {
        return null;
    }

}
