package com.abaya.picacho.common.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Schedule {
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
