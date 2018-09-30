package org.chens.framework.trace;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
 * 追踪过滤器 依赖于servlet容器。
 * 在实现上基于函数回调，可以对几乎所有请求进行过滤，但是缺点是一个过滤器实例只能在容器初始化时调用一次。
 * 使用过滤器的目的是用来做一些过滤操作，获取我们想要获取的数据
 * 
 * @author songchunlei
 * @since 2018/9/17
 */
@Slf4j
public class TraceFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

    }

    @Override
    public void destroy() {

    }
}
