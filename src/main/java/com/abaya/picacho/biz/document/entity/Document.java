package com.abaya.picacho.biz.document.entity;

import com.abaya.picacho.common.entity.EntityBase;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Document extends EntityBase {
    private String title;

    public Document(String title) {
        this.title = title;
    }

    public Document() {

    }
}
