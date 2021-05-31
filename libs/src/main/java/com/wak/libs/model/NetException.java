package com.wak.libs.model;

/**
 * Created by JiangKe on 2017/5/5.
 */

public class NetException extends Exception {

    public final static int EXCEPTION_LEVEL_A = 1;
    public final static int REQ_CONTINUE = 100;

    public final static int EXCEPTION_LEVEL_B = 2;
    public final static int SUCCESS = 200;

    public final static int EXCEPTION_LEVEL_C = 3;
    public final static int EXCEPTION_REDIRECTION = 300;

    public final static int EXCEPTION_LEVEL_D = 4;
    public final static int EXCEPTION_WRONG_REQ = 400;
    public final static int EXCEPTION_UNAUTH = 401;
    public final static int EXCEPTION_FORBID = 403;
    public final static int EXCEPTION_NOT_FOUND = 404;
    public final static int EXCEPTION_SERVICE_TIMEOUT = 408;

    public final static int EXCEPTION_LEVEL_E = 5;
    public final static int EXCEPTION_SERVER_ERROR = 500;
    public final static int EXCEPTION_SERVER_UNAVAILABLE = 503;

    public final static int EXCEPTION_ILLEGAL = 6;

    /**
     * 错误代码
     */
    public static int RESPONSE_CODE = 999;


    public static int switchException(int code) {

        if (code >= 100 && code < 200) {
            RESPONSE_CODE = REQ_CONTINUE;
            return EXCEPTION_LEVEL_A;
        }

        if (code >= 200 && code < 300) {
            if (code == 200) {
                RESPONSE_CODE = SUCCESS;
            } else {
                RESPONSE_CODE = REQ_CONTINUE;
            }
            return EXCEPTION_LEVEL_B;
        }

        if (code >= 300 && code < 400) {
            RESPONSE_CODE = EXCEPTION_REDIRECTION;
            return EXCEPTION_LEVEL_C;
        }

        if (code >= 400 && code < 500) {
            switch (code) {
                case 400:
                    RESPONSE_CODE = EXCEPTION_WRONG_REQ;
                    break;
                case 401:
                    RESPONSE_CODE = EXCEPTION_UNAUTH;
                    break;
                case 403:
                    RESPONSE_CODE = EXCEPTION_FORBID;
                    break;
                case 404:
                    RESPONSE_CODE = EXCEPTION_NOT_FOUND;
                    break;
                case 408:
                    RESPONSE_CODE = EXCEPTION_SERVICE_TIMEOUT;
                    break;
            }
            return EXCEPTION_LEVEL_D;
        }

        if (code >= 500) {
            switch (code) {
                case 500:
                    RESPONSE_CODE = EXCEPTION_SERVER_ERROR;
                    break;
                case 503:
                    RESPONSE_CODE = EXCEPTION_SERVER_UNAVAILABLE;
                    break;
            }
            return EXCEPTION_LEVEL_E;
        }

        return EXCEPTION_ILLEGAL;

    }
}
