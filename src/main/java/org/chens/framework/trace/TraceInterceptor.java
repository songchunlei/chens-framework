package org.chens.framework.trace;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 跟踪拦截器 可以使用Spring的依赖注入（DI）进行一些业务操作
 * 同时一个拦截器实例在一个controller生命周期之内可以多次调用
 * 但是缺点是只能对controller请求进行拦截，对其他的一些比如直接访问静态资源的请求则没办法进行拦截处理
 * 
 * @author songchunlei
 * @since 2018/9/17
 */
@Slf4j
public class TraceInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            TraceWebSupport.setTrace(request);
        } catch (Exception e) {
            log.error("TraceInterceptor:preHandle error", e);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        try {
            TraceWebSupport.remove();
        } catch (Exception e) {
            log.error("TraceInterceptor:postHandle error", e);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
