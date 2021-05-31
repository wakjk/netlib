package com.wak.libs.utils;

/**
 * Created by JiangKe on 2017/5/4.
 * 字符串判空工具
 */

public final class TextUtil {

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public final static boolean isBlank(String str) {
        if (null == str || str.trim().length() <= 0 || str.toLowerCase().equals("null") || str.toLowerCase().equals("about:blank")) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否不为空
     *
     * @param str
     * @return
     */
    public final static boolean isNotBlank(String str) {
        if (isBlank(str)) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * 判断两个字符串是否相等（非空）
     *
     * @param str
     * @param str1
     * @return
     */
    public final static boolean isEqualNotNull(String str, String str1) {
        if (TextUtil.isBlank(str) || TextUtil.isBlank(str1)) {
            return false;
        }
        if (str.equals(str1)) {
            return true;
        }
        return false;
    }

    public final static boolean isNotEqualNotNull(String str, String str1) {
        if (TextUtil.isBlank(str) || TextUtil.isBlank(str1)) {
            return false;
        }
        if (str.equals(str1)) {
            return false;
        }
        return true;
    }

    /**
     * 判断两个字符串是否相等
     *
     * @param str
     * @param str1
     * @return
     */
    public final static boolean isEqual(String str, String str1) {
        if (TextUtil.isBlank(str) && TextUtil.isBlank(str1)) {
            return true;
        }
        if (TextUtil.isBlank(str) || TextUtil.isBlank(str1)) {
            return false;
        }
        if (str.equals(str1)) {
            return true;
        }
        return false;
    }

    public final static boolean isNotEqual(String str, String str1) {
        return !isEqual(str, str1);
    }

    /**
     * 判断两个字符串是否相等
     *
     * @param str
     * @param str1
     * @return
     */
    public final static boolean isEqualIgnoreCase(String str, String str1) {
        if (TextUtil.isBlank(str) || TextUtil.isBlank(str1)) {
            return false;
        }
        if (str.toLowerCase().equals(str1.toLowerCase())) {
            return true;
        }
        return false;
    }

}
