package com.abaya.picacho.user.biz;

import com.abaya.picacho.common.Converter;
import com.abaya.picacho.user.entity.Account;
import com.abaya.picacho.user.model.SystemUser;
import com.abaya.picacho.user.util.SystemUserHelper;

import java.util.ArrayList;
import java.util.List;

public class SystemUserConverter implements Converter<List<Account>, List<SystemUser>> {
    @Override
    public List<SystemUser> get(List<Account> accountList) {
        List<SystemUser> result = new ArrayList<>();
        accountList.forEach(account -> result.add(SystemUserHelper.create(account)));

        return result;
    }
}