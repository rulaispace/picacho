package com.abaya.picacho.notification.service;

import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.notification.model.SourceType;

public interface FatNotificationServiceFactory {
    FatNotificationQueryService getQueryService(SourceType sourceType) throws ServiceException;
}
