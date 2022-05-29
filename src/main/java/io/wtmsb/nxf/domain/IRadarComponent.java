package io.wtmsb.nxf.domain;

import com.google.protobuf.ByteString;
import io.wtmsb.nxf.validator.ByteStringSize;

public interface IRadarComponent {
	int BEACON_CODE_SIZE = 2;
	ByteString DEFAULT_BEACON_CODE = ByteString.fromHex("0000");
	int AIRCRAFT_ADDRESS_SIZE = 3;
	ByteString DEFAULT_AIRCRAFT_ADDRESS = ByteString.fromHex("000000");

	enum TransponderMode {
		NO_MODE, MODE_A, MODE_C, MODE_S
	}

	enum FlightRule {
		INSTRUMENT, VISUAL, SPECIAL_VISUAL, DEFENSE_VISUAL
	}

	 enum WakeCategory {
		NO_WEIGHT, CAT_A, CAT_B, CAT_C, CAT_D, CAT_E, CAT_F
	}

	enum LeaderLineDirection {
		DEFAULT,
		NW, N, NE, W,
		HIDE,
		E, SW, S, SE
	}

	static ByteString getBeaconCodeOrDefault(ByteString src) {
		return checkBeaconCodeValue(src) ? src : DEFAULT_BEACON_CODE;
	}

	static TransponderMode getTransponderModeOrDefault(int ordinal) {
		return (0 < ordinal && ordinal < TransponderMode.values().length) ?
				TransponderMode.values()[ordinal] : TransponderMode.NO_MODE;
	}

	static FlightRule getFlightRuleOrDefault(int ordinal) {
		return (0 < ordinal && ordinal < FlightRule.values().length) ?
				FlightRule.values()[ordinal] : FlightRule.INSTRUMENT;
	}

	static WakeCategory getWakeCategoryOrDefault(int ordinal) {
		return (0 < ordinal && ordinal < WakeCategory.values().length) ?
				WakeCategory.values()[ordinal] : WakeCategory.NO_WEIGHT;
	}

	static LeaderLineDirection getLeaderLineDirectionOrDefault(int ordinal) {
		return (0 < ordinal && ordinal < LeaderLineDirection.values().length) ?
				LeaderLineDirection.values()[ordinal] : LeaderLineDirection.DEFAULT;
	}

	private static boolean checkBeaconCodeValue(ByteString octal) {
		int value = fromBeaconCodeArray(octal.toByteArray());
		return 0 <= value && value <= 0xFFF;
	}

	private static int fromBeaconCodeArray(byte[] bytes) {
		return ((bytes[0] & 0xFF) << 8) | (bytes[1] & 0xFF);
	}

	private static int fromAircraftAddressArray(byte[] bytes) {
		return ((bytes[0] & 0xFF) << 16) | ((bytes[1] & 0xFF) << 8) | (bytes[2] & 0xFF);
	}
}
