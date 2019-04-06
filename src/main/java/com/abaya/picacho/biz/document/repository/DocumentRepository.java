package com.abaya.picacho.biz.document.repository;

import com.abaya.picacho.biz.document.entity.Document;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DocumentRepository extends CrudRepository<Document, Long> {
    List<Document> findAll();
}
