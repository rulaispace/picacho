package com.abaya.picacho.matrix.repository;

import com.abaya.picacho.matrix.entity.Announcement;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnnouncementRepository extends CrudRepository<Announcement, Long> {
    List<Announcement> findAll();
}
