package com.abaya.picacho.common.service.impl;

import com.abaya.picacho.common.entity.Resource;
import com.abaya.picacho.common.repository.ResourceRepository;
import com.abaya.picacho.common.service.ResourceService;
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
