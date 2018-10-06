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

    private Class<?> clazz;

    protected Logger logger;

    protected BaseController() {
        clazz = AopTargetUtil.getSuperClassGenricType(getClass(), 0);
        logger = LoggerFactory.getLogger(clazz);
    }

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

    /**
     * 反馈通用方法
     * 
     * @param result
     * @return
     */
    public ResponseEntity<Result> doResponse(Result result) {
        return ResponseEntity.ok(result);
    }


    /**
     * 失败反馈
     * 
     * @return
     */
    public ResponseEntity<Result> doError(String msg) {
        return ResponseEntity.ok(Result.getError(msg));
    }

    /**
     * 失败反馈
     * 
     * @return
     */
    public ResponseEntity<Result> doError(IBaseEnum baseEnum) {
        return ResponseEntity.ok(Result.getError(baseEnum));
    }

    /**
     * 返回前台文件流
     *
     * @author fengshuonan
     * @date 2017年2月28日 下午2:53:19
     */
    protected ResponseEntity<byte[]> renderFile(String fileName, byte[] fileBytes) {
        String dfileName = null;
        try {
            dfileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", dfileName);
        return new ResponseEntity<byte[]>(fileBytes, headers, HttpStatus.CREATED);
    }

}
