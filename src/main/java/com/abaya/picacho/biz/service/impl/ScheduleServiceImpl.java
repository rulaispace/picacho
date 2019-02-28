package com.abaya.picacho.biz.service.impl;

import com.abaya.picacho.biz.entity.Schedule;
import com.abaya.picacho.user.repository.ScheduleRepository;
import com.abaya.picacho.biz.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleRepository repository = null;

    @Override
    public List<Schedule> queryAll() {
        return repository.findAll();
    }
}
