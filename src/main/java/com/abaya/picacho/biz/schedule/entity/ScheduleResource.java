package com.abaya.picacho.biz.schedule.entity;

import com.abaya.picacho.common.entity.EntityBase;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
public class ScheduleResource extends EntityBase {
    private Long resourceId;

    @ManyToOne
    private Schedule schedule;
}
