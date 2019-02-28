package com.abaya.picacho.biz.entity;

import com.abaya.picacho.common.entity.EntityBase;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Schedule extends EntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    private int time;
    private String event;

    public Schedule(int time, String event) {
        this.time = time;
        this.event = event;
    }

    public Schedule() {

    }
}
