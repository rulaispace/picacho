package com.abaya.picacho.biz.notification.service;

import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.biz.notification.model.FatNotification;

public interface FatNotificationQueryService {
    FatNotification queryFatNotification(Long id) throws ServiceException;
}
