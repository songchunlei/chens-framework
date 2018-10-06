package org.chens.framework.aop;

import lombok.extern.slf4j.Slf4j;
import org.chens.core.exception.BaseException;
import org.chens.core.exception.BaseExceptionEnum;
import org.chens.core.exception.TimeOutException;
import org.chens.core.util.StringUtils;
import org.chens.core.vo.Result;
import org.chens.framework.auth.exception.AuthExceptionEnum;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.security.auth.message.AuthException;
import java.util.List;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 * @author fengshuonan
 * @since 2016年11月12日 下午3:19:56
 */
@Slf4j
public class BaseControllerExceptionHandler {

    /**
     * 拦截超时异常
     */
    @ExceptionHandler(TimeOutException.class)
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    @ResponseBody
    public Result handleBaseException(TimeOutException e) {
        log.error("超时异常:", e);
        return Result.getError(BaseExceptionEnum.TIMEOUT);
    }

    /**
     * 拦截授权异常
     */
    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Result handleBaseException(AuthException e) {
        log.error("授权异常:", e);
        return Result.getError(AuthExceptionEnum.AUTH_REQUEST_SIMPLE_ERROR.getCode(),e.getMessage());
    }

    /**
     * 拦截业务异常
     */
    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Result handleBaseException(BaseException e) {
        log.error("业务异常:", e);
        return Result.getError(AuthExceptionEnum.AUTH_REQUEST_SIMPLE_ERROR.getCode(),e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Result handleRuntimeException(RuntimeException e) {
        log.error("运行时异常:", e);
        return Result.getError(BaseExceptionEnum.SERVER_ERROR.getCode(),e.getMessage());
    }

    /**
     * 必填报错拦截
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Result handleBindException(MethodArgumentNotValidException e) {
        log.error("必填报错异常:", e);
        String msg = handlerValidateMsg(e.getBindingResult());
        if(StringUtils.isNotEmpty(msg))
        {
            return Result.getError(BaseExceptionEnum.VALIDATE_FAILED.getCode(),msg);
        }
        return Result.getError(BaseExceptionEnum.SERVER_ERROR.getCode(), e.getMessage());
    }

    /**
     * 获取校验提示
     * @param result
     * @return
     */
    public static String handlerValidateMsg(BindingResult result){
        String errMsg = "";

        if(result.hasErrors())
        {
            List<ObjectError> errors = result.getAllErrors();
            for(ObjectError error :errors)
            {
                String errMsgTemp = error.getDefaultMessage();
                errMsg = ("").equals(errMsg)?errMsgTemp:errMsg+","+errMsgTemp;
            }
        }
        return errMsg;
    }
}
