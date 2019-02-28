package com.abaya.picacho.biz.service;

import com.abaya.picacho.biz.entity.Notification;

import java.util.List;

public interface NotificationService {
  List<Notification> queryAll();
}
