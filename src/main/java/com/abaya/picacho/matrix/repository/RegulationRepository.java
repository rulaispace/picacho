package com.abaya.picacho.matrix.repository;

import com.abaya.picacho.matrix.entity.Regulation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RegulationRepository  extends CrudRepository<Regulation, Long> {
    List<Regulation> findAll();
}
