package com.abaya.picacho.org.entity;

import com.abaya.picacho.org.model.NodeType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    private int level;

    @Column(unique=true)
    private String code;
    private String parentCode;

    @Enumerated(EnumType.STRING)
    private NodeType type;

    private String name;
    private String description;

    public Organization() {
    }

    public Organization(int level, String code, String parentCode, NodeType type, String name, String description) {
        this.level = level;
        this.code = code;
        this.parentCode = parentCode;
        this.type = type;
        this.name = name;
        this.description = description;
    }

}
