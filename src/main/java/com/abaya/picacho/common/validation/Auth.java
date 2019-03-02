package com.abaya.picacho.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AccountAuthValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
    String message() default "无效的TOKEN，操作无法执行";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
