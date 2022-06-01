package io.wtmsb.nxf.domain;

import com.google.protobuf.ByteString;
import io.wtmsb.nxf.message.radar.NxfRadar;
import io.wtmsb.nxf.validation.BeaconCode;
import io.wtmsb.nxf.validation.IcaoAddress;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.Instant;

@Getter
@EqualsAndHashCode @ToString
public final class RadarTarget implements IRadarComponent {
	@Min(-90) @Max(90)
	private final double lat;
	@Min(-180) @Max(180)
	private final double lon;

	@NonNull @Min(0)
	private final Instant returnTime;

	@BeaconCode
	private final ByteString beaconCode;

	@Max(MAX_ALTITUDE)
	private final int reportedAltitude;

	@IcaoAddress
	private final ByteString modeSAddress;

	private final IRadarComponent.TransponderMode transponderMode;

	private final int groundSpeed;

	@Max(359)
	private final int groundTrack;


	public RadarTarget(long customReturnTime) {
		// TODO: for testing only, remove when possible
		lat = lon = 0.0;
		beaconCode = DEFAULT_BEACON_CODE;
		reportedAltitude = 0;
		modeSAddress = DEFAULT_ICAO_ADDRESS;
		transponderMode = TransponderMode.PRIMARY;
		groundSpeed = 0;
		groundTrack = 0;
		returnTime = Instant.ofEpochSecond(customReturnTime);
	}

	public RadarTarget(NxfRadar.RadarTarget rtMsg) {
		lat = rtMsg.getLat();
		lon = rtMsg.getLon();
		returnTime = Instant.ofEpochSecond(rtMsg.getReturnTime());

		if (rtMsg.hasModeSAddress() && rtMsg.hasReportedAltitude() && rtMsg.hasBeaconCode()) {
			transponderMode = TransponderMode.MODE_S;
			beaconCode = rtMsg.getBeaconCode();
			reportedAltitude = rtMsg.getReportedAltitude();
			modeSAddress = rtMsg.getModeSAddress();
		} else if (rtMsg.hasReportedAltitude() && rtMsg.hasBeaconCode()) {
			transponderMode = TransponderMode.MODE_C;
			beaconCode = rtMsg.getBeaconCode();
			reportedAltitude = rtMsg.getReportedAltitude();
			modeSAddress = DEFAULT_ICAO_ADDRESS;
		} else if (rtMsg.hasBeaconCode()) {
			transponderMode = TransponderMode.MODE_A;
			beaconCode = rtMsg.getBeaconCode();
			reportedAltitude = 0;
			modeSAddress = DEFAULT_ICAO_ADDRESS;
		} else {
			transponderMode = TransponderMode.PRIMARY;
			beaconCode = DEFAULT_BEACON_CODE;
			reportedAltitude = 0;
			modeSAddress = DEFAULT_ICAO_ADDRESS;
		}

		groundSpeed = rtMsg.getGroundSpeed();
		groundTrack = rtMsg.getGroundTrack();
	}
}
