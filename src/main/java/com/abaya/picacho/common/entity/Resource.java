package com.abaya.picacho.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    private String name;
    private String code;
    private String state;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createDate;

    public Resource() {

    }

    public Resource(String name, String code, String state, Date createDate) {
        this.name = name;
        this.code = code;
        this.state = state;
        this.createDate = createDate;
    }
}
