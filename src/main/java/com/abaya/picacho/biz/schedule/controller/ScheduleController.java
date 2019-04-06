package com.abaya.picacho.biz.schedule.controller;

import com.abaya.picacho.biz.file.model.FileIndicator;
import com.abaya.picacho.biz.file.service.FileStorageService;
import com.abaya.picacho.biz.schedule.entity.Schedule;
import com.abaya.picacho.biz.schedule.entity.ScheduleAttachment;
import com.abaya.picacho.biz.schedule.service.ScheduleService;
import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.common.model.AuthorizedRequest;
import com.abaya.picacho.common.model.Response;
import com.abaya.picacho.common.util.AuthorizeUtils;
import com.abaya.picacho.common.util.ConversionUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService = null;

    @Autowired
    private FileStorageService fileStorageService = null;

    @Data
    static class ScheduleQueryRequest extends AuthorizedRequest {
        private String username;
    }

    @Data
    static class ParticipantSegment {
        @NotNull(message = "传入的参加人员的登录名不能为空！")
        private String username;
    }

    @Data
    static class FileResourceSegment {
        @NotNull(message = "文件名称不能为空")
        private String nickName;
        @NotNull(message = "存储路径不能为空")
        private String fileName;
    }

    @Data
    static class ResourceSegment {
        @NotNull(message = "资源编号不能为空")
        private Long resourceId;
    }

    @Data
    static class SchedulePayload {
        private Long id;
        private String title;
        private String content;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime startTime;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime finishTime;
        private List<ParticipantSegment> participants;
        private List<FileResourceSegment> attachments;
        private List<ResourceSegment> resources;
    }

    @CrossOrigin
    @PostMapping(value = "schedule/query")
    public Response<List<SchedulePayload>> querySelfSchedule(@Valid @RequestBody ScheduleQueryRequest request) {
        String username = request.getUsername();
        if (username == null) username = request.getOperator();

        return Response.success(scheduleService.queryAllSchedules(username), SchedulePayload.class);
    }

    @Data
    static class SingleScheduleQueryRequest extends AuthorizedRequest {
        @NotNull(message = "主键编号不能为空")
        private Long id;
    }


    @CrossOrigin
    @PostMapping(value = "schedule/query/single")
    public Response<SchedulePayload> querySingleSchedule(@Valid @RequestBody SingleScheduleQueryRequest request) throws ServiceException {
        return Response.success(scheduleService.querySingleSchedule(request.getId()), SchedulePayload.class);
    }

    @Data
    static class ScheduleAddRequest extends AuthorizedRequest {
        @NotNull(message = "标题不能为空")
        private String title;
        private String content;
        @NotNull(message = "开始时间不能为空")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime startTime;
        @NotNull(message = "结束时间不能为空")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime finishTime;
        @NotEmpty(message = "参加人员不能为空")
        private List<ParticipantSegment> participants;
        private List<FileResourceSegment> attachments;
        private List<ResourceSegment> resources;
    }

    @Data
    static class ScheduleAddPayload {
        private Long id;
        private String title;
    }

    @CrossOrigin
    @PostMapping(value = "schedule/add")
    public Response<ScheduleAddPayload> addSchedule(@Valid @RequestBody ScheduleAddRequest request) throws ServiceException {
        Schedule schedule = ConversionUtils.convert(request, Schedule.class);
        schedule.getResources().forEach(resource -> resource.setId(null));

        return Response.success(scheduleService.insertSchedule(schedule), ScheduleAddPayload.class);
    }

    @Data
    static class IdleElementQueryRequest extends AuthorizedRequest {
        @NotNull(message = "开始时间不能为空")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime startTime;
        @NotNull(message = "结束时间不能为空")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime finishTime;
    }

    @Data
    static class IdleAccountPayload {
        private String username;
        private String name;
    }

    @CrossOrigin
    @PostMapping(value = "schedule/query/account/idle")
    public Response<List<IdleAccountPayload>> queryIdleAccount(@Valid @RequestBody IdleElementQueryRequest request) {
        return Response.success(scheduleService.queryIdleAccount(request.getStartTime(), request.getFinishTime()), IdleAccountPayload.class);
    }

    @Data
    static class IdleResourcePayload {
        private Long id;
        private String name;
        private String code;
    }

    @CrossOrigin
    @PostMapping(value = "schedule/query/resource/idle")
    public Response<List<IdleResourcePayload>> queryIdleResource(@Valid @RequestBody IdleElementQueryRequest request) {
        return Response.success(scheduleService.queryIdleResource(request.getStartTime(), request.getFinishTime()), IdleResourcePayload.class);
    }

    @Data
    @NoArgsConstructor
    static class AttachmentPayload {
        private Long id;
        private String nickName;
        private String fileName;
    }

    @CrossOrigin
    @PostMapping(value = "schedule/attachment/upload/{token}")
    public Response<AttachmentPayload> uploadAttachment(@RequestParam("file") MultipartFile file, @PathVariable String token) throws ServiceException {
        String operator = AuthorizeUtils.getUsernameByToken(token);
        FileIndicator fileIndicator = fileStorageService.storeFile(file, operator);

        ScheduleAttachment attachment = ConversionUtils.convert(fileIndicator, ScheduleAttachment.class);
        attachment.setCreator(operator);
        attachment.setModifier(operator);

        return Response.success(scheduleService.addAttachment(attachment), AttachmentPayload.class);
    }

    @Data
    @NoArgsConstructor
    static class AttachmentDeleteRequest extends AuthorizedRequest {
        @NotNull(message = "附件编号不能为空！")
        private Long id;
    }

    @CrossOrigin
    @PostMapping(value = "schedule/attachment/delete")
    public Response deleteAttachment(@Valid @RequestBody AttachmentDeleteRequest request) throws ServiceException {
        ScheduleAttachment attachment = scheduleService.queryAttachment(request.getId(), request.getOperator());
        if (attachment == null) throw new ServiceException("附件(%d)不存在！", request.getId());

        String fileName = attachment.getFileName();
        if (!fileStorageService.deleteFileInLocalStorage(fileName)) throw new ServiceException("本地文件删除失败！");

        scheduleService.deleteAttachment(request.getId(), request.getOperator());

        return Response.success("附件删除成功！");
    }

    @CrossOrigin
    @PostMapping(value = "schedule/modify")
    public Response modifySchedule() {
        return null;
    }

    @CrossOrigin
    @PostMapping(value = "schedule/delete")
    public Response deleteSchedule() {
        return null;
    }
}
