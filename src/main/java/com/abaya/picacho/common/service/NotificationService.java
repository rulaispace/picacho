package com.abaya.picacho.common.service;

import com.abaya.picacho.common.entity.Notification;

import java.util.List;

public interface NotificationService {
  List<Notification> queryAll();
}
