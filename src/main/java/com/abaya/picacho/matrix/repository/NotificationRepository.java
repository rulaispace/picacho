package com.abaya.picacho.matrix.repository;

import com.abaya.picacho.matrix.entity.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
  List<Notification> findAll();
}
