package com.omantel.restapi.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.omantel.restapi.validator.AllOrNoneValidator;

/**
 * @author Dhiraj Gour
 * @date 20 August 2019
 *
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AllOrNoneValidator.class)
public @interface AllOrNone {
    
	String[] value();
    String message() default "Missing mandatory inputs";
    
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}