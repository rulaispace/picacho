package com.abaya.picacho.biz.schedule.entity;

import com.abaya.picacho.common.entity.EntityBase;
import lombok.Data;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Schedule extends EntityBase {
    private String title;
    private String content;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;

    @ToString.Exclude
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleParticipant> participants;

    @ToString.Exclude
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleAttachment> attachments;

    @ToString.Exclude
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleResource> resources;
}
