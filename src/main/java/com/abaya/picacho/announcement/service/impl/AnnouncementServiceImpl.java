package com.abaya.picacho.announcement.service.impl;

import com.abaya.picacho.announcement.entity.Announcement;
import com.abaya.picacho.announcement.entity.AnnouncementAttachment;
import com.abaya.picacho.announcement.model.AnnouncementState;
import com.abaya.picacho.announcement.repository.AnnouncementRepository;
import com.abaya.picacho.announcement.repository.AttachmentRepository;
import com.abaya.picacho.announcement.service.AnnouncementService;
import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.common.util.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private AttachmentRepository attachmentRepository;

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

        if (announcement.getState() != AnnouncementState.inEdit) throw  new ServiceException("待发布公告状态不正确，无法发布！");

        announcement.setState(AnnouncementState.released);
        announcement.setReleaseDateTime(LocalDateTime.now());
        announcement.setModifier(operator);

        return announcementRepository.save(announcement);
    }

    @Override
    public Announcement callbackAnnouncement(Long id, String operator) throws ServiceException {
        Assert.notNull(operator, "操作人员不能为空");
        Assert.notNull(id, "待发布公告的编号不能为空");

        Announcement announcement = announcementRepository.findById(id).orElse(new Announcement());
        if (announcement.getId() == null) throw new ServiceException("未找到编号(%d)的公告，无法发布！", id);

        if (announcement.getState() != AnnouncementState.released) throw  new ServiceException("待撤回公告状态不正确，无法撤回！");

        announcement.setState(AnnouncementState.inEdit);
        announcement.setModifier(operator);

        return announcementRepository.save(announcement);
    }

    @Override
    public Announcement addAnnouncement(Announcement announcement) throws ServiceException {
        Assert.notNull(announcement, "传入的待新增公告不能为空");
        Assert.isNull(announcement.getId(), "待新增的公告不能包含主键信息");

        Announcement localAnnouncement = announcementRepository.save(announcement);
        if (localAnnouncement == null) throw new ServiceException("公告存储失败，无法存储公告信息！");
        announcement.getAttachments().forEach(attachment -> {
            attachment.setAnnouncement(localAnnouncement);
        });
        attachmentRepository.saveAll(announcement.getAttachments());

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
        return announcementRepository.save(PropertyUtils.entityUpdateMerge(localAnnouncement, announcement));
    }

    private void updateAttachments(Announcement localAnnouncement, Announcement updateAnnouncement) {
        attachmentRepository.deleteAllByAnnouncement(localAnnouncement);
        localAnnouncement.getAttachments().clear();

        Set<AnnouncementAttachment> attachments = updateAnnouncement.getAttachments();
        attachments.forEach(attachment -> {
            attachment.setAnnouncement(localAnnouncement);
        });
        attachmentRepository.saveAll(attachments).forEach(attachment -> {
            localAnnouncement.getAttachments().add(attachment);
        });
    }

    @Override
    public List<Announcement> queryAll() {
        return announcementRepository.findAll();
    }
}
