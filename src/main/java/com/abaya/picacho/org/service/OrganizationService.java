package com.abaya.picacho.org.service;

import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.org.entity.Organization;
import com.abaya.picacho.org.model.OrgNode;
import com.abaya.picacho.user.entity.Account;
import com.abaya.picacho.user.model.RuleType;

public interface OrganizationService {
    OrgNode queryOrganizationAsTree();

    Organization updateOrganization(Organization organization) throws ServiceException;

    Organization addOrganization(Organization organization) throws ServiceException;

    void deleteOrganization(String code, String operator) throws ServiceException;

    Account activateUser(String username, RuleType rule, String operator) throws ServiceException;

    Account deactivateUser(String username, String operator) throws ServiceException;
}
