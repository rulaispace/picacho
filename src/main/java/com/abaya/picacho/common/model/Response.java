package com.abaya.picacho.common.model;

import com.abaya.picacho.common.util.ConversionUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Response<T> {
    public static final int SUCCESS = 200;
    public static final int INNER_ERROR = 501;

    private int status;
    private T payload;

    public Response(int status, T payload) {
        this.status = status;
        this.payload = payload;
    }

    public static <T> Response<List<T>> success(List<?> sourceList, Class<T> type) {
        List<T> targetList = new ArrayList<T>();

        for (Object sourceObj : sourceList) {
            targetList.add(ConversionUtils.convert(sourceObj, type));
        }

        return success(targetList);
    }

    public static <T> Response<T> success(Object sourceObj, Class<T> type) {
        return success(ConversionUtils.convert(sourceObj, type));
    }

    public static <T> Response<T> success(T payload) {
        return new Response<>(SUCCESS, payload);
    }

    public static Response success(String message) {
        return new Response(SUCCESS, message);
    }

    public static Response success(String message, Object... args) {
        return success(String.format(message, args));
    }

    public static Response fail(String message) {
        return new Response(INNER_ERROR, message);
    }

    public static Response fail(String message, Object... args) {
        return new Response(INNER_ERROR, String.format(message, args));
    }
}
