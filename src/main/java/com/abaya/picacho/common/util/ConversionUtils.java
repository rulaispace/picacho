package com.abaya.picacho.common.util;

import com.abaya.picacho.common.convert.ExtensibleEntityConverter;
import com.abaya.picacho.common.convert.GenericConverter;
import com.abaya.picacho.common.convert.NothingConverter;
import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.user.entity.Account;
import com.abaya.picacho.user.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.support.DefaultConversionService;

@Slf4j
public class ConversionUtils implements ApplicationContextAware {
    private static final DefaultConversionService conversionService;
    static {
        conversionService = new DefaultConversionService();
        conversionService.addConverter(new NothingConverter());
        conversionService.addConverter(new ExtensibleEntityConverter());
        conversionService.addConverter(new GenericConverter());
    }

    private static ApplicationContext applicationContext = null;

    public static <T> T convert(Object source, Class<T> type) {
        T result = conversionService.convert(source, type);
        if (applicationContext != null) {
            // 如果source中存在token字段，则尝试将token转换成username并保存在result中
            result = convertToken2Username(source, result);
        }
        return result;
    }

    private static <T> T convertToken2Username(Object source, T result) {
        String token = PropertyUtils.get(source, "token", String.class);
        if (token == null) return result;

        String username = getUsernameByToken(token);
        if (username == null) {
            log.warn("未找到（{}）的用户数据！", token);
            return result;
        }

        PropertyUtils.set(result, "creator", username);
        PropertyUtils.set(result, "modifier", username);

        return result;
    }

    public static String getUsernameByToken(String token) {
        if (token == null) return null;
        if (applicationContext == null) return null;

        AccountService service = applicationContext.getBean(AccountService.class);
        if (service == null) return null;

        try {
            Account account = service.queryValidAccountByToken(token);
            if (account == null) {
                log.warn("未找到（{}）的用户数据！", token);
                return null;
            }

            return account.getUsername();
        } catch (ServiceException e) {
            log.warn("查询用户信息异常", e);
            return null;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ConversionUtils.applicationContext = applicationContext;
    }
}
