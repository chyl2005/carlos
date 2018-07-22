package com.github.carlos.common.model;




import com.github.carlos.common.constant.CariosCodeEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class WebResponse<T>extends BaseObject implements Serializable {

    private Boolean success = true;

    private Integer status = CariosCodeEnum.SUCCESS.getCode();

    private T data;

    private String message=CariosCodeEnum.SUCCESS.getMessage();


    public WebResponse() {
    }

    public WebResponse(T data) {
        this.data = data;
    }

    public WebResponse(boolean success, Integer status, T data, String message) {
        this.success = success;
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static WebResponse getSuccessWebResponse() {
        WebResponse webResponse = new WebResponse();
        webResponse.setStatus(CariosCodeEnum.SUCCESS.getCode());
        webResponse.setMessage(CariosCodeEnum.SUCCESS.getMessage());
        return webResponse;
    }



    public static WebResponse getParamErrorWebResponse() {
        WebResponse webResponse = new WebResponse();
        webResponse.setStatus(CariosCodeEnum.ILLEGAL_PARAMS.getCode());
        webResponse.setMessage(CariosCodeEnum.ILLEGAL_PARAMS.getMessage());
        return webResponse;
    }



    public static WebResponse getErrorWebResponse(String detailMsg) {
        WebResponse webResponse = new WebResponse();
        webResponse.setStatus(CariosCodeEnum.DATA_ERROR.getCode());
        webResponse.setMessage(detailMsg);
        return webResponse;
    }



}
