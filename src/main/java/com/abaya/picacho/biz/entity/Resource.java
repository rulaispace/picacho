package com.abaya.picacho.biz.entity;

import com.abaya.picacho.common.entity.EntityBase;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Resource extends EntityBase {
    private String name;
    private String code;
    private String state;

    public Resource() {

    }

    public Resource(String name, String code, String state) {
        this.name = name;
        this.code = code;
        this.state = state;
    }
}
