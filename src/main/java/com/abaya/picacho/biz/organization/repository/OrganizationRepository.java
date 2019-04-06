package com.abaya.picacho.biz.organization.repository;

import com.abaya.picacho.biz.organization.entity.Organization;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrganizationRepository extends CrudRepository<Organization, Long> {
    List<Organization> findAll();
    List<Organization> findByParentCode(String parentCode);
    Organization findByCodeIgnoreCase(String code);
}
