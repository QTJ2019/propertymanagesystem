package com.jian.propertymanagesystem.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: qtj
 * @Date: 2021/2/4 0:19
 * @Version
 */
public class HttpServletRequestUtil {
    public static String getString(HttpServletRequest request, String key) {
        try {
            String result = request.getParameter(key);
            if (result != null) {
                //去空格
                result = result.trim();
            }
            if ("".equals(result)) {
                result = null;
            }
            return result;
        } catch (Exception e) {
            return null;
        }

    }
}
