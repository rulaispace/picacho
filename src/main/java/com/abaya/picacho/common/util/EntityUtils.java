package com.abaya.picacho.common.util;

import com.abaya.picacho.common.entity.EntityBase;

import java.time.LocalDateTime;

public class EntityUtils {
    public static <T> T entityMerge(T target, Object source) {
        return PropertyUtils.merge(target, source);
    }

    /**
     * 属性拷贝，忽略creator字段
     * @param target
     * @param source
     * @param <T>
     * @return
     */
    public static <T> T entityUpdateMerge(T target, Object source) {
        return PropertyUtils.merge(target, source, "creator");
    }

    /**
     * 属性拷贝，忽略id字段
     * @param target
     * @param source
     * @param <T>
     * @return
     */
    public static <T> T entityPropertyMerge(T target, Object source) {
        return PropertyUtils.merge(target, source, "id");
    }

    /**
     * 属性拷贝，忽略id字段
     * @param target
     * @param source
     * @param <T>
     * @return
     */
    public static <T> T entityPropertyMerge(T target, Object source, String... ignoreProperties) {
        String[] _ignoreProperties = new String[ignoreProperties.length + 1];
        _ignoreProperties[0] = "id";
        for (int i=1; i<_ignoreProperties.length; i++)
            _ignoreProperties[i] = ignoreProperties[i-1];

        return PropertyUtils.merge(target, source, _ignoreProperties);
    }

    public static <T extends EntityBase> T initEntity(T target) {
        target.setCreateDateTime(LocalDateTime.now());
        target.setUpdateDateTime(LocalDateTime.now());
        target.setModifier("ADMIN");
        target.setCreator("ADMIN");
        return target;
    }
}
