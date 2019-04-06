package com.abaya.picacho.common.model;

import com.abaya.picacho.common.util.AuthorizeUtils;
import com.abaya.picacho.common.validation.Auth;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 当后端请求需要增加安全性校验时，必须传入token
 */
@Data
public class AuthorizedRequest extends ExtensibleEntity {
    @Auth
    private String token;

    @JsonIgnore
    public String getOperator() {
        return AuthorizeUtils.getUsernameByToken(token);
    }
}
