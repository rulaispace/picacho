package com.abaya.picacho.biz.notification.service.impl;

import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.biz.notification.model.SourceType;
import com.abaya.picacho.biz.notification.service.FatNotificationQueryService;
import com.abaya.picacho.biz.notification.service.FatNotificationServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class FatNotificationServiceFactoryImpl implements FatNotificationServiceFactory {
    @Autowired
    private ApplicationContext applicationContext = null;

    @Override
    public FatNotificationQueryService getQueryService(SourceType sourceType) throws ServiceException {
        if (sourceType == null) throw new ServiceException("传入类型为空，无法找到对应的查询类");

        if (SourceType.announcement == sourceType)
            return applicationContext.getBean(AnnouncementQueryService.class);

        throw new ServiceException("传入的类型无法确认，无法找到对应的查询类");
    }
}
