package com.abaya.picacho.org.service.impl;

import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.common.util.RandomUtils;
import com.abaya.picacho.org.entity.Organization;
import com.abaya.picacho.org.model.OrgNode;
import com.abaya.picacho.org.model.OrgType;
import com.abaya.picacho.org.repository.OrganizationRepository;
import com.abaya.picacho.org.service.OrgNodeConvertService;
import com.abaya.picacho.org.service.OrganizationService;
import com.abaya.picacho.org.util.OrgNodeHelper;
import com.abaya.picacho.user.entity.Account;
import com.abaya.picacho.user.model.AccountState;
import com.abaya.picacho.user.model.Rule;
import com.abaya.picacho.user.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    @Autowired
    private OrgNodeConvertService service;

    @Autowired
    private OrganizationRepository repository;

    @Autowired
    private AccountRepository accountRepository;


    @Override
    public OrgNode queryOrganizationAsTree() {
        return service.convert(repository.findAll());
    }

    @Override
    public OrgNode updateOrganization(OrgNode node) {
        Organization organization = repository.findById(node.getId()).get();
        organization.setName(node.getPrimaryText());
        organization.setDescription(node.getSecondaryText());

        return OrgNodeHelper.create(repository.save(organization));
    }

    @Override
    public Organization addOrganization(Organization organization) throws ServiceException {
        if (organization.getCreator() == null)
            throw ServiceException.illegalOperation();

        if (organization == null)
            throw new ServiceException("待新增的组织机构为空！");

        if (repository.findByCode(organization.getCode()) != null) {
            throw new ServiceException("编码(%s)在组织架构中已经存在，请更换编码再试", organization.getCode());
        }

        Organization parent = repository.findByCode(organization.getParentCode());
        if (parent == null)
            throw new ServiceException("未找到待新增的组织机构的父机构(%s)！" + organization.getParentCode());

        if (parent.getType() == OrgType.employee)
            throw new ServiceException("不能在人员节点(%s)下增加新的子节点！", parent.getCode());

        // 根据上级节点调整当前组织机构的层级
        organization.setLevel(parent.getLevel() + 1);
        return repository.save(organization);
    }

    @Override
    public Account activateUser(String username, Rule rule) throws ServiceException {
        if (username == null || username.trim().length() == 0)
            throw new ServiceException("用户名不可为空");

        Account account = accountRepository.findByUsername(username);
        if (account == null)
            return activateUserAtFirstTime(username, rule);

        if (account.getState() == AccountState.valid)
            throw new ServiceException("用户(%s)已经存在，且账户状态正常", username);

        return activateInvalidUser(account);
    }

    private Account activateUserAtFirstTime(String username, Rule rule) throws ServiceException {
        Organization organization = repository.findByCode(username);
        if (organization == null) throw new ServiceException("用户(%s)在组织架构中不存在，无法处理", username);

        if (organization.getType() == OrgType.department) throw new ServiceException("待开户的对象($s)是机构或部门，无法处理", username);

        Account account = new Account();
        account.setUsername(username);
        account.setPassword(RandomUtils.generatePassayPassword());
        account.setToken(RandomUtils.uuid());
        account.setDepartment(generateOrganizationFullName(organization.getParentCode()));
        account.setName(organization.getName());
        account.setRule(rule);
        account.setState(AccountState.valid);

        return accountRepository.save(account);
    }

    private Account activateInvalidUser(Account account) throws ServiceException {
        // 修改状态且重置密码
        account.setState(AccountState.valid);
        account.setPassword(RandomUtils.generatePassayPassword());
        return accountRepository.save(account);
    }

    @Override
    public String generateOrganizationFullName(String username) {
        List<String> organizationNames = new ArrayList<String>();
        Organization org = repository.findByCode(username);
        while (org != null) {
            organizationNames.add(org.getName());
            org = repository.findByCode(org.getParentCode());
        }

        String fullName = "";
        for (int i=organizationNames.size()-1; i>=0; i--) {
            fullName += organizationNames.get(i) + "/";
        }

        return fullName.substring(0, fullName.length()-1);
    }

    @Override
    public Account deactivateUser(String username) throws ServiceException {
        if (username == null || username.trim().length() == 0)
            throw new ServiceException("用户名不可为空");

        Account account = accountRepository.findByUsername(username.toUpperCase());
        if (account == null) throw new ServiceException("用户(%s)在系统中不存在！", username);

        if (account.getState() != AccountState.valid) throw new ServiceException("用户(%s)状态异常，无法注销！", username);

        account.setState(AccountState.invalid);
        return accountRepository.save(account);
    }
}
