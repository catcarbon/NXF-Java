package io.wtmsb.nxf.validation;

import com.google.protobuf.ByteString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IcaoAddressValidator implements ConstraintValidator<IcaoAddress, ByteString> {

	public void initialize(IcaoAddress icaoAddress) {}

	@Override
	public boolean isValid(ByteString bs, ConstraintValidatorContext constraintValidatorContext) {
		int ICAO_ADDRESS_SIZE = 3;
		return bs != null && bs.size() == ICAO_ADDRESS_SIZE;
	}
}
