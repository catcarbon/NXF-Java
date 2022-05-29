package io.wtmsb.nxf.validator;

import com.google.protobuf.ByteString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ByteStringSizeValidator implements ConstraintValidator<ByteStringSize, ByteString> {
	private int mandatorySize;

	public void initialize(ByteStringSize byteStringSize) {
		mandatorySize = byteStringSize.value();
	}

	@Override
	public boolean isValid(ByteString bs, ConstraintValidatorContext constraintValidatorContext) {
		return bs.size() == mandatorySize;
	}
}
