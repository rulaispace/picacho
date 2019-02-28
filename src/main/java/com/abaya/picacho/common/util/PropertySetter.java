package com.abaya.picacho.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

@Slf4j
public class PropertySetter {
    public static <T> void invoke(Object object, String key, T value) {
        String getMethodName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
        Method method = ReflectionUtils.findMethod(object.getClass(), getMethodName, value.getClass());
        if (method != null) {
            method.setAccessible(true);
            ReflectionUtils.invokeMethod(method, object, value);
            return ;
        }

        method = ReflectionUtils.findMethod(object.getClass(), "set", key.getClass(), value.getClass());
        if (method != null) {
            method.setAccessible(true);
            ReflectionUtils.invokeMethod(method, object, key, value);
            return ;
        }
    }
}