package com.abaya.picacho.biz.entity;

import com.abaya.picacho.common.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@Entity
public class Regulation extends EntityBase {
    private String name;
    private String type;
    private String state;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime releaseDate;

    public Regulation() {

    }

    public Regulation(String name, String type, String state, LocalDateTime releaseDate) {
        this.name = name;
        this.type = type;
        this.state = state;
        this.releaseDate = releaseDate;
    }
}
