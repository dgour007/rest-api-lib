/**
 * 
 */
package com.omantel.restapi.validator;

import java.util.Objects;
import java.util.stream.Stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.expression.spel.standard.SpelExpressionParser;

import com.omantel.restapi.annotation.AtleastOne;

/**
 * @author Dhriaj Gour
 * @date 20 August 2019
 *
 */
public class AtleastOneValidator implements ConstraintValidator<AtleastOne, Object> {
	
    private static final SpelExpressionParser PARSER = new SpelExpressionParser();
    private String[] fields;

    @Override
    public void initialize(AtleastOne constraintAnnotation) {
        fields = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        long notNull = Stream.of(fields)
                .map(field -> PARSER.parseExpression(field).getValue(value))
                .filter(Objects::nonNull)
                .count();
        return notNull > 0;
    }
}