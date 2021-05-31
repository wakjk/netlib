package com.wak.libs.model;


import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * Created by JiangKe on 2017/5/5.
 */

public class NetModel {

    public final static String STATUS_SYSEM_ERROR = "00";  //请求失败
    public final static String STATUS_SUCCESS = "01";      //成功
    public final static String STATUS_FAILED = "02";       //失败
    public final static String STATUS_HANDLING = "03";     //处理中
    public final static String STATUS_LOGIN_INVALID = "04";      //登录失效

    public final static String STATUS_ANALYSIS_ERROR = "05"; //数据解析错误
    public final static String STATUS_LOCAL = "06"; //本地缓存数据


//    {"data":{"Phone":"12222222222","Id":"36012913"},"respmsg":"发送短信验证码成功","respcod":"01"}
    /**
     * 返回的数据1
     */
    private JsonElement data;

    /**
     * 提示字符串
     */
    @SerializedName("respmsg")
    private String msg;

    /**
     * 返回状态
     * 1：成功；0：失败
     */
    @SerializedName("respcod")
    private String status;


//    /**
//     * 其他数据
//     */
//    private ArrayMap<String, Object> addition;

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


//    public ArrayMap<String, Object> getAddition() {
//        return addition;
//    }
//
//    public void setAddition(ArrayMap<String, Object> addition) {
//        this.addition = addition;
//    }
}
