package org.chens.framework.web;

import org.chens.core.enums.IBaseEnum;
import org.chens.core.vo.Result;
import org.chens.framework.util.AopTargetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;

/**
 * 基础web包
 *
 * @author songchunlei@qq.com
 * @since 2018/3/5
 */
public class BaseController {

    /**
     * redirect跳转
     */
    public String redirect(String url) {
        return "redirect:" + url;
    }

    /**
     * forward跳转 转发
     */
    public String forward(String url) {
        return "forward:" + url;
    }
}
