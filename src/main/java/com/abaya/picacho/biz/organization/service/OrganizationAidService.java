package com.abaya.picacho.biz.organization.service;

import com.abaya.picacho.biz.organization.entity.Organization;

public interface OrganizationAidService {
    String generateAncestorFullName(String code);

    String generateOrganizationFullName(String code);

    boolean hasChildren(Organization organization);

    boolean isDepartmentCode(String parentCode);

    Organization queryOrganizationByCode(String code);
}
