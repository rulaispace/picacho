package com.abaya.picacho.biz.account.service;

import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.biz.account.entity.Account;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface AccountService {
  Account login(String username, String password) throws ServiceException;
  List<Account> queryAllAccount();

  @Cacheable("accounts")
  Account queryValidAccountByToken(String token) throws ServiceException;

  Account queryAccountByUsername(String username);

  Account upsertAccount(Account account);

  boolean isValidAccount(String username);

  boolean isUsedUsername(String username);

  Account resetPassword(String username, String operator) throws ServiceException;

  Account changePassword(String username, String oldPassword, String newPassword, String operator) throws ServiceException;

  String queryNameByUsername(String username) throws ServiceException;

  List<Account> queryAllValidAccount();
}
