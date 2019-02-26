package com.abaya.picacho.org.service;

import com.abaya.picacho.matrix.model.NestedListNode;
import com.abaya.picacho.org.model.OrgAddRequest;

public interface OrganizationService {
  NestedListNode queryOrganizationAsTree();
  NestedListNode updateOrganization(NestedListNode node);
  NestedListNode addOrganization(OrgAddRequest request);
}
