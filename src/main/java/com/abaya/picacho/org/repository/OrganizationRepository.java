package com.abaya.picacho.org.repository;

import com.abaya.picacho.matrix.entity.Organization;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrganizationRepository extends CrudRepository<Organization, Long> {
  List<Organization> findAll();
}
