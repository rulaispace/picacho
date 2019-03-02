package com.abaya.picacho.biz.service.impl;

import com.abaya.picacho.biz.entity.Announcement;
import com.abaya.picacho.biz.repository.AnnouncementRepository;
import com.abaya.picacho.biz.service.AnnouncementService;
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
