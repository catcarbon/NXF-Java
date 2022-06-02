package io.wtmsb.nxf.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Constraint(validatedBy = AlphanumericStringValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER})
public @interface AlphanumericString {
	String message() default "{io.wtmsb.nxf.validation.AlphanumericString.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	int minLength() default 0;

	int maxLength() default Integer.MAX_VALUE;
}
