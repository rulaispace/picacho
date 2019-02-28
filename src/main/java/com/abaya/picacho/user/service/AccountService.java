package com.abaya.picacho.user.service;

import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.user.entity.Account;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface AccountService {
  Account login(String username, String password) throws ServiceException;
  List<Account> queryAllAccount();

  @Cacheable("accounts")
  Account validateToken(String token) throws ServiceException;
}
