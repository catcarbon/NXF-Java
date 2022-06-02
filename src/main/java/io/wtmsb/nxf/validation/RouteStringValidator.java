package io.wtmsb.nxf.validation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class RouteStringValidator implements ConstraintValidator<RouteString, String> {
	private static final Pattern illegalCharacterPattern = Pattern.compile("/[^A-Za-z\\d. +-]/");

	private int maxLength;

	public void initialize(RouteString constraint) {
		maxLength = constraint.maxLength();
	}

	@Override
	public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
		if (StringUtils.hasText(s))
			return s.length() < maxLength && !illegalCharacterPattern.asPredicate().test(s);

		// otherwise s must be empty
		return s != null && s.length() == 0;
	}
}
