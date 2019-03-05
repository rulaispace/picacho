package com.abaya.picacho.user.service.impl;

import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.common.util.RandomUtils;
import com.abaya.picacho.org.entity.Organization;
import com.abaya.picacho.org.service.OrganizationAidService;
import com.abaya.picacho.user.entity.Account;
import com.abaya.picacho.common.model.CommonState;
import com.abaya.picacho.user.repository.AccountRepository;
import com.abaya.picacho.user.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository repository = null;

    @Autowired
    private OrganizationAidService organizationService = null;

    @Override
    public Account login(String username, String password) throws ServiceException {
        Account account = repository.findByUsernameIgnoreCase(username);
        if (account == null)
            throw new ServiceException("账户不存在，请联系管理员开通权限");

        if (!account.getPassword().equals(password))
            throw new ServiceException("登录密码错误，请重新登录");


        if (account.getState() != CommonState.valid)
            throw new ServiceException("账号失效，请联系管理员处理");

        // 新生成token
        account.setToken(RandomUtils.uuid());
        repository.save(account);

        return account;
    }

    @Override
    public List<Account> queryAllAccount() {
        return repository.findAll().stream().filter(account -> {
            Organization organization = organizationService.queryOrganizationByCode(account.getUsername());
            if (organization == null) return false;

            account.setDepartment(organizationService.generateAncestorFullName(account.getUsername()));
            return true;
        }).collect(Collectors.toList());
    }

    @Override
    public Account queryValidAccountByToken(String token) {
        Assert.notNull(token, "传入的token不能为空");
        return repository.findByTokenAndState(token, CommonState.valid);
    }

    @Override
    public Account queryAccountByUsername(String username) {
        Assert.notNull(username, "登录名不能为空");
        return repository.findByUsernameIgnoreCase(username);
    }

    @Override
    public Account upsertAccount(Account account) {
        Assert.notNull(account, "账户信息不能为空");
        return repository.save(account);
    }

    @Override
    public boolean isValidAccount(String username) {
        Assert.notNull(username, "登录名不能为空");

        Account account = repository.findByUsernameIgnoreCase(username);
        if (account == null) return false;

        return (account.getState() == CommonState.valid);
    }

    @Override
    public boolean isUsedUsername(String username) {
        if (username == null) return false;
        return repository.findByUsernameIgnoreCase(username) != null;
    }

    @Override
    public Account resetPassword(String username, String operator) throws ServiceException {
        String password = RandomUtils.generatePassayPassword();

        Account account = repository.findByUsernameIgnoreCase(username);

        if (account == null) throw new ServiceException("用户名(%s)不存在，请确认参数是否正确！", username);
        if (account.getState() != CommonState.valid) throw new ServiceException("用户(%s)账户状态异常，无法重置密码", username);

        account.setPassword(password);
        account.setModifier(operator);
        account.setShouldChangePassword(true);

        return repository.save(account);
    }

    @Override
    public Account changePassword(String username, String oldPassword, String newPassword, String operator) throws ServiceException {
        Assert.notNull(username, "用户名不能为空");
        Assert.notNull(operator, "操作员不能为空");
        Assert.notNull(oldPassword, "原密码不能为空");
        Assert.notNull(newPassword, "新密码不能为空");

        Account account = repository.findByUsernameIgnoreCase(username);

        if (account == null) throw new ServiceException("用户名(%s)不存在，请确认参数是否正确！", username);
        if (!account.getPassword().equals(oldPassword)) throw new ServiceException("原始密码不正确，请重新输入");

        if (account.getState() != CommonState.valid) throw new ServiceException("用户(%s)账户状态异常，无法重置密码", username);

        account.setPassword(newPassword);
        account.setModifier(operator);
        account.setShouldChangePassword(false);

        return repository.save(account);
    }
}
