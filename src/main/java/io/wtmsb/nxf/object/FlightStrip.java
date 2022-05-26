package io.wtmsb.nxf.object;

import lombok.*;

import java.nio.ByteBuffer;

@Getter
@Setter
@Builder(toBuilder = true)
public class FlightStrip {
	@NonNull
	private Integer id;

	@NonNull @Builder.Default
	private String aircraftType = "";

	@NonNull @Builder.Default
	private String aircraftCallsign = "";

	@NonNull @Builder.Default
	private ByteBuffer aircraftAddress = ByteBuffer.allocate(3);

	public enum WakeCategory {
		NO_WEIGHT, CAT_A, CAT_B, CAT_C, CAT_D, CAT_E, CAT_F
	}

	@NonNull @Builder.Default
	private WakeCategory wakeCategory = WakeCategory.NO_WEIGHT;

	public enum FlightRule {
		INSTRUMENT, VISUAL, SPECIAL_VISUAL, DEFENSE_VISUAL
	}

	@NonNull @Builder.Default
	private FlightRule flightRule = FlightRule.INSTRUMENT;

	@NonNull @Builder.Default
	private String destination = "";

	@NonNull
	private Integer requestedAltitude;

	@NonNull @Builder.Default
	private ByteBuffer assignedBeaconCode = ByteBuffer.allocate(2);

	@NonNull @Builder.Default
	private String routeString = "";

	public static FlightStrip getDefault() {
		return FlightStrip.builder().build();
	}
}
