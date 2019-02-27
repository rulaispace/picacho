package com.abaya.picacho.user.entity;

import com.abaya.picacho.common.entity.EntityBase;
import com.abaya.picacho.user.model.AccountState;
import com.abaya.picacho.common.util.StringUUIDUtils;
import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public class AccountBase extends EntityBase {
  @Column(unique=true)
  protected String username;
  protected String password;

  private String token = StringUUIDUtils.uuid();

  @Enumerated(EnumType.STRING)
  protected AccountState state = AccountState.valid;
}
