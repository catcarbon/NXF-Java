package io.wtmsb.nxf.object;

import com.google.protobuf.ByteString;
import io.wtmsb.nxf.message.radar.NxfRadar;
import lombok.*;

@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public final class RadarTarget implements IRadarComponent {
	@NonNull
	String lat;
	@NonNull
	String lon;

	@NonNull @EqualsAndHashCode.Include
	TransponderMode transponderMode = TransponderMode.NO_MODE;

	@NonNull @EqualsAndHashCode.Include
	ByteString beaconCode = DEFAULT_BEACON_CODE;

	@NonNull @EqualsAndHashCode.Include @ToString.Include
	Long returnTime;

	@NonNull
	Integer reportedAltitude;

	@NonNull @EqualsAndHashCode.Include
	ByteString modeSAddress = DEFAULT_AIRCRAFT_ADDRESS;

	public RadarTarget(long returnTime) {
		this.returnTime = returnTime;
	}

	public RadarTarget(NxfRadar.Track.RadarTarget rtMsg) {
		lat = rtMsg.getLat();
		lon = rtMsg.getLon();
		transponderMode = IRadarComponent.getTransponderModeOrDefault(rtMsg.getTransponderModeValue());
		beaconCode = IRadarComponent.getBeaconCodeOrDefault(rtMsg.getBeaconCode());
		returnTime = rtMsg.getReturnTime();
		reportedAltitude = rtMsg.getReportedAltitude();
		modeSAddress = IRadarComponent.getAircraftAddressOrDefault(rtMsg.getModeSAddress());
	}
}
