package com.github.carlos.common.constant;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/21 12:36
 * @description: TODO
 */
public enum  CariosCodeEnum {



    SUCCESS(0, "success!", ErrorLevelEnum.SYSTEM),

    DATA_NOT_FOUND(10001, "data not found", ErrorLevelEnum.SYSTEM),

    ILLEGAL_PARAMS(10002, "illegal params", ErrorLevelEnum.USER_WARN),

    INTERFACE_NOT_FOUND(10003, "interface not found", ErrorLevelEnum.USER_WARN),

    DATA_NOT_SUPPORT(10004, "data not support", ErrorLevelEnum.SYSTEM),

    DATA_ERROR(10005, "data error", ErrorLevelEnum.USER_WARN),

    TIME_OUT(10006, "time out", ErrorLevelEnum.SYSTEM),

    UNKNOWN_ERROR(90001,"unknow error",ErrorLevelEnum.SYSTEM );

    private CariosCodeEnum(Integer code, String message, ErrorLevelEnum errorLevel) {
        this.code = code;
        this.message = message;
        this.errorLevel = errorLevel;
    }

    private Integer code;
    private String message;
    private ErrorLevelEnum errorLevel;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorLevelEnum getErrorLevel() {
        return errorLevel;
    }

    public void setErrorLevel(ErrorLevelEnum errorLevel) {
        this.errorLevel = errorLevel;
    }



    @Override
    public String toString() {
        return "PhoenixTreeCode{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", errorLevel=" + errorLevel +
                '}';
    }
}
