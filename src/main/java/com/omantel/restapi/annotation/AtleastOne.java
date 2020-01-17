/**
 * 
 */
package com.omantel.restapi.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.omantel.restapi.validator.AtleastOneValidator;

/**
 * @author Dhiraj Gour
 * @date 20 August 2019
 *
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Constraint(validatedBy = AtleastOneValidator.class)
public @interface AtleastOne {

	String[] value();
    String message() default "Atleast one input mandatory";
    
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
