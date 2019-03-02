package com.abaya.picacho.biz.service.impl;

import com.abaya.picacho.biz.entity.Document;
import com.abaya.picacho.biz.repository.DocumentRepository;
import com.abaya.picacho.biz.service.DocumentService;
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
