package com.abaya.picacho.biz.announcement.service.impl;

import com.abaya.picacho.biz.announcement.entity.Announcement;
import com.abaya.picacho.biz.announcement.entity.AnnouncementAttachment;
import com.abaya.picacho.biz.announcement.model.AnnouncementState;
import com.abaya.picacho.biz.announcement.repository.AnnouncementRepository;
import com.abaya.picacho.biz.announcement.repository.AnnouncementAttachmentRepository;
import com.abaya.picacho.biz.announcement.service.AnnouncementService;
import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.common.util.EntityUtils;
import com.abaya.picacho.biz.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private AnnouncementAttachmentRepository attachmentRepository;

    @Override
    public AnnouncementAttachment addAttachment(AnnouncementAttachment attachment) {
        Assert.notNull(attachment, "附件内容不能为空！");
        return attachmentRepository.save(attachment);
    }

    @Override
    public AnnouncementAttachment queryAttachment(Long id, String operator) {
        Assert.notNull(id, "附件编号不能为空");
        Assert.notNull(operator, "操作人员不能为空");
        Optional<AnnouncementAttachment> attachmentOptional = attachmentRepository.findById(id);
        if (attachmentOptional.isPresent()) return attachmentOptional.get();

        return null;
    }

    @Override
    public void deleteAttachment(Long id, String operator) {
        Assert.notNull(id, "附件编号不能为空");
        Assert.notNull(operator, "操作人员不能为空");
        attachmentRepository.deleteById(id);
    }

    @Override
    public Announcement releaseAnnouncement(Long id, String operator) throws ServiceException {
        Assert.notNull(id, "待发布公告的编号不能为空");
        Assert.notNull(operator, "操作人员不能为空");

        Announcement announcement = announcementRepository.findById(id).orElse(new Announcement());
        if (announcement.getId() == null) throw new ServiceException("未找到编号(%d)的公告，无法发布！", id);

        if (announcement.getState() != AnnouncementState.inEdit) throw new ServiceException("待发布公告状态不正确，无法发布！");

        announcement.setState(AnnouncementState.released);
        announcement.setReleaseDateTime(LocalDateTime.now());
        announcement.setModifier(operator);

        if (!notificationService.sendAnnouncement(announcement)) throw new ServiceException("发布公告失败！");
        return announcementRepository.save(announcement);
    }

    @Override
    public Announcement callbackAnnouncement(Long id, String operator) throws ServiceException {
        Assert.notNull(operator, "操作人员不能为空");
        Assert.notNull(id, "待发布公告的编号不能为空");

        Announcement announcement = announcementRepository.findById(id).orElse(new Announcement());
        if (announcement.getId() == null) throw new ServiceException("未找到编号(%d)的公告，无法发布！", id);

        if (announcement.getState() != AnnouncementState.released) throw new ServiceException("待撤回公告状态不正确，无法撤回！");

        announcement.setState(AnnouncementState.inEdit);
        announcement.setModifier(operator);

        if (!notificationService.callbackAnnouncement(announcement)) throw new ServiceException("公告撤回失败！");
        return announcementRepository.save(announcement);
    }

    @Override
    public Announcement queryAnnouncementById(Long id) {
        Assert.notNull(id, "传入的记录编号不能为空！");
        return announcementRepository.findById(id).orElse(new Announcement());
    }

    @Override
    @Transactional
    public Announcement addAnnouncement(Announcement announcement) throws ServiceException {
        Assert.notNull(announcement, "传入的待新增公告不能为空");
        Assert.isNull(announcement.getId(), "待新增的公告不能包含主键信息");

        Announcement localAnnouncement = announcementRepository.save(announcement);
        if (localAnnouncement == null) throw new ServiceException("公告存储失败，无法存储公告信息！");

        updateAttachments(localAnnouncement, announcement);
        return localAnnouncement;
    }

    @Override
    public void deleteAnnouncement(Long id, String operator) throws ServiceException {
        Assert.notNull(id, "传入的记录编号不能为空");
        Assert.notNull(operator, "传入的操作员不能为空");

        Announcement announcement = announcementRepository.findById(id).orElse(new Announcement());
        if (announcement.getId() == null) throw new ServiceException("记录(%d)不存在", id);

        if (announcement.getState() != AnnouncementState.inEdit) throw new ServiceException("公告状态非可编辑状态，无法执行删除操作！");

        attachmentRepository.deleteAllByAnnouncement(announcement);
        announcementRepository.delete(announcement);
    }

    @Override
    public Announcement updateAnnouncement(Announcement announcement) throws ServiceException {
        Assert.notNull(announcement, "待修改的公告不能为空");
        Assert.notNull(announcement.getId(), "待修改数据的主键不能为空");

        Long id = announcement.getId();
        Announcement localAnnouncement = announcementRepository.findById(id).orElse(new Announcement());
        if (localAnnouncement.getId() == null) throw new ServiceException("公告(%d)不存在, 请确认传入参数是否正确", id);
        if (localAnnouncement.getState() != AnnouncementState.inEdit) throw new ServiceException("公告(%d)状态不正确，无法修改", id);

        updateAttachments(localAnnouncement, announcement);
        return announcementRepository.save(EntityUtils.entityUpdateMerge(localAnnouncement, announcement));
    }

    private void updateAttachments(Announcement localAnnouncement, Announcement updateAnnouncement) throws ServiceException {
        // 将原有附件全部清空
        List<AnnouncementAttachment> originAttachments =  attachmentRepository.findAllByAnnouncement(localAnnouncement);
        originAttachments.forEach(attachment -> {attachment.setAnnouncement(null);});
        attachmentRepository.saveAll(originAttachments);

        List<AnnouncementAttachment> newAttachments = new ArrayList<>();
        for (AnnouncementAttachment attachment : updateAnnouncement.getAttachments()) {
            AnnouncementAttachment localAttachment = attachmentRepository.findByNickNameAndFileName(attachment.getNickName(), attachment.getFileName());
            if (localAttachment == null) throw new ServiceException("未找到需要存储的附件内容");

            localAttachment.setAnnouncement(localAnnouncement);
            newAttachments.add(localAttachment);
        }

        attachmentRepository.saveAll(newAttachments);
    }

    @Override
    public List<Announcement> queryAll() {
        return announcementRepository.findAllByOrderByUpdateDateTimeDesc();
    }
}
