package com.abaya.picacho.matrix.service;

import com.abaya.picacho.matrix.entity.Notification;

import java.util.List;

public interface NotificationService {
  List<Notification> queryAll();
}
