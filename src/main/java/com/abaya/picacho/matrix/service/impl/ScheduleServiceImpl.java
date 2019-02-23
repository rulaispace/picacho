package com.abaya.picacho.matrix.service.impl;

import com.abaya.picacho.matrix.entity.Schedule;
import com.abaya.picacho.matrix.repository.ScheduleRepository;
import com.abaya.picacho.matrix.service.ScheduleService;
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
