package com.abaya.picacho.org.service.impl;

import com.abaya.picacho.org.entity.Organization;
import com.abaya.picacho.matrix.model.NestedListNode;
import com.abaya.picacho.org.biz.NestedListConverter;
import com.abaya.picacho.org.model.OrgAddRequest;
import com.abaya.picacho.org.repository.OrganizationRepository;
import com.abaya.picacho.org.service.OrganizationService;
import com.abaya.picacho.org.util.NestedListNodeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {
  @Autowired
  private OrganizationRepository repository;


  @Override
  public NestedListNode queryOrganizationAsTree() {
    return new NestedListConverter(repository.findAll()).get();
  }

  @Override
  public NestedListNode updateOrganization(NestedListNode node) {
    Organization organization = repository.findById(node.getId()).get();
    organization.setName(node.getPrimaryText());
    organization.setDescription(node.getSecondaryText());

    return NestedListNodeHelper.create(repository.save(organization));
  }

  @Override
  public NestedListNode addOrganization(OrgAddRequest request) {
    Organization parent = repository.findById(request.getParentId()).get();
    if (parent == null) return null;

    Organization organization = new Organization();
    organization.setLevel(parent.getLevel() + 1);
    organization.setParentOrgId(parent.getOrgId());
    organization.setName(request.getPrimaryText());
    organization.setDescription(request.getSecondaryText());
    organization.setType(request.getType());

    organization = repository.save(organization);

    // update org id using id
    organization.setOrgId(Long.toString(organization.getId()));
    return NestedListNodeHelper.create(repository.save(organization));
  }
}
