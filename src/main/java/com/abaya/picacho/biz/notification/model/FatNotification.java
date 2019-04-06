package com.abaya.picacho.biz.notification.model;

import com.abaya.picacho.biz.file.entity.FileResource;
import com.abaya.picacho.biz.notification.entity.Notification;
import lombok.Data;

import java.util.List;

@Data
public class FatNotification extends Notification {
    private String content;
    private List<FileResource> attachments;
}
