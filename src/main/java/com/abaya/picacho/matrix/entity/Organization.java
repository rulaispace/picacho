package com.abaya.picacho.matrix.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Organization {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long key;

  private int level;
  private String orgId;
  private String parentOrgId;
  private String type;
  private String name;
  private String description;

  public Organization() {}

  public Organization(int level, String orgId, String parentOrgId, String type, String name, String description) {
    this.level = level;
    this.orgId = orgId;
    this.parentOrgId = parentOrgId;
    this.type = type;
    this.name = name;
    this.description = description;
  }

}
