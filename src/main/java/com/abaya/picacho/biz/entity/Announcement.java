package com.abaya.picacho.biz.entity;

import com.abaya.picacho.common.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@Entity
public class Announcement extends EntityBase {
    private String name;
    private String type;
    private String state;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime releaseDateTime;

    public Announcement() {

    }

    public Announcement(String name, String type, String state, LocalDateTime releaseDateTime) {
        this.name = name;
        this.type = type;
        this.state = state;
        this.releaseDateTime = releaseDateTime;
    }
}
