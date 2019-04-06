package com.abaya.picacho.biz.organization.service;

import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.biz.organization.entity.Organization;
import com.abaya.picacho.biz.organization.model.OrgNode;
import com.abaya.picacho.biz.account.entity.Account;
import com.abaya.picacho.biz.account.model.RuleType;

public interface OrganizationService {
    OrgNode queryOrganizationAsTree();

    Organization updateOrganization(Organization organization) throws ServiceException;

    Organization addOrganization(Organization organization) throws ServiceException;

    void deleteOrganization(String code, String operator) throws ServiceException;

    Account activateUser(String username, RuleType rule, String operator) throws ServiceException;

    Account deactivateUser(String username, String operator) throws ServiceException;
}
