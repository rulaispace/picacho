package com.abaya.picacho.biz.schedule.entity;

import com.abaya.picacho.common.entity.EntityBase;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ScheduleParticipant extends EntityBase {
    private String username;

    @ManyToOne
    private Schedule schedule;
}
