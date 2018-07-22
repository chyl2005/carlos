package com.github.carlos.app.filter;

import com.github.carlos.common.constant.CariosCodeEnum;
import com.github.carlos.common.constant.ErrorLevelEnum;
import com.github.carlos.common.constant.SysConstant;
import com.github.carlos.common.exception.CariosException;
import com.github.carlos.common.model.WebResponse;
import com.github.carlos.common.utils.JsonUtils;

import com.github.carlos.common.utils.ThreadLocalContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/6/21 17:08
 * @description: 全局异常处理器
 */

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public WebResponse handleException(HttpServletRequest request, Exception ex) {
        WebResponse response = new WebResponse(false, CariosCodeEnum.ILLEGAL_PARAMS.getCode(), null, CariosCodeEnum.ILLEGAL_PARAMS.getMessage());
        if (ex instanceof IllegalArgumentException) {
            /**
             * 参数异常
             */
            writeWarnLog(request, ex);
            return response;
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            /**
             * 接口调用方式错误
             */
            writeWarnLog(request, ex);
            return response;
        } else if (ex instanceof HttpMessageNotReadableException) {
            /**
             * requestBody中参数不符合规范
             */
            writeWarnLog(request, ex);
            return response;
        } else if (ex instanceof MissingServletRequestParameterException) {
            /**
             * 参数缺失错误
             */
            writeWarnLog(request, ex);
            return response;
        } else if (ex instanceof MethodArgumentNotValidException) {
            /**
             * 参数使用了valid注解
             */
            writeWarnLog(request, ex);
            return response;
        } else if (ex instanceof BindException) {
            /**
             * model bind 中参数不符合规范
             */
            writeWarnLog(request, ex);
            return response;
        }
        // 异常转换
        CariosException exception = CariosException.conventToCariosException(ex);
        WebResponse webResponse = new WebResponse(false, exception.getCode(), null, exception.getDetailErrorMessage());

        ErrorLevelEnum errorLevel = exception.getErrorLevelEnum();
        if (errorLevel.equals(ErrorLevelEnum.PARAM)) {
            writeWarnLog(request, ex);
        } else {
            writeErrorLog(request, ex);
        }
        return webResponse;
    }

    private void writeErrorLog(HttpServletRequest request, Exception ex) {
        LOGGER.error("INVOKE->{} ,parameterMap={} , METHOD={}, REQUEST={}, EXTRAPARAMS={}", request.getRequestURI(),
                JsonUtils.object2Json(request.getParameterMap()), request.getMethod(),
                ThreadLocalContext.get(SysConstant.PARAMS), JsonUtils.object2Json(ThreadLocalContext.get(SysConstant.EXTRA_PARAMS)),
                ex);

    }

    private void writeWarnLog(HttpServletRequest request, Exception ex) {
        LOGGER.warn(" INVOKE->{} ,parameterMap={} , METHOD={}, REQUEST={}, EXTRAPARAMS={}", request.getRequestURI(),
                JsonUtils.object2Json(request.getParameterMap()), request.getMethod(),
                ThreadLocalContext.get(SysConstant.PARAMSMAP), JsonUtils.object2Json(ThreadLocalContext.get(SysConstant.EXTRA_PARAMS)),
                ex);

    }
}
