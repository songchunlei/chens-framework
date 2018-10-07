package org.chens.framework.interceptor;

import org.chens.core.exception.BaseException;
import org.chens.core.util.StringUtils;
import org.chens.framework.annotation.IgnoreAuth;
import org.chens.framework.auth.constants.AuthConstants;
import org.chens.framework.auth.exception.AuthExceptionEnum;
import org.chens.framework.auth.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.security.auth.message.AuthException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户权限拦截器
 * 引用自 https://gitee.com/geek_qi/ace-security,按现有框架做了调整
 * @author songchunlei@qq.com
 * @create 2018/3/21
 */
public class UserAuthRestInterceptor extends HandlerInterceptorAdapter {

    @Autowired(required = false)
    private ITokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 配置该注解，说明不进行服务拦截
        IgnoreAuth annotation = handlerMethod.getBeanType().getAnnotation(IgnoreAuth.class);
        if(annotation==null)
        {
            annotation = handlerMethod.getMethodAnnotation(IgnoreAuth.class);
        }
        if(annotation!=null) {
            return super.preHandle(request, response, handler);
        }

        //鉴权
        if(tokenService!=null){
            if(!tokenService.doCheck(getToken(request))){
                throw new BaseException(AuthExceptionEnum.TOKEN_ERROR);
            }
        }else{

        }

        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }


    /**
     * 获取token
     * @param request
     * @return
     */
    private String getToken(HttpServletRequest request) {

        //获取token
        String token = request.getHeader(AuthConstants.TOKEN_KEY);

        //当没有token则从cookie取
        if (StringUtils.isEmpty(token)) {
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals(AuthConstants.TOKEN_KEY)) {
                        token = cookie.getValue();
                    }
                }
            }
        }

        return token;
    }
}
