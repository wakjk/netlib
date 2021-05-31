package com.wak.libs.utils;

import android.annotation.SuppressLint;
import android.util.Log;

/**
 * Created by LiTao on 2015-12-11-11:12.
 * Company: xiaonongding
 * Email: 14846869@qq.com
 * WebSite: http://lixiaopeng.top
 */
public class LogUtil {
    private static final String LOG_TAG = LogUtil.class.getSimpleName();
    public final static int VERBOSE_LEVEL = 0;
    public final static int INFO_LEVEL = 1;
    public final static int WARN_LEVEL = 2;
    public final static int DEBUG_LEVEL = 3;
    public final static int ERROR_LEVEL = 4;


    private final static int LOG_LEVEL = 4;
    
    @SuppressLint("LongLogTag")
    public static final void v(String verbose) {
        if (LOG_LEVEL >= VERBOSE_LEVEL) {
            Log.v(LOG_TAG, verbose);
        }
    }

    @SuppressLint("LongLogTag")
    public static final void v(Class clazz, String verbose) {
        if (LOG_LEVEL >= VERBOSE_LEVEL) {
            Log.v(LOG_TAG, ">>>>>>>>>>>" + clazz.getSimpleName() + " " + verbose);
        }
    }

    public static final void v(String tag, String verbose) {
        if (LOG_LEVEL >= INFO_LEVEL) {
            Log.i(tag, verbose);
        }
    }

    @SuppressLint("LongLogTag")
    public static final void i(String info) {
        if (LOG_LEVEL >= INFO_LEVEL) {
            Log.i(LOG_TAG, info);
        }
    }

    @SuppressLint("LongLogTag")
    public static final void i(Class clazz, String info) {
        if (LOG_LEVEL >= INFO_LEVEL) {
            Log.i(LOG_TAG, ">>>>>>>>>>>" + clazz.getSimpleName() + " " + info);
        }
    }

    public static final void i(String tag, String info) {
        if (LOG_LEVEL >= INFO_LEVEL) {
            Log.i(tag, info);
        }
    }


    @SuppressLint("LongLogTag")
    public static final void w(String warn) {
        if (LOG_LEVEL >= WARN_LEVEL) {
            Log.d(LOG_TAG, warn);
        }
    }

    @SuppressLint("LongLogTag")
    public static final void w(Class clazz, String warn) {
        if (LOG_LEVEL >= WARN_LEVEL) {
            Log.i(LOG_TAG, ">>>>>>>>>>>" + clazz.getSimpleName() + " " + warn);
        }
    }

    public static final void w(String tag, String warn) {
        if (LOG_LEVEL >= WARN_LEVEL) {
            Log.d(tag, warn);
        }
    }


    @SuppressLint("LongLogTag")
    public static final void d(String debug) {
        if (LOG_LEVEL >= DEBUG_LEVEL) {
            Log.d(LOG_TAG, debug);
        }
    }

    @SuppressLint("LongLogTag")
    public static final void d(Class clazz, String debug) {
        if (LOG_LEVEL >= DEBUG_LEVEL) {
            Log.d(LOG_TAG, ">>>>>>>>>>>" + clazz.getSimpleName() + " " + debug);
        }
    }

    public static final void d(String tag, String debug) {
        if (LOG_LEVEL >= DEBUG_LEVEL) {
            Log.d(tag, debug);
        }
    }


    @SuppressLint("LongLogTag")
    public static final void e(String error) {
        if (LOG_LEVEL >= ERROR_LEVEL) {
            Log.e(LOG_TAG, error);
        }
    }

    @SuppressLint("LongLogTag")
    public static final void e(String error, Throwable t) {
        if (LOG_LEVEL >= ERROR_LEVEL) {
            Log.e(LOG_TAG, error, t);
        }
    }

    @SuppressLint("LongLogTag")
    public static final void e(Class clazz, String error, Throwable t) {
        if (LOG_LEVEL >= ERROR_LEVEL) {
            Log.e(LOG_TAG, ">>>>>>>>>>>" + clazz.getSimpleName() + " " + error, t);
        }
    }

    @SuppressLint("LongLogTag")
    public static final void e(Class clazz, String error) {
        if (LOG_LEVEL >= ERROR_LEVEL) {
            Log.e(LOG_TAG, ">>>>>>>>>>>" + clazz.getSimpleName() + " " + error);
        }
    }

    public static final void e(String tag, String error) {
        if (LOG_LEVEL >= ERROR_LEVEL) {
            Log.e(tag, error);
        }
    }

    public static final void e(String tag, String error, Throwable t) {
        if (LOG_LEVEL >= ERROR_LEVEL) {
            Log.e(tag, error, t);
        }
    }

}
