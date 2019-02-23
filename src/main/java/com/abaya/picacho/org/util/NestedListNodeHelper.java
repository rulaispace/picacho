package com.abaya.picacho.org.util;

import com.abaya.picacho.matrix.entity.Organization;
import com.abaya.picacho.matrix.model.NestedListNode;

public class NestedListNodeHelper {
  public static NestedListNode setNormalProperty(NestedListNode target, Organization organization) {
    target.setLevel(organization.getLevel());
    target.setKey(organization.getKey());
    target.setType(organization.getType());
    target.setPrimaryText(organization.getName());
    target.setSecondaryText(organization.getDescription());

    return target;
  }
}
