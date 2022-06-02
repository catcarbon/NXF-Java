package io.wtmsb.nxf.validation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class CallsignValidator implements ConstraintValidator<Callsign, String> {

	public static final Pattern illegalCharacterPattern = Pattern.compile("[^a-zA-Z\\d]");

	private int minLength, maxLength;

	public void initialize(Callsign constraint) {
		minLength = constraint.minLength();
		maxLength = constraint.maxLength();
	}

	@Override
	public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
		return StringUtils.hasText(s) &&
				minLength <= s.length() && s.length() <= maxLength &&
				!illegalCharacterPattern.asPredicate().test(s);
	}
}
