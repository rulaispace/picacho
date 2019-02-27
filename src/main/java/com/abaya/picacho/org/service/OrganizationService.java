package com.abaya.picacho.org.service;

import com.abaya.picacho.org.model.OrgAddRequest;
import com.abaya.picacho.org.model.OrgNode;

public interface OrganizationService {
  OrgNode queryOrganizationAsTree();
  OrgNode updateOrganization(OrgNode node);
  OrgNode addOrganization(OrgAddRequest request);
}
