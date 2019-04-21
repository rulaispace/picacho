package com.abaya.picacho.biz.organization.entity;

import com.abaya.picacho.common.entity.EntityBase;
import com.abaya.picacho.biz.organization.model.OrgType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Organization extends EntityBase {
    private Integer level;

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
        setLevel(level);
        setCode(code);
        setParentCode(parentCode);
        setType(type);
        setName(name);
        setDescription(description);
    }


    public void setCode(String code) {
        this.code = code == null ? null : code.toUpperCase();
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode == null ? null : parentCode.toUpperCase();
    }
}
