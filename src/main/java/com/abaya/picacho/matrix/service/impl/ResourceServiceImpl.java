package com.abaya.picacho.matrix.service.impl;

import com.abaya.picacho.matrix.entity.Resource;
import com.abaya.picacho.matrix.repository.ResourceRepository;
import com.abaya.picacho.matrix.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceRepository repository = null;

    @Override
    public List<Resource> queryAll() {
        return repository.findAll();
    }
}
