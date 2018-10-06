package org.chens.framework.auth.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 认证请求
 *
 * @author songchunlei@qq.com
 * @since 2018/3/8
 */
@Data
public class AuthRequest implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4958322063059771295L;
    private String userName;
    private String password;

    public AuthRequest() {

    }

    public AuthRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
