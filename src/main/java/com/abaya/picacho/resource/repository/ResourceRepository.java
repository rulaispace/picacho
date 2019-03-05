package com.abaya.picacho.resource.repository;

import com.abaya.picacho.resource.entity.Resource;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResourceRepository extends CrudRepository<Resource, Long> {
    List<Resource> findAll();
    Resource findByCodeIgnoreCase(String code);
}
