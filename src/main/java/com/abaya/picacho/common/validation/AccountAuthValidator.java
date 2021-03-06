package com.abaya.picacho.common.validation;

import com.abaya.picacho.common.util.AuthorizeUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AccountAuthValidator implements ConstraintValidator<Auth, String> {
    @Override
    public boolean isValid(String constraintField, ConstraintValidatorContext context) {
        String username = AuthorizeUtils.getUsernameByToken(constraintField);
        return username != null && username.trim().length() != 0;
    }
}
