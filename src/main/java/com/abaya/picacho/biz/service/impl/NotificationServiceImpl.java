package com.abaya.picacho.biz.service.impl;

import com.abaya.picacho.biz.entity.Notification;
import com.abaya.picacho.biz.repository.NotificationRepository;
import com.abaya.picacho.biz.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
  @Autowired
  private NotificationRepository repository = null;

  @Override
  public List<Notification> queryAll() {
    List<Notification> notifications = repository.findAll();
    return notifications;
  }
}
