package com.abaya.picacho.user.service.impl;

import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.common.util.RandomUtils;
import com.abaya.picacho.user.entity.Account;
import com.abaya.picacho.user.model.AccountState;
import com.abaya.picacho.user.repository.AccountRepository;
import com.abaya.picacho.user.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository repository;

    @Override
    public Account login(String username, String password) throws ServiceException {
        Account account = repository.findByUsername(username);
        if (account == null)
            throw new ServiceException("账户不存在，请联系管理员开通权限");

        if (!account.getPassword().equals(password))
            throw new ServiceException("登录密码错误，请重新登录");


        if (account.getState() != AccountState.valid)
            throw new ServiceException("账号失效，请联系管理员处理");

        // 新生成token
        account.setToken(RandomUtils.uuid());
        repository.save(account);

        return account;
    }

    @Override
    public List<Account> queryAllAccount() {
        return repository.findAll();
    }

    @Override
    public Account validateToken(String token) throws ServiceException {
        if (token == null) throw new ServiceException("传入的token不能为空！");
        return repository.findByToken(token);
    }
}
