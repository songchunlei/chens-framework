package org.chens.framework.auth.service;

import org.chens.core.vo.Result;

/**
 * token校验服务
 *
 * @author songchunlei
 * @since 2018/10/6
 */
public interface ITokenService {

    /**
     * 鉴权
     * @param token
     * @return
     */
    Boolean doCheck(String token);
}
