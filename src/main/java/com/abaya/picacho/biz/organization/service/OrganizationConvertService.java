package com.abaya.picacho.biz.organization.service;

import com.abaya.picacho.biz.organization.entity.Organization;
import com.abaya.picacho.biz.organization.model.OrgNode;

import java.util.List;

public interface OrganizationConvertService {
    OrgNode convert(List<Organization> organizations);
}
