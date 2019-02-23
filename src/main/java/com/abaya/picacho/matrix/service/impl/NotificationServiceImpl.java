package com.abaya.picacho.matrix.service.impl;

import com.abaya.picacho.matrix.entity.Notification;
import com.abaya.picacho.matrix.repository.NotificationRepository;
import com.abaya.picacho.matrix.service.NotificationService;
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
