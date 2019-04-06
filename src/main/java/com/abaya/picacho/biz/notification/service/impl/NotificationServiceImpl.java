package com.abaya.picacho.biz.notification.service.impl;

import com.abaya.picacho.biz.announcement.entity.Announcement;
import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.common.util.EntityUtils;
import com.abaya.picacho.biz.notification.entity.Notification;
import com.abaya.picacho.biz.notification.entity.NotificationReceiver;
import com.abaya.picacho.biz.notification.model.FatNotification;
import com.abaya.picacho.biz.notification.model.NotificationState;
import com.abaya.picacho.biz.notification.model.NotificationType;
import com.abaya.picacho.biz.notification.model.SourceType;
import com.abaya.picacho.biz.notification.repository.NotificationReceiverRepository;
import com.abaya.picacho.biz.notification.repository.NotificationRepository;
import com.abaya.picacho.biz.notification.service.FatNotificationService;
import com.abaya.picacho.biz.notification.service.NotificationService;
import com.abaya.picacho.biz.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private AccountService accountService = null;

    @Autowired
    private NotificationRepository repository = null;

    @Autowired
    private NotificationReceiverRepository receiverRepository = null;

    @Autowired
    private FatNotificationService fatNotificationService = null;

    @Override
    public List<Notification> queryAll() {
        List<Notification> notifications = repository.findAll();
        return notifications;
    }

    @Override
    @Transactional
    public boolean sendAnnouncement(Announcement announcement) throws ServiceException {
        if (announcement == null) {
            log.warn("待发布的公告为null，请确认逻辑");
            return true;
        }

        if (announcement.getId() == null) throw new ServiceException("公告编号为空，无法发布");
        if (repository.existsBySourceIdAndState(announcement.getId(), NotificationState.valid))
            throw new ServiceException("公告已发布，无法再次发布！");

        Notification notification = saveAsNotification(announcement);
        notifyReceiver(notification);

        return true;
    }

    private Notification saveAsNotification(Announcement announcement) throws ServiceException {
        Notification notification = new Notification();
        notification = EntityUtils.entityPropertyMerge(notification, announcement);
        notification.setAnnouncer(accountService.queryNameByUsername(announcement.getModifier()));
        notification.setAnnounceDate(LocalDateTime.now());
        notification.setState(NotificationState.valid);
        notification.setType(NotificationType.announcement);
        notification.setSourceId(announcement.getId());
        notification.setSourceType(SourceType.announcement);
        return repository.save(notification);
    }

    private void notifyReceiver(Notification notification) {
        List<NotificationReceiver> receivers = new ArrayList<>();
        accountService.queryAllAccount().stream().forEach(account -> {
            NotificationReceiver receiver = new NotificationReceiver();
            receiver = EntityUtils.entityPropertyMerge(receiver, notification);
            receiver.setUsername(account.getUsername());
            receiver.setNotificationId(notification.getId());

            receivers.add(receiver);
        });
        receiverRepository.saveAll(receivers);
    }

    @Override
    public boolean callbackAnnouncement(Announcement announcement) throws ServiceException {
        if (announcement == null) {
            log.warn("待撤销的公告为空，请确认逻辑是否正确");
            return true;
        }

        if (announcement.getId() == null) throw new ServiceException("传入待撤销通知无主键编号，无法撤销");
        Notification notification = repository.findBySourceId(announcement.getId());
        if (notification == null) {
            log.warn("未找到待撤销通知，请确认逻辑是否正确");
            return true;
        }

        notification.setState(NotificationState.invalid);
        repository.save(notification);

        return true;
    }

    @Override
    @Transactional
    public List<Notification> queryAllValidNotifications(String username) throws ServiceException {
        if (username == null) throw new ServiceException("传入用户名为空，请检查是否存在异常！");
        List<Long> notificationIdList = receiverRepository.findAllByUsername(username).stream()
                .map(receiver -> receiver.getNotificationId())
                .collect(Collectors.toList());

        return repository.findAllByIdInAndStateOrderByCreateDateTimeDesc(notificationIdList, NotificationState.valid);
    }

    @Override
    public FatNotification queryFatNotification(Long notificationId, String operator) throws ServiceException {
        Assert.notNull(notificationId, "公告编号不能为空");
        Assert.notNull(operator, "操作人员不能为空");

        if (!receiverRepository.existsByNotificationIdAndUsername(notificationId, operator))
            throw new ServiceException(format("用户[{0}]不具有查看公告[{1}]的权限，无法调阅详细信息", accountService.queryNameByUsername(operator), this.queryTitleById(notificationId)));

        Notification notification = repository.findById(notificationId).orElse(new Notification());
        if (notification == null || notification.getState() != NotificationState.valid)
            throw new ServiceException(format("公告[{0}]不存在或者状态不正确，无法调阅详细信息", notificationId));

        return fatNotificationService.queryFatNotification(notification);
    }

    private String queryTitleById(Long notificationId) {
        Assert.notNull(notificationId, "公告编号不能为空");
        Notification notification = repository.findById(notificationId).orElse(new Notification());
        return notification.getTitle();
    }
}
