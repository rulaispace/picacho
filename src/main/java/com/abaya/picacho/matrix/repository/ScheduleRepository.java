package com.abaya.picacho.matrix.repository;

import com.abaya.picacho.matrix.entity.Schedule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    List<Schedule> findAll();
}
