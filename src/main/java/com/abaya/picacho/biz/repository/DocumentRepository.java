package com.abaya.picacho.biz.repository;

import com.abaya.picacho.biz.entity.Document;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DocumentRepository extends CrudRepository<Document, Long> {
    List<Document> findAll();
}
