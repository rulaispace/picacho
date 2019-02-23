package com.abaya.picacho.matrix.repository;

import com.abaya.picacho.matrix.entity.Resource;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResourceRepository extends CrudRepository<Resource, Long> {
    List<Resource> findAll();
}
