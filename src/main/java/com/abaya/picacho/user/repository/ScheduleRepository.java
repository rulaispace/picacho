package com.abaya.picacho.user.repository;

import com.abaya.picacho.biz.entity.Schedule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    List<Schedule> findAll();
}
