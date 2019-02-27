package com.abaya.picacho.org.service.impl;

import com.abaya.picacho.org.entity.Organization;
import com.abaya.picacho.org.model.OrgAddRequest;
import com.abaya.picacho.org.model.OrgNode;
import com.abaya.picacho.org.repository.OrganizationRepository;
import com.abaya.picacho.org.service.OrgNodeConvertService;
import com.abaya.picacho.org.service.OrganizationService;
import com.abaya.picacho.org.util.OrgNodeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {
  @Autowired
  private OrgNodeConvertService service;

  @Autowired
  private OrganizationRepository repository;


  @Override
  public OrgNode queryOrganizationAsTree() {
    return service.convert(repository.findAll());
  }

  @Override
  public OrgNode updateOrganization(OrgNode node) {
    Organization organization = repository.findById(node.getId()).get();
    organization.setName(node.getPrimaryText());
    organization.setDescription(node.getSecondaryText());

    return OrgNodeHelper.create(repository.save(organization));
  }

  @Override
  public OrgNode addOrganization(OrgAddRequest request) {
    Organization parent = repository.findById(request.getParentId()).get();
    if (parent == null) return null;

    Organization organization = new Organization();
    organization.setLevel(parent.getLevel() + 1);
    organization.setParentCode(parent.getCode());
    organization.setName(request.getPrimaryText());
    organization.setDescription(request.getSecondaryText());
    organization.setType(request.getType());

    organization = repository.save(organization);

    // update org id using id
    organization.setCode(Long.toString(organization.getId()));
    return OrgNodeHelper.create(repository.save(organization));
  }
}
