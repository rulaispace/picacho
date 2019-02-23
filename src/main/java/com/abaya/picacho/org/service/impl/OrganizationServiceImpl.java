package com.abaya.picacho.org.service.impl;

import com.abaya.picacho.matrix.model.NestedListNode;
import com.abaya.picacho.matrix.repository.OrganizationRepository;
import com.abaya.picacho.org.biz.NestedListConverter;
import com.abaya.picacho.org.service.OrganizationService;
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

}
