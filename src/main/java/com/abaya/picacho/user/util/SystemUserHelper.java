package com.abaya.picacho.user.util;

import com.abaya.picacho.user.entity.Account;
import com.abaya.picacho.user.model.SystemUser;

public class SystemUserHelper {
    public static SystemUser create(Account account) {
        SystemUser user = new SystemUser();
        user.setId(account.getId());
        user.setName(account.getName());
        user.setUserName(account.getUsername());
        user.setState(account.getState());
        user.setCreateDate(account.getCreatedDate());
        user.setDepartment(account.getDepartment());

        return user;
    }
}
