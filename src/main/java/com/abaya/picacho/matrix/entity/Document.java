package com.abaya.picacho.matrix.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    private String title;

    public Document(String title) {
        this.title = title;
    }

    public Document() {

    }
}
