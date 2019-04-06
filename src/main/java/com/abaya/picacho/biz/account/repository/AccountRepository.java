package com.abaya.picacho.biz.account.repository;

import com.abaya.picacho.biz.account.entity.Account;
import com.abaya.picacho.common.model.CommonState;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Long> {
    List<Account> findAll();

    Account findByUsernameIgnoreCase(String userName);

    Account findByTokenAndState(String token, CommonState state);
}
