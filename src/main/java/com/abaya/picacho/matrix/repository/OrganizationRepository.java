package com.abaya.picacho.matrix.repository;

import com.abaya.picacho.matrix.entity.Organization;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrganizationRepository extends CrudRepository<Organization, Long> {
  List<Organization> findAll();
}
