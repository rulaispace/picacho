package com.abaya.picacho.user.repository;

import com.abaya.picacho.biz.entity.Announcement;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnnouncementRepository extends CrudRepository<Announcement, Long> {
    List<Announcement> findAll();
}
