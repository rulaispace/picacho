package com.abaya.picacho.org.service;

import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.org.entity.Organization;
import com.abaya.picacho.org.model.OrgNode;
import com.abaya.picacho.user.entity.Account;
import com.abaya.picacho.user.model.Rule;

public interface OrganizationService {
    OrgNode queryOrganizationAsTree();
    OrgNode updateOrganization(OrgNode node);
    Organization addOrganization(Organization organization) throws ServiceException;
    Account activateUser(String username, Rule rule) throws ServiceException;
    Account deactivateUser(String username) throws ServiceException;
    String generateOrganizationFullName(String username);
}
