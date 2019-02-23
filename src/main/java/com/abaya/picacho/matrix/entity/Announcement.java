package com.abaya.picacho.matrix.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long key;

    private String name;
    private String type;
    private String state;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date releaseDate;

    public Announcement() {

    }

    public Announcement(String name, String type, String state, Date releaseDate) {
        this.name = name;
        this.type = type;
        this.state = state;
        this.releaseDate = releaseDate;
    }
}