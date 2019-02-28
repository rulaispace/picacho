package com.abaya.picacho.user.util;

import com.abaya.picacho.user.entity.Account;
import com.abaya.picacho.user.model.SystemUser;

public class SystemUserHelper {
    public static SystemUser create(Account account) {
        SystemUser user = new SystemUser();
        user.setId(account.getId());
        user.setName(account.getName());
        user.setUsername(account.getUsername());
        user.setState(account.getState());
        user.setCreateDateTime(account.getCreateDateTime());
        user.setDepartment(account.getDepartment());

        return user;
    }
}
