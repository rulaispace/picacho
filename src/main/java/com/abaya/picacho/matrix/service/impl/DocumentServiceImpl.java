package com.abaya.picacho.matrix.service.impl;

import com.abaya.picacho.matrix.entity.Document;
import com.abaya.picacho.matrix.repository.DocumentRepository;
import com.abaya.picacho.matrix.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private DocumentRepository repository = null;

    @Override
    public List<Document> queryAll() {
        return repository.findAll();
    }
}
