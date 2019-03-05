package com.abaya.picacho.user.entity;

import com.abaya.picacho.common.entity.EntityBase;
import com.abaya.picacho.common.model.CommonState;
import com.abaya.picacho.common.util.RandomUtils;
import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public class AccountBase extends EntityBase {
  @Column(unique=true)
  protected String username;
  protected String password;

  private String token = RandomUtils.uuid();

  @Enumerated(EnumType.STRING)
  protected CommonState state = CommonState.valid;

  // 是否需要修改密码
  private boolean shouldChangePassword = true;
}
