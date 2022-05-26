package io.wtmsb.nxf.object;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.nio.ByteBuffer;

@Getter
@Setter
public final class RadarTarget {
	@NonNull String lat;
	@NonNull String lon;

	enum TransponderMode {
		NO_MODE, MODE_A, MODE_C, MODE_S
	}

	@NonNull TransponderMode transponderMode;
	@NonNull ByteBuffer beaconCode;
	@NonNull Long returnTime;
	@NonNull Integer reportedAltitude;
	@NonNull ByteBuffer modeSAddress;

	public RadarTarget() {
		lat = "";
		lon = "";
		transponderMode = TransponderMode.NO_MODE;
		beaconCode = ByteBuffer.allocate(2);
		returnTime = 0L;
		reportedAltitude = 0;
		modeSAddress = ByteBuffer.allocate(3);
	}
}
