package com.abaya.picacho.user.repository;

import com.abaya.picacho.user.entity.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Long> {
  List<Account> findAll();
  Account findByUsernameIgnoreCase(String userName);
  Account findByUsername(String userName);
}
