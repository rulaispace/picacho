package com.abaya.picacho.biz.announcement.repository;

import com.abaya.picacho.biz.announcement.entity.Announcement;
import com.abaya.picacho.biz.announcement.entity.AnnouncementAttachment;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface AnnouncementAttachmentRepository extends CrudRepository<AnnouncementAttachment, Long> {
    @Transactional
    void deleteAllByAnnouncement(Announcement announcement);
    List<AnnouncementAttachment> findAllByAnnouncement(Announcement announcement);
    AnnouncementAttachment findByNickNameAndFileName(String nickName, String fileName);
}
