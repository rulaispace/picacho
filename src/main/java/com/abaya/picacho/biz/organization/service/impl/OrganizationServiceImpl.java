package com.abaya.picacho.biz.organization.service.impl;

import com.abaya.picacho.biz.account.entity.Account;
import com.abaya.picacho.biz.account.model.RuleType;
import com.abaya.picacho.biz.account.service.AccountService;
import com.abaya.picacho.biz.organization.entity.Organization;
import com.abaya.picacho.biz.organization.model.OrgNode;
import com.abaya.picacho.biz.organization.model.OrgType;
import com.abaya.picacho.biz.organization.repository.OrganizationRepository;
import com.abaya.picacho.biz.organization.service.OrganizationAidService;
import com.abaya.picacho.biz.organization.service.OrganizationConvertService;
import com.abaya.picacho.biz.organization.service.OrganizationService;
import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.common.model.CommonState;
import com.abaya.picacho.common.util.EntityUtils;
import com.abaya.picacho.common.util.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    @Autowired
    private OrganizationConvertService convertService = null;

    @Autowired
    private OrganizationAidService aidService = null;

    @Autowired
    private AccountService accountService = null;

    @Autowired
    private OrganizationRepository repository = null;

    @Override
    public OrgNode queryOrganizationAsTree() {
        return convertService.convert(repository.findAll());
    }

    @Override
    @Transactional
    public Organization updateOrganization(Organization organization) throws ServiceException {
        Assert.notNull(organization, "传入的组织机构不能为空");

        Long id = organization.getId();
        Assert.notNull(id, "传入的组织机构主键不能为空");

        Organization origin = repository.findById(id).orElse(new Organization());
        if (origin.getId() == null) throw new ServiceException("组织机构（%s）不存在，请确认请求参数是否正确", id);

        String parentCode = organization.getParentCode();
        if (parentCode != null && parentCode.trim().length() != 0 && !aidService.isDepartmentCode(parentCode))
            throw new ServiceException("无法调整当前组织在公司的位置，父组织机构编码（%s）设置异常！", parentCode);

        if (organization.getType() != null && organization.getType() != origin.getType())
            throw new ServiceException("组织结构类型不可修改！");

        if (shouldUpdateCode(organization.getCode(), origin.getCode())) updateChildrenParentCode(organization.getCode(), origin.getCode());

        return repository.save(EntityUtils.entityUpdateMerge(origin, organization));
    }

    private boolean shouldUpdateCode(String target, String origin) throws ServiceException {
        if (target == null) return false;
        if (origin == null) throw new ServiceException("系统内部数据异常，找到机构组织编码为空的数据！");

        if (origin.equals(target)) return false;

        // 确认以target为组织机构编码的数据，在当前数据库中不存在
        if (repository.findByCodeIgnoreCase(target) != null) throw new ServiceException("组织机构编码(%s)已被使用！", target);
        return true;
    }

    private void updateChildrenParentCode(String target, String origin) {
        List<Organization> children = repository.findByParentCode(origin);
        children.forEach(organization -> organization.setParentCode(target));
        repository.saveAll(children);
    }

    @Override
    public Organization addOrganization(Organization organization) throws ServiceException {
        if (organization.getCreator() == null)
            throw ServiceException.illegalOperation();

        if (organization == null)
            throw new ServiceException("待新增的组织机构为空！");

        if (repository.findByCodeIgnoreCase(organization.getCode()) != null)
            throw new ServiceException("编码(%s)在组织架构中已经存在，请更换编码再试", organization.getCode());

        if (accountService.isUsedUsername(organization.getCode()))
            throw new ServiceException("编码(%s)已被作为系统登录账户使用，请更换编码再试", organization.getCode());

        Organization parent = repository.findByCodeIgnoreCase(organization.getParentCode());
        if (parent == null)
            throw new ServiceException("未找到待新增的组织机构的父机构(%s)！", organization.getParentCode());

        if (parent.getType() == OrgType.employee)
            throw new ServiceException("不能在人员节点(%s)下增加新的子节点！", parent.getCode());

        // 根据上级节点调整当前组织机构的层级
        organization.setLevel(parent.getLevel() + 1);
        return repository.save(organization);
    }

    @Override
    public void deleteOrganization(String code, String operator) throws ServiceException {
        Assert.notNull(code, "传入参数为空");
        Assert.notNull(operator, "传入参数为空");

        Organization organization = repository.findByCodeIgnoreCase(code);
        if (organization == null)
            throw new ServiceException("节点(%s)不存在，请确认请求数据是否正确", code);

        if (aidService.hasChildren(organization))
            throw new ServiceException("节点(%s)下仍有子节点存在，无法删除仍存在子节点的节点", organization.getCode());

        if (accountService.isValidAccount(organization.getCode()))
            throw new ServiceException("节点(%s)对应的账户未被注销，请先注销账户再删除该节点", organization.getCode());

        repository.delete(organization);
    }

    @Override
    public Account activateUser(String username, RuleType rule, String operator) throws ServiceException {
        if (username == null || username.trim().length() == 0)
            throw new ServiceException("用户名不可为空");

        Account account = accountService.queryAccountByUsername(username);
        if (account == null)
            return activateUserAtFirstTime(username, rule, operator);

        if (account.getState() == CommonState.valid)
            throw new ServiceException("用户(%s)已经存在，且账户状态正常", username);

        if (rule != null) account.setRule(rule);
        account.setModifier(operator);

        return activateInvalidUser(account);
    }

    private Account activateUserAtFirstTime(String username, RuleType rule, String operator) throws ServiceException {
        Organization organization = repository.findByCodeIgnoreCase(username);
        if (organization == null) throw new ServiceException("用户(%s)在组织架构中不存在，无法处理", username);

        if (organization.getType() == OrgType.department) throw new ServiceException("待开户的对象(%s)是机构或部门，无法处理", username);

        if (rule == null) throw new ServiceException("需要制定(%s)用户需要开通的角色权限", username);

        Account account = new Account();
        account.setUsername(username);
        account.setPassword(RandomUtils.generatePassayPassword());
        account.setToken(RandomUtils.uuid());
        account.setDepartment(aidService.generateOrganizationFullName(organization.getParentCode()));
        account.setName(organization.getName());
        account.setRule(rule);
        account.setState(CommonState.valid);
        account.setCreator(operator);
        account.setModifier(operator);

        return accountService.upsertAccount(account);
    }

    private Account activateInvalidUser(Account account) throws ServiceException {
        // 修改状态且重置密码
        account.setState(CommonState.valid);
        account.setPassword(RandomUtils.generatePassayPassword());
        return accountService.upsertAccount(account);
    }

    @Override
    public Account deactivateUser(String username, String operator) throws ServiceException {
        if (username == null || username.trim().length() == 0)
            throw new ServiceException("用户名不可为空");

        Account account = accountService.queryAccountByUsername(username.toUpperCase());
        if (account == null) throw new ServiceException("用户(%s)在系统中不存在！", username);

        if (account.getState() != CommonState.valid) throw new ServiceException("用户(%s)状态异常，无法注销！", username);

        account.setModifier(operator);
        account.setState(CommonState.invalid);
        return accountService.upsertAccount(account);
    }
}
