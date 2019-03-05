package com.abaya.picacho.resource.entity;

import com.abaya.picacho.common.entity.EntityBase;
import com.abaya.picacho.common.model.CommonState;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Entity
public class Resource extends EntityBase {
    private String name;

    @Column(unique=true)
    private String code;
    @Enumerated(EnumType.STRING)
    private CommonState state = CommonState.valid;

    public Resource() {

    }

    public Resource(String name, String code, CommonState state) {
        this.name = name;
        this.code = code;
        this.state = state;
    }
}
