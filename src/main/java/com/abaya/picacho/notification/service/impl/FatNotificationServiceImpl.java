package com.abaya.picacho.notification.service.impl;

import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.common.util.EntityUtils;
import com.abaya.picacho.notification.entity.Notification;
import com.abaya.picacho.notification.model.FatNotification;
import com.abaya.picacho.notification.model.SourceType;
import com.abaya.picacho.notification.service.FatNotificationService;
import com.abaya.picacho.notification.service.FatNotificationServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class FatNotificationServiceImpl implements FatNotificationService {
    @Autowired
    private FatNotificationServiceFactory serviceFactory = null;

    @Override
    public FatNotification queryFatNotification(Notification notification) throws ServiceException {
        Assert.notNull(notification, "传入的公告对应不能为空");

        SourceType sourceType = notification.getSourceType();
        FatNotification result = serviceFactory.getQueryService(sourceType).queryFatNotification(notification.getSourceId());
        return EntityUtils.entityMerge(result, notification);
    }
}
