package com.abaya.picacho.org.service;

import com.abaya.picacho.matrix.model.NestedListNode;

public interface OrganizationService {
  NestedListNode queryOrganizationAsTree();
  NestedListNode updateOrganization(NestedListNode node);
}
