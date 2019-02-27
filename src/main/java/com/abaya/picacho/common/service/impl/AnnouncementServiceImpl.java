package com.abaya.picacho.common.service.impl;

import com.abaya.picacho.common.entity.Announcement;
import com.abaya.picacho.common.repository.AnnouncementRepository;
import com.abaya.picacho.common.service.AnnouncementService;
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