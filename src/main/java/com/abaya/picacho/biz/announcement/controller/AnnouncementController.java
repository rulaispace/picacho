package com.abaya.picacho.biz.announcement.controller;

import com.abaya.picacho.biz.announcement.entity.Announcement;
import com.abaya.picacho.biz.announcement.entity.AnnouncementAttachment;
import com.abaya.picacho.biz.announcement.model.AnnouncementState;
import com.abaya.picacho.biz.announcement.model.AnnouncementType;
import com.abaya.picacho.biz.announcement.service.AnnouncementService;
import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.common.model.AuthorizedRequest;
import com.abaya.picacho.common.model.Response;
import com.abaya.picacho.common.util.AuthorizeUtils;
import com.abaya.picacho.common.util.ConversionUtils;
import com.abaya.picacho.biz.file.model.FileIndicator;
import com.abaya.picacho.biz.file.service.FileStorageService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
public class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService = null;
    @Autowired
    private FileStorageService fileStorageService = null;

    @Data
    @NoArgsConstructor
    static class AttachmentPayload {
        private Long id;
        private String nickName;
        private String fileName;
    }

    @Data
    @NoArgsConstructor
    static class AttachmentDeleteRequest extends AuthorizedRequest {
        @NotNull(message = "附件编号不能为空！")
        private Long id;
    }

    @Data
    @NoArgsConstructor
    static class AttachmentSegment {
        @NotNull(message = "文件名不能为空")
        private String nickName;
        @NotNull(message = "请求uri标识不能为空")
        private String fileName;
    }

    @Data
    static class AnnouncementAddRequest extends AuthorizedRequest {
        @NotNull(message = "发文标题不能为空")
        private String title;
        @NotNull(message = "发文内容不能为空")
        private String content;
        @NotNull(message = "发文类型不能为空")
        private AnnouncementType type;
        @NotNull(message = "发文状态不能为空")
        private AnnouncementState state;
        private List<AttachmentSegment> attachments;
    }

    @Data
    @NoArgsConstructor
    static class AnnouncementCommonRequest extends AuthorizedRequest {
        @NotNull(message = "发文编号不能为空！")
        private Long id;
    }

    @Data
    @NoArgsConstructor
    static class AnnouncementModifyRequest extends AnnouncementCommonRequest {
        private String title;
        private String content;
        private AnnouncementType type;
        private AnnouncementState state;
        private List<AttachmentSegment> attachments;
    }

    @Data
    @NoArgsConstructor
    static class AnnouncementPayload {
        private Long id;
        private String title;
        private String content;
        private AnnouncementType type;
        private AnnouncementState state;
        private Set<AttachmentPayload> attachments;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        protected LocalDateTime createDateTime;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        protected LocalDateTime updateDateTime;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        protected LocalDateTime releaseDateTime;
    }

    @CrossOrigin
    @PostMapping(value = "announcement/attachment/upload/{token}")
    public Response<AttachmentPayload> uploadAttachment(@RequestParam("file") MultipartFile file, @PathVariable String token) throws ServiceException {
        String operator = AuthorizeUtils.getUsernameByToken(token);
        FileIndicator fileIndicator = fileStorageService.storeFile(file, operator);

        AnnouncementAttachment attachment = ConversionUtils.convert(fileIndicator, AnnouncementAttachment.class);
        attachment.setCreator(operator);
        attachment.setModifier(operator);

        return Response.success(announcementService.addAttachment(attachment), AttachmentPayload.class);
    }

    @CrossOrigin
    @PostMapping(value = "announcement/attachment/delete")
    public Response deleteAttachment(@Valid @RequestBody AttachmentDeleteRequest request) throws ServiceException {
        AnnouncementAttachment attachment = announcementService.queryAttachment(request.getId(), request.getOperator());
        if (attachment == null) throw new ServiceException("附件(%d)不存在！", request.getId());

        String fileName = attachment.getFileName();
        if (!fileStorageService.deleteFileInLocalStorage(fileName)) throw new ServiceException("本地文件删除失败！");

        announcementService.deleteAttachment(request.getId(), request.getOperator());

        return Response.success("附件删除成功！");
    }

    @CrossOrigin
    @PostMapping(value = "announcement/add")
    public Response<AnnouncementPayload> addAnnouncement(@Valid @RequestBody AnnouncementAddRequest request) throws Exception {
        Announcement announcement = ConversionUtils.convert(request, Announcement.class);
        return Response.success(announcementService.addAnnouncement(announcement), AnnouncementPayload.class);
    }

    @CrossOrigin
    @PostMapping(value = "announcement/delete")
    public Response deleteAnnouncement(@Valid @RequestBody AnnouncementController.AnnouncementCommonRequest request) throws ServiceException {
        announcementService.deleteAnnouncement(request.getId(), request.getOperator());
        return Response.success("资源(%s)删除成功", request.getId());
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "announcement/modify")
    public Response<AnnouncementPayload> modifyAnnouncement(@Valid @RequestBody AnnouncementModifyRequest request) throws Exception {
        Announcement announcement = ConversionUtils.convert(request, Announcement.class);
        return Response.success(announcementService.updateAnnouncement(announcement), AnnouncementPayload.class);
    }

    @CrossOrigin
    @PostMapping(value = "announcement/query")
    public Response<List<AnnouncementPayload>> queryAnnouncement(@Valid @RequestBody AuthorizedRequest request) throws Exception {
        return Response.success(announcementService.queryAll(), AnnouncementPayload.class);
    }

    @CrossOrigin
    @PostMapping(value = "announcement/release")
    public Response<AnnouncementPayload> releaseAnnouncement(@Valid @RequestBody AnnouncementCommonRequest request) throws Exception {
        return Response.success(announcementService.releaseAnnouncement(request.getId(), request.getOperator()), AnnouncementPayload.class);
    }

    @CrossOrigin
    @PostMapping(value = "announcement/callback")
    public Response<AnnouncementPayload> callbackAnnouncement(@Valid @RequestBody AnnouncementCommonRequest request) throws Exception {
        return Response.success(announcementService.callbackAnnouncement(request.getId(), request.getOperator()), AnnouncementPayload.class);
    }
}
