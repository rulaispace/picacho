package com.abaya.picacho.org.entity;

import com.abaya.picacho.common.entity.EntityBase;
import com.abaya.picacho.org.model.OrgType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Organization extends EntityBase {
    private int level;

    @Column(unique=true)
    private String code;
    private String parentCode;

    @Enumerated(EnumType.STRING)
    private OrgType type;

    private String name;
    private String description;

    public Organization() {
    }

    public Organization(int level, String code, String parentCode, OrgType type, String name, String description) {
        this.level = level;
        this.code = code;
        this.parentCode = parentCode;
        this.type = type;
        this.name = name;
        this.description = description;
    }

}
