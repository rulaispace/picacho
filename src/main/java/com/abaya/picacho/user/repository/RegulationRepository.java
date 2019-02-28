package com.abaya.picacho.user.repository;

import com.abaya.picacho.biz.entity.Regulation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RegulationRepository  extends CrudRepository<Regulation, Long> {
    List<Regulation> findAll();
}
