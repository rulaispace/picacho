package com.abaya.picacho.matrix.repository;

import com.abaya.picacho.matrix.entity.Document;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DocumentRepository extends CrudRepository<Document, Long> {
    List<Document> findAll();
}
