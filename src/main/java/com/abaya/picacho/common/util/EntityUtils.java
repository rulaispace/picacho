package com.abaya.picacho.common.util;

import com.abaya.picacho.common.entity.EntityBase;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class EntityUtils {
    public static <T> T entityMerge(T target, Object source) {
        return PropertyUtils.override(target, source);
    }

    /**
     * 属性拷贝，忽略creator字段
     * @param target
     * @param source
     * @param <T>
     * @return
     */
    public static <T> T entityUpdateMerge(T target, Object source) {
        return PropertyUtils.override(target, source, "creator");
    }

    /**
     * 属性拷贝，忽略id字段
     * @param target
     * @param source
     * @param <T>
     * @return
     */
    public static <T> T entityPropertyMerge(T target, Object source) {
        return PropertyUtils.override(target, source, "id");
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

        return PropertyUtils.override(target, source, _ignoreProperties);
    }

    public static <T extends EntityBase> T initEntity(T target) {
        target.setCreateDateTime(LocalDateTime.now());
        target.setUpdateDateTime(LocalDateTime.now());
        target.setModifier("ADMIN");
        target.setCreator("ADMIN");
        return target;
    }

    public static <T> T overrideOperator(Object source, T entity) {
        String token = PropertyUtils.get(source, "token", String.class);
        if (token == null) return entity;
        if (!(entity instanceof EntityBase)) return entity;

        String username = AuthorizeUtils.getUsernameByToken(token);
        if (username == null) {
            log.warn("未找到（{}）的用户数据！", token);
            return entity;
        }

        PropertyUtils.set(entity, "creator", username);
        PropertyUtils.set(entity, "modifier", username);

        return entity;
    }

    public static <T extends EntityBase> Iterable<T> overrideOperator(Iterable<T> entities, String username) {
        if (entities == null) return null;

        entities.forEach(entity -> overrideOperator(entity, username));
        return entities;
    }

    public static <T extends EntityBase> T overrideOperator(T entity, String username) {
        entity.setCreator(username);
        entity.setModifier(username);
        return entity;
    }
}
