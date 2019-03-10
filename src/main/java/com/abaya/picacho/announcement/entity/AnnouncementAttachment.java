package com.abaya.picacho.announcement.entity;

import com.abaya.picacho.file.entity.FileResource;
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
