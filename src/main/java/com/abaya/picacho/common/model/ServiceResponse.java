package com.abaya.picacho.common.model;

import lombok.Data;

@Data
public class ServiceResponse {
    private Long status;
    private String message;

    public ServiceResponse(Long status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ServiceResponse success() {
        return new ServiceResponse(SERVICE_SUCCESS_STATUS, SERVICE_SUCCESS_MESSAGE);
    }

    public static ServiceResponse serviceError(String message) {
        return new ServiceResponse(SERVICE_ERROR_STATUS, message);
    }

    public static final Long SERVICE_ERROR_STATUS = 500L;
    public static final Long SERVICE_SUCCESS_STATUS = 200L;
    public static final String SERVICE_SUCCESS_MESSAGE = "操作成功";
}
