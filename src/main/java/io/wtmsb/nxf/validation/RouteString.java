package io.wtmsb.nxf.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Constraint(validatedBy = RouteStringValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER})
public @interface RouteString {
	String message() default "{io.wtmsb.nxf.validation.RouteString.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	int maxLength() default Integer.MAX_VALUE;
}
