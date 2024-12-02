package com.srinageswari.rezeptmonolith.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import com.srinageswari.rezeptmonolith.common.Constants;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author smanickavasagam
 *     <p>Custom constraint annotation for ItemValidator
 */
@Documented
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {ItemBusinessValidator.class})
public @interface ValidItem {

  String message() default Constants.NOT_VALIDATED_ELEMENT;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
