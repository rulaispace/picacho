package com.abaya.picacho.announcement.repository;

import com.abaya.picacho.announcement.entity.Announcement;
import com.abaya.picacho.announcement.entity.AnnouncementAttachment;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface AttachmentRepository extends CrudRepository<AnnouncementAttachment, Long> {
    @Transactional
    void deleteAllByAnnouncement(Announcement announcement);
}
