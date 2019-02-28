package com.abaya.picacho.common.exception;

public class ServiceException extends Exception {
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Object... args) {
        super(String.format(message, args));
    }

    public static ServiceException illegalOperation() {
        return new ServiceException("操作员信息为空，请确认是否有权限执行该操作!");
    }
}
