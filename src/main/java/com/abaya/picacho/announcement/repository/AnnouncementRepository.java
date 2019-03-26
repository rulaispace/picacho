package com.abaya.picacho.announcement.repository;

import com.abaya.picacho.announcement.entity.Announcement;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnnouncementRepository extends CrudRepository<Announcement, Long> {
    List<Announcement> findAllByOrderByUpdateDateTimeDesc();
}
