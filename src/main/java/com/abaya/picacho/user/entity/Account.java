package com.abaya.picacho.user.entity;

import com.abaya.picacho.user.model.RuleType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Account extends AccountBase {
  private static final String ROOT_DEPARTMENT = "总公司";

  private String name;

  @Enumerated(EnumType.STRING)
  private RuleType rule = RuleType.employee;

  private String department = ROOT_DEPARTMENT;

  public Account() {}

  public Account(String username, String password, String name, RuleType rule) {
    this.username = username;
    this.password = password;
    this.name = name;
    this.rule = rule;
  }
}
