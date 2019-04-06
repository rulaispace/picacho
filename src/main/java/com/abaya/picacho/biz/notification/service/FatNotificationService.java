package com.abaya.picacho.biz.notification.service;

import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.biz.notification.entity.Notification;
import com.abaya.picacho.biz.notification.model.FatNotification;

public interface FatNotificationService {
    FatNotification queryFatNotification(Notification notification) throws ServiceException;
}
