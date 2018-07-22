package com.github.carlos.common.exception;


import com.github.carlos.common.constant.CariosCodeEnum;
import com.github.carlos.common.constant.ErrorLevelEnum;
import lombok.Data;
import lombok.Getter;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/21 12:35
 * @description: TODO
 */
@Data
@Getter
public class CariosException extends RuntimeException {

    private Integer code;

    private String errorMessage;

    private String detailErrorMessage;

    private ErrorLevelEnum errorLevelEnum;


    public CariosException(CariosCodeEnum cariosCode) {
        this(cariosCode, cariosCode.getMessage());
    }

    public CariosException(CariosCodeEnum cariosCode, Exception e) {
        this(cariosCode, cariosCode.getMessage(), cariosCode.getMessage(), e);

    }

    public CariosException(CariosCodeEnum cariosCode, String errorMessage) {
        this(cariosCode, errorMessage, errorMessage);
    }

    public CariosException(CariosCodeEnum cariosCode, String errorMessage, Exception e) {
        this(cariosCode, errorMessage, errorMessage, e);
    }

    public CariosException(CariosCodeEnum cariosCode, String errorMessage, String detailErrorMessage) {
        super('{' +
                "code='" + cariosCode.getCode() + '\'' +
                ", message='" + errorMessage + '\'' +
                ", detailErrorMessage='" + detailErrorMessage + '\'' +
                ", errorLevel=" + cariosCode.getErrorLevel() +
                '}');
        this.code = cariosCode.getCode();
        this.errorMessage = errorMessage;
        this.detailErrorMessage = detailErrorMessage;
        this.errorLevelEnum = cariosCode.getErrorLevel();
    }

    public CariosException(CariosCodeEnum cariosCode, String errorMessage, String detailErrorMessage, Exception e) {
        this(cariosCode, errorMessage, detailErrorMessage);
        this.initCause(e);
    }

    public static final CariosException conventToCariosException(Exception e) {
        if (e instanceof CariosException) {
            return (CariosException) e;
        } else {
            return new CariosException(CariosCodeEnum.UNKNOWN_ERROR, e);
        }


    }


    @Override
    public String toString() {
        return "PhoenixTreeException{" +
                "code='" + code + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", detailErrorMessage='" + detailErrorMessage + '\'' +
                ", errorLevelEnum=" + errorLevelEnum +
                '}';
    }
}
