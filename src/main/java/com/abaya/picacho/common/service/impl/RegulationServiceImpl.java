package com.abaya.picacho.common.service.impl;

import com.abaya.picacho.common.entity.Regulation;
import com.abaya.picacho.common.repository.RegulationRepository;
import com.abaya.picacho.common.service.RegulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegulationServiceImpl implements RegulationService {
    @Autowired
    private RegulationRepository repository = null;


    @Override
    public List<Regulation> queryAll() {
        return repository.findAll();
    }
}