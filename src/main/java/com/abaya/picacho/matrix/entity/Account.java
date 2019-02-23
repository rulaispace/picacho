package com.abaya.picacho.matrix.entity;

import com.abaya.picacho.matrix.util.StringUUIDUtils;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Account extends AccountBase {
  private String name;

  private String rule;

  private String token = StringUUIDUtils.uuid();

  public Account() {}

  public Account(String username, String password, String name, String rule) {
    this.username = username;
    this.password = password;
    this.name = name;
    this.rule = rule;
  }
}
