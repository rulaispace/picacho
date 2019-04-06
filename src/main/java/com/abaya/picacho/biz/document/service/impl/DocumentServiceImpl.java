package com.abaya.picacho.biz.document.service.impl;

import com.abaya.picacho.biz.document.entity.Document;
import com.abaya.picacho.biz.document.repository.DocumentRepository;
import com.abaya.picacho.biz.document.service.DocumentService;
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
