package com.abaya.picacho.biz.notification.service.impl;

import com.abaya.picacho.biz.announcement.entity.Announcement;
import com.abaya.picacho.biz.announcement.service.AnnouncementService;
import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.common.util.ConversionUtils;
import com.abaya.picacho.biz.notification.model.FatNotification;
import com.abaya.picacho.biz.notification.service.FatNotificationQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class AnnouncementQueryService implements FatNotificationQueryService {
    @Autowired
    private AnnouncementService announcementService = null;

    @Override
    public FatNotification queryFatNotification(Long id) throws ServiceException {
        Assert.notNull(id, "传入的ID不能为空！");

        Announcement announcement = announcementService.queryAnnouncementById(id);
        if (announcement.getId() == null) throw new ServiceException("无法找到对应的数据，请确认数据是否正确！");
        return ConversionUtils.convert(announcement, FatNotification.class);
    }
}
