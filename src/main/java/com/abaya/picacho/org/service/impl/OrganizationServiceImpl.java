package com.abaya.picacho.org.service.impl;

import com.abaya.picacho.matrix.entity.Organization;
import com.abaya.picacho.matrix.model.NestedListNode;
import com.abaya.picacho.org.biz.NestedListConverter;
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
}
