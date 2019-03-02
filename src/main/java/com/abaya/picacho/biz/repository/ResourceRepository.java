package com.abaya.picacho.biz.repository;

import com.abaya.picacho.biz.entity.Resource;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResourceRepository extends CrudRepository<Resource, Long> {
    List<Resource> findAll();
}
