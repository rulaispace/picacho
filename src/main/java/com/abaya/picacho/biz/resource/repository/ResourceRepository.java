package com.abaya.picacho.biz.resource.repository;

import com.abaya.picacho.biz.resource.entity.Resource;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResourceRepository extends CrudRepository<Resource, Long> {
    List<Resource> findAll();
    Resource findByCodeIgnoreCase(String code);
}
