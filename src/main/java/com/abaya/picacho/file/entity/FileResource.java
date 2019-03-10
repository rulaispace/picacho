package com.abaya.picacho.file.entity;

import com.abaya.picacho.common.entity.EntityBase;
import lombok.Data;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class FileResource extends EntityBase {
    protected String nickName;
    protected String fileName;
}
