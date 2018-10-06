package org.chens.framework.auth.service;

import org.chens.core.exception.BaseExceptionEnum;
import org.chens.core.util.StringUtils;
import org.chens.core.vo.Result;
import org.chens.core.vo.UserInfo;
import org.chens.framework.auth.BaseContextHandler;
import org.chens.framework.auth.exception.AuthExceptionEnum;
import org.chens.framework.auth.vo.AuthRequest;

/**
 * 授权服务
 *
 * @author songchunlei
 * @since 2018/10/5
 */
public abstract class AbstractAuthService {

    /**
     * 抽象方法，获取用户信息
     * @param authRequest
     * @return
     */
    protected abstract UserInfo getUserInfo(AuthRequest authRequest);

    /**
     * 登陆
     * @param authRequest
     * @return
     */
    public Result<UserInfo> login(AuthRequest authRequest){
        if(authRequest==null){
            return Result.getError(BaseExceptionEnum.REQUEST_NULL);
        }
        if(StringUtils.isEmpty(authRequest.getUserName())){
            return Result.getError(AuthExceptionEnum.AUTH_REQUEST_NO_USERNAME);
        }
        if(StringUtils.isEmpty(authRequest.getUserName())){
            return Result.getError(AuthExceptionEnum.AUTH_REQUEST_NO_USERNAME);
        }
        //获取用户信息
        UserInfo userInfo = this.getUserInfo(authRequest);
        //写入线程缓存
        BaseContextHandler.setUserInfo(userInfo);
        //返回登陆信息
        return Result.getSuccess(userInfo);
    }

    /**
     * 登出
     * @return
     */
    public Result<Boolean> logout(){
        BaseContextHandler.clear();
        return Result.getSuccess(true);
    }

}
