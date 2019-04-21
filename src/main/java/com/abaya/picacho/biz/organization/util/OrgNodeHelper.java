package com.abaya.picacho.biz.organization.util;

import com.abaya.picacho.biz.organization.entity.Organization;
import com.abaya.picacho.biz.organization.model.OrgNode;

public class OrgNodeHelper {
  public static OrgNode setNormalProperty(OrgNode target, Organization organization) {
    target.setLevel(organization.getLevel());
    target.setId(organization.getId());
    target.setCode(organization.getCode());
    target.setType(organization.getType().name());
    target.setPrimaryText(organization.getName());
    target.setSecondaryText(organization.getDescription());

    return target;
  }
}
