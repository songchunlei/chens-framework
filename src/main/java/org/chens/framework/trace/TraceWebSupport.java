package org.chens.framework.trace;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author songchunlei
 * @since 2018/9/17
 */
public class TraceWebSupport {
    public static void setTrace(HttpServletRequest request) {
        if (null == request) {
            return;
        }
        Cookie[] cookies = request.getCookies();
        if (null == cookies || 0 == cookies.length) {
            //TraceContext.set(EntrySourceType.WEB, request.getRequestURL().toString());
            return;
        }
        boolean isPressureTest = false;
        for (Cookie cookie : cookies) {
            if ("online_test".equals(cookie.getName()) && "true".equals(cookie.getValue())) {
                isPressureTest = true;
            }
        }
       //TraceContext.set(EntrySourceType.WEB, request.getRequestURL().toString(), isPressureTest);
    }

    public static void remove() {
        //TraceContext.remove();
    }
}
