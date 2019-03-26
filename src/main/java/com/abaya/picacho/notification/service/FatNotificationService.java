package com.abaya.picacho.notification.service;

import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.notification.entity.Notification;
import com.abaya.picacho.notification.model.FatNotification;

public interface FatNotificationService {
    FatNotification queryFatNotification(Notification notification) throws ServiceException;
}
