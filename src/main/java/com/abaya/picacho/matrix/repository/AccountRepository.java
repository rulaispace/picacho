package com.abaya.picacho.matrix.repository;

import com.abaya.picacho.matrix.entity.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Long> {
  List<Account> findByUsername(String userName);
  Account findByUsernameAndPassword(String userName, String password);
}
