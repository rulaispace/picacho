package com.abaya.picacho.notification.controller;

import com.abaya.picacho.common.model.AuthorizedRequest;
import com.abaya.picacho.common.model.Response;
import com.abaya.picacho.notification.model.NotificationType;
import com.abaya.picacho.notification.service.NotificationService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
public class NotificationController {
    @Autowired
    private NotificationService service = null;

    @Data
    @NoArgsConstructor
    static class NotificationPayload {
        private Long id;
        private String title;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDateTime announceDate;
        @Enumerated(EnumType.STRING)
        private NotificationType type;
        private String announcer;
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "notification/query")
    public Response<List<NotificationPayload>> queryNotifications(@Valid @RequestBody AuthorizedRequest request) throws Exception {
        String username = request.getOperator();
        return Response.success(service.queryAllValidNotifications(username), NotificationPayload.class);
    }

    @Data
    @NoArgsConstructor
    static class NotificationDetailQueryRequest extends AuthorizedRequest {
        @NotNull(message = "待查询公告编号不能为空")
        private Long id;
    }

    @Data
    @NoArgsConstructor
    static class NotificationDetailPayload {
        private Long id;
        private String title;
        private String content;
        private List<FileResourceSegment> attachments;
    }

    @Data
    @NoArgsConstructor
    static class FileResourceSegment {
        private String nickName;
        private String fileName;
        private Long id;
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "notification/query/detail")
    public Response<NotificationDetailPayload> queryNotificationDetail(@Valid @RequestBody NotificationController.NotificationDetailQueryRequest request) throws Exception {
        Long notificationId = request.getId();
        String operator = request.getOperator();
        return Response.success(service.queryFatNotification(notificationId, operator), NotificationDetailPayload.class);
    }
}
