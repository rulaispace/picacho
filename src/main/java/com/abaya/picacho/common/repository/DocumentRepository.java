package com.abaya.picacho.common.repository;

import com.abaya.picacho.common.entity.Document;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DocumentRepository extends CrudRepository<Document, Long> {
    List<Document> findAll();
}
