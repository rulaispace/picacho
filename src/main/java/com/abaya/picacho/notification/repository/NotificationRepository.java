package com.abaya.picacho.notification.repository;

import com.abaya.picacho.notification.entity.Notification;
import com.abaya.picacho.notification.model.NotificationState;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
  List<Notification> findAll();

  boolean existsBySourceIdAndState(Long sourceId, NotificationState state);

  Notification findBySourceId(Long sourceId);

  List<Notification> findAllByIdInAndStateOrderByCreateDateTimeDesc(List<Long> idList, NotificationState valid);
}
