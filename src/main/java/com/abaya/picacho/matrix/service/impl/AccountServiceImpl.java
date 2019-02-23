package com.abaya.picacho.matrix.service.impl;

import com.abaya.picacho.matrix.entity.Account;
import com.abaya.picacho.matrix.repository.AccountRepository;
import com.abaya.picacho.matrix.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
  @Autowired
  private AccountRepository repository;

  @Override
  public Account login(String username, String password) {
    return repository.findByUsernameAndPassword(username, password);
  }
}
