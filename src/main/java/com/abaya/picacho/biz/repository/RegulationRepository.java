package com.abaya.picacho.biz.repository;

import com.abaya.picacho.biz.entity.Regulation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RegulationRepository  extends CrudRepository<Regulation, Long> {
    List<Regulation> findAll();
}
