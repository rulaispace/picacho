package com.abaya.picacho.biz.notification.service;

import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.biz.notification.model.SourceType;

public interface FatNotificationServiceFactory {
    FatNotificationQueryService getQueryService(SourceType sourceType) throws ServiceException;
}
