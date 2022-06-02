package io.wtmsb.nxf.validation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class AlphanumericStringValidator implements ConstraintValidator<AlphanumericString, String> {
	private static final Pattern illegalCharacterPattern = Pattern.compile("[^A-Za-z]");

	private int minLength, maxLength;

	public void initialize(AlphanumericString constraint) {
		minLength = constraint.minLength();
		maxLength = constraint.maxLength();
	}

	@Override
	public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
		if (StringUtils.hasText(s))
			return minLength <= s.length() && s.length() <= maxLength &&
					!illegalCharacterPattern.asPredicate().test(s);

		// otherwise minLength must be 0 and s must be empty
		return minLength == 0 && s != null && s.length() == 0;
	}
}
