package com.abaya.picacho.biz.notification.entity;

import com.abaya.picacho.common.entity.EntityBase;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class NotificationReceiver extends EntityBase {
    private String username;
    private Long notificationId;
}
