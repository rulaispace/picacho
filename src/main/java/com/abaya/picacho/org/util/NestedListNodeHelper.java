package com.abaya.picacho.org.util;

import com.abaya.picacho.matrix.entity.Organization;
import com.abaya.picacho.matrix.model.NestedListNode;

public class NestedListNodeHelper {
  public static NestedListNode create(Organization organization) {
    NestedListNode node = new NestedListNode();
    return setNormalProperty(node, organization);
  }

  public static NestedListNode setNormalProperty(NestedListNode target, Organization organization) {
    target.setLevel(organization.getLevel());
    target.setId(organization.getId());
    target.setType(organization.getType());
    target.setPrimaryText(organization.getName());
    target.setSecondaryText(organization.getDescription());

    return target;
  }
}
