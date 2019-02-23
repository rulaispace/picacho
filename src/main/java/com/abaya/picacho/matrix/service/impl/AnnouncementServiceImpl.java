package com.abaya.picacho.matrix.service.impl;

import com.abaya.picacho.matrix.entity.Announcement;
import com.abaya.picacho.matrix.repository.AnnouncementRepository;
import com.abaya.picacho.matrix.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    @Autowired
    private AnnouncementRepository repository;

    @Override
    public List<Announcement> queryAll() {
        return repository.findAll();
    }
}
