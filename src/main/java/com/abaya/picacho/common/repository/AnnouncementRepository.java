package com.abaya.picacho.common.repository;

import com.abaya.picacho.common.entity.Announcement;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnnouncementRepository extends CrudRepository<Announcement, Long> {
    List<Announcement> findAll();
}
