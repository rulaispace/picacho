package com.abaya.picacho.announcement.service;

import com.abaya.picacho.announcement.entity.Announcement;
import com.abaya.picacho.announcement.entity.AnnouncementAttachment;
import com.abaya.picacho.common.exception.ServiceException;

import java.util.List;

public interface AnnouncementService {

    Announcement addAnnouncement(Announcement announcement) throws ServiceException;

    void deleteAnnouncement(Long id, String operator) throws ServiceException;

    Announcement updateAnnouncement(Announcement announcement) throws ServiceException;

    List<Announcement> queryAll();

    AnnouncementAttachment addAttachment(AnnouncementAttachment attachment);

    AnnouncementAttachment queryAttachment(Long id, String operator);

    void deleteAttachment(Long id, String operator);

    Announcement releaseAnnouncement(Long id, String operator) throws ServiceException;

    Announcement callbackAnnouncement(Long id, String operator) throws ServiceException;

    Announcement queryAnnouncementById(Long id);
}
