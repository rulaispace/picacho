package com.abaya.picacho.user.service;

import com.abaya.picacho.common.ServiceException;
import com.abaya.picacho.user.entity.Account;
import com.abaya.picacho.user.model.SystemUser;

import java.util.List;

public interface AccountService {
  Account login(String username, String password) throws ServiceException;

  List<SystemUser> queryAllUsers();
}
