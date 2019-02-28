package com.abaya.picacho.user.repository;

import com.abaya.picacho.biz.entity.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
  List<Notification> findAll();
}
