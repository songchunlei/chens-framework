package org.chens.framework.urlstat;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * url收集
 */
public class UrlStatInterceptor implements HandlerInterceptor {
    private UrlStatCollector urlStatCollector;

    public void setUrlStatCollector(UrlStatCollector urlStatCollector) {
        this.urlStatCollector = urlStatCollector;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String matchingPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String method = request.getMethod();
        urlStatCollector.onStart(matchingPattern, method);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable Exception ex) throws Exception {
        if (ex == null) {
            urlStatCollector.onFinally();
        } else {
            urlStatCollector.onThrowable(ex);
        }
    }
}
