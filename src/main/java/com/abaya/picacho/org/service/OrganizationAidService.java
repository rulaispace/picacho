package com.abaya.picacho.org.service;

import com.abaya.picacho.org.entity.Organization;

public interface OrganizationAidService {
    String generateAncestorFullName(String code);

    String generateOrganizationFullName(String code);

    boolean hasChildren(Organization organization);

    boolean isDepartmentCode(String parentCode);

    Organization queryOrganizationByCode(String code);
}
