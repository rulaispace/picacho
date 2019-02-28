package com.abaya.picacho.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

@Slf4j
public class PropertyGetter {
    public static <T> T invoke(Object object, String key, Class<T> type) {
        String getMethodName = "get" + key.substring(0, 1).toUpperCase() + key.substring(1);
        Method method = ReflectionUtils.findMethod(object.getClass(), getMethodName);
        if (method != null) {
            method.setAccessible(true);
            return (T)ReflectionUtils.invokeMethod(method, object);
        }

        method = ReflectionUtils.findMethod(object.getClass(), "get", Object.class);
        if (method != null) {
            method.setAccessible(true);
            return (T)ReflectionUtils.invokeMethod(method, object, key);
        }

        return null;
    }
}