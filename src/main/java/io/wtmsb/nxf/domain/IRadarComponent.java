package io.wtmsb.nxf.domain;

import com.google.protobuf.ByteString;
import io.wtmsb.nxf.validation.IcaoAddress;

public interface IRadarComponent {
	ByteString DEFAULT_BEACON_CODE = ByteString.fromHex("0000");
	ByteString DEFAULT_ICAO_ADDRESS = ByteString.fromHex("000000");
	int MAX_ALTITUDE = 100000;

	enum TransponderMode {
		PRIMARY, MODE_A, MODE_C, MODE_S
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
}
