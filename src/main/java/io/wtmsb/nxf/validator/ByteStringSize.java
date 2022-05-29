package io.wtmsb.nxf.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ByteStringSizeValidator.class)
public @interface ByteStringSize {
	String message() default "{io.wtmsb.nxf.constraint.ByteStringSize.message}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	int value();
}
