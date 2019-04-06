package com.abaya.picacho.biz.announcement.entity;

import com.abaya.picacho.biz.file.entity.FileResource;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class AnnouncementAttachment extends FileResource {
    @ManyToOne
    private Announcement announcement;
}
