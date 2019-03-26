package com.abaya.picacho.notification.model;

import com.abaya.picacho.file.entity.FileResource;
import com.abaya.picacho.notification.entity.Notification;
import lombok.Data;

import java.util.List;

@Data
public class FatNotification extends Notification {
    private String content;
    private List<FileResource> attachments;
}
