package com.abaya.picacho.biz.schedule.entity;

import com.abaya.picacho.biz.file.entity.FileResource;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
public class ScheduleAttachment extends FileResource {
    @ManyToOne
    private Schedule schedule;
}
