package com.abaya.picacho.matrix.service;

import com.abaya.picacho.matrix.entity.Account;

public interface AccountService {
  Account login(String username, String password);
}
