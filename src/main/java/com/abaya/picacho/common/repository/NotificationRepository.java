package com.abaya.picacho.common.repository;

import com.abaya.picacho.common.entity.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
  List<Notification> findAll();
}
