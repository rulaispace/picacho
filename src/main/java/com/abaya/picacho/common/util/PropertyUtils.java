package com.abaya.picacho.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class PropertyUtils {
    public static <T> T get(Object object, String key, Class<T> type) {
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

    public static <T> void set(Object object, String key, T value) {
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

    public static <T> T override(T target, Object source) {
        return override(target, source, null);
    }

    public static <T> T override(T target, Object source, String... ignoreProperties) {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);
        PropertyDescriptor[] targetPds = reflectionProxy(target.getClass(), "getPropertyDescriptors", null, PropertyDescriptor[].class);

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod == null || (ignoreList != null && ignoreList.contains(targetPd.getName()))) continue;

            PropertyDescriptor sourcePd = reflectionProxy(source.getClass(), "getPropertyDescriptor", targetPd.getName(), PropertyDescriptor.class);
            if (sourcePd == null) continue;

            Method readMethod = sourcePd.getReadMethod();
            if (readMethod == null || !ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType()))  continue;

            try {
                readMethod.setAccessible(true);
                Object value = readMethod.invoke(source);

                if (value == null) continue;

                writeMethod.setAccessible(true);
                writeMethod.invoke(target, value);
            }
            catch (Throwable ex) {
                throw new FatalBeanException("Could not copy property '" + targetPd.getName() + "' from source to target", ex);
            }
        }

        return target;
    }

    private static <T> T reflectionProxy(Class<?> clazz, String methodName, Object arg, Class<T> type) {
        Method forClass = ReflectionUtils.findMethod(CachedIntrospectionResults.class, "forClass", clazz.getClass());

        forClass.setAccessible(true);
        CachedIntrospectionResults cr = (CachedIntrospectionResults)ReflectionUtils.invokeMethod(forClass, null, clazz);


        Method method = null;
        if (arg == null) method = ReflectionUtils.findMethod(cr.getClass(), methodName);
        if (arg != null) method = ReflectionUtils.findMethod(cr.getClass(), methodName, arg.getClass());

        method.setAccessible(true);
        if (arg == null) return (T)ReflectionUtils.invokeMethod(method, cr);
        return (T)ReflectionUtils.invokeMethod(method, cr, arg);
    }
}