package com.abaya.picacho.notification.service;

import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.notification.model.FatNotification;

public interface FatNotificationQueryService {
    FatNotification queryFatNotification(Long id) throws ServiceException;
}
