package com.abaya.picacho.matrix.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Inheritance(
  strategy = InheritanceType.JOINED
)
public class AccountBase {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long key;

  protected String username;
  protected String password;
}
