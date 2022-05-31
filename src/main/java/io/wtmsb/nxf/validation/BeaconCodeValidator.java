package io.wtmsb.nxf.validation;

import com.google.protobuf.ByteString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BeaconCodeValidator implements ConstraintValidator<BeaconCode, ByteString> {
	private static final int BEACON_CODE_SIZE = 2;

	private static boolean checkBeaconCodeValue(ByteString octal) {
		int value = fromBeaconCodeArray(octal.toByteArray());
		return 0 <= value && value <= 0xFFF;
	}

	private static int fromBeaconCodeArray(byte[] bytes) {
		return ((bytes[0] & 0xFF) << 8) | (bytes[1] & 0xFF);
	}

	public void initialize(BeaconCode beaconCode) {}

	@Override
	public boolean isValid(ByteString bs, ConstraintValidatorContext constraintValidatorContext) {
		if (bs == null || bs.size() != BEACON_CODE_SIZE)
			return false;

		return checkBeaconCodeValue(bs);
	}
}
