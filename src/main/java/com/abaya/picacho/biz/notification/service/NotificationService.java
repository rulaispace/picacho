package com.abaya.picacho.biz.notification.service;

import com.abaya.picacho.biz.announcement.entity.Announcement;
import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.biz.notification.entity.Notification;
import com.abaya.picacho.biz.notification.model.FatNotification;

import java.util.List;

public interface NotificationService {
  List<Notification> queryAll();

  boolean sendAnnouncement(Announcement announcement) throws ServiceException;
  boolean callbackAnnouncement(Announcement announcement) throws ServiceException;

  List<Notification> queryAllValidNotifications(String username) throws ServiceException;

  FatNotification queryFatNotification(Long notificationId, String operator) throws ServiceException;
}
