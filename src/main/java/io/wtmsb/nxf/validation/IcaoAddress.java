package io.wtmsb.nxf.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IcaoAddressValidator.class)
public @interface IcaoAddress {
	String message() default "{io.wtmsb.nxf.validation.IcaoAddress.message}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
