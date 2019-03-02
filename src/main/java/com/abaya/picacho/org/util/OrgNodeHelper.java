package com.abaya.picacho.org.util;

import com.abaya.picacho.org.entity.Organization;
import com.abaya.picacho.org.model.OrgNode;

public class OrgNodeHelper {
  public static OrgNode setNormalProperty(OrgNode target, Organization organization) {
    target.setLevel(organization.getLevel());
    target.setId(organization.getCode());
    target.setType(organization.getType().name());
    target.setPrimaryText(organization.getName());
    target.setSecondaryText(organization.getDescription());

    return target;
  }
}
