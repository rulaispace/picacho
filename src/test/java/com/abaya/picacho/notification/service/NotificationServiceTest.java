package com.abaya.picacho.notification.service;

import com.abaya.picacho.announcement.entity.Announcement;
import com.abaya.picacho.announcement.entity.AnnouncementAttachment;
import com.abaya.picacho.announcement.model.AnnouncementState;
import com.abaya.picacho.announcement.model.AnnouncementType;
import com.abaya.picacho.announcement.repository.AnnouncementRepository;
import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.common.util.EntityUtils;
import com.abaya.picacho.common.util.RandomUtils;
import com.abaya.picacho.notification.entity.Notification;
import com.abaya.picacho.notification.model.NotificationState;
import com.abaya.picacho.notification.repository.NotificationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NotificationServiceTest {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    public void sendNullAsNotification() throws ServiceException {
        boolean success = notificationService.sendAnnouncement(null);
        assertThat(success, is(true));
    }

    @Test(expected = ServiceException.class)
    public void sendIdNullNotification() throws ServiceException {
        Announcement announcement = new Announcement();
        notificationService.sendAnnouncement(announcement);
    }

    @Test
    public void sendNormalNotificationWithoutAttachment() throws ServiceException {
        Announcement announcement = createAnnouncement();
        boolean success = notificationService.sendAnnouncement(announcement);
        assertThat(success, is(true));
    }

    @Test
    public void sendNormalNotificationWithAttachment() throws ServiceException {
        Announcement announcement = createAnnouncement();
        announcement.setAttachments(createAttachments());

        boolean success = notificationService.sendAnnouncement(announcement);
        assertThat(success, is(true));
    }

    @Test
    public void callbackNotification() throws ServiceException {
        Announcement announcement = createAnnouncement();
        announcement.setAttachments(createAttachments());

        boolean success = notificationService.sendAnnouncement(announcement);
        assertThat(success, is(true));

        success = notificationService.callbackAnnouncement(announcement);
        assertThat(success, is(true));

        Notification notification = notificationRepository.findBySourceId(announcement.getId());
        assertThat(notification.getState(), is(NotificationState.invalid));
    }

    @Test
    public void queryNotification() throws ServiceException {
        List<Notification> notifications = notificationService.queryAllValidNotifications("ZHANGSAN");
        assertThat(notifications, is(not(empty())));
    }

    private Announcement createAnnouncement() {
        Announcement announcement = new Announcement();
        announcement.setTitle(MessageFormat.format("JUnit Testing[{0}]", RandomUtils.uuid()));
        announcement.setContent(MessageFormat.format("Junit Testing content[{0}]", RandomUtils.uuid()));
        announcement.setReleaseDateTime(LocalDateTime.now());
        announcement.setState(AnnouncementState.released);
        announcement.setType(AnnouncementType.announcement);

        announcement = EntityUtils.initEntity(announcement);

        return announcementRepository.save(announcement);
    }

    private List<AnnouncementAttachment> createAttachments() {
        return IntStream.range(1, 3)
                .mapToObj(i -> {
                    AnnouncementAttachment attachment = new AnnouncementAttachment();
                    attachment = EntityUtils.initEntity(attachment);

                    attachment.setNickName(MessageFormat.format("JunitTestFile[{0}].pdf", RandomUtils.uuid()));
                    attachment.setFileName(MessageFormat.format("{0}.pdf", RandomUtils.uuid()));

                    return attachment;
                })
                .collect(Collectors.toList());
    }
}