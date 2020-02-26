/**
 * 
 */
package com.omantel.restapi.annotation;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Profile;

/**
 * @author Dhiraj Gour
 * @date 13 Feb 2020
 *
 */
@Retention(RUNTIME)
@Target({ TYPE, FIELD, METHOD, CONSTRUCTOR })
@Profile("openshift")
public @interface OpenShiftProfile {

}
