package com.abaya.picacho.common.util;

import com.abaya.picacho.biz.account.entity.Account;
import com.abaya.picacho.biz.account.service.AccountService;
import com.abaya.picacho.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Slf4j
public class AuthorizeUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

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
        AuthorizeUtils.applicationContext = applicationContext;
    }
}
