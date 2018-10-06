package org.chens.framework.auth.exception;

import org.chens.core.enums.IBaseEnum;

/**
 * 错误异常枚举
 *
 * @author songchunlei
 * @since 2018/3/8
 */
public enum AuthExceptionEnum implements IBaseEnum {

    /**
     * token异常
     */
    TOKEN_EXPIRED(100, "token过期"), TOKEN_ERROR(101, "token验证失败"), TOKEN_IS_NULL(102, "token为空"),

    /**
     * 登录校验异常
     */
    AUTH_REQUEST_ERROR(200, "账号或密码错误"), AUTH_REQUEST_NO_USERNAME(201, "账号为空"), AUTH_REQUEST_NO_PASSWORD(202,
            "密码为空"), AUTH_REQUEST_NO_CODE(203, "验证码为空"), AUTH_REQUEST_SIMPLE_ERROR(204, "账户校验异常");

    private Integer code;

    private String message;

    AuthExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

}
