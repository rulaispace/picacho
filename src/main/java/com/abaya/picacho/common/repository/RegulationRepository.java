package com.abaya.picacho.common.repository;

import com.abaya.picacho.common.entity.Regulation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RegulationRepository  extends CrudRepository<Regulation, Long> {
    List<Regulation> findAll();
}
