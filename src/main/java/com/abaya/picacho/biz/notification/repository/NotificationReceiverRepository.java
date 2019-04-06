package com.abaya.picacho.biz.notification.repository;

import com.abaya.picacho.biz.notification.entity.NotificationReceiver;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationReceiverRepository extends CrudRepository<NotificationReceiver, Long> {
    List<NotificationReceiver> findAllByUsername(String username);

    boolean existsByNotificationIdAndUsername(Long notificationId, String username);
}
