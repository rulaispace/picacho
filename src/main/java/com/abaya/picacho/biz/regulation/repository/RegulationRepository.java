package com.abaya.picacho.biz.regulation.repository;

import com.abaya.picacho.biz.regulation.entity.Regulation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RegulationRepository  extends CrudRepository<Regulation, Long> {
    List<Regulation> findAll();
}
