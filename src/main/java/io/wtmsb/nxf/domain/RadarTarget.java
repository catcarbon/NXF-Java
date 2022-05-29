package io.wtmsb.nxf.domain;

import com.google.protobuf.ByteString;
import io.wtmsb.nxf.message.radar.NxfRadar;
import io.wtmsb.nxf.validator.ByteStringSize;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.Instant;

@Getter @Setter
@EqualsAndHashCode @ToString
public final class RadarTarget implements IRadarComponent {
	@NonNull @Min(-90) @Max(90)
	private Double lat;
	@NonNull @Min(-180) @Max(180)
	private Double lon;

	@NonNull
	private TransponderMode transponderMode = TransponderMode.NO_MODE;

	@NonNull @ByteStringSize(BEACON_CODE_SIZE)
	private ByteString beaconCode = DEFAULT_BEACON_CODE;

	@NonNull
	private Instant returnTime = Instant.EPOCH;

	@NonNull @Min(0) @Max(100000)
	private Integer reportedAltitude;

	@NonNull
	private ByteString modeSAddress = DEFAULT_AIRCRAFT_ADDRESS;

	public RadarTarget(long customReturnTime) {
		returnTime = Instant.ofEpochSecond(customReturnTime);
	}

	public RadarTarget(NxfRadar.Track.RadarTarget rtMsg) throws RuntimeException {
		lat = Double.parseDouble(rtMsg.getLat());
		lon = Double.parseDouble(rtMsg.getLon());
		transponderMode = IRadarComponent.getTransponderModeOrDefault(rtMsg.getTransponderModeValue());
		beaconCode = IRadarComponent.getBeaconCodeOrDefault(rtMsg.getBeaconCode());
		returnTime = Instant.ofEpochSecond(rtMsg.getReturnTime());
		reportedAltitude = rtMsg.getReportedAltitude();
		modeSAddress = rtMsg.getModeSAddress();
	}

	public boolean isDefault() {
		return returnTime == Instant.EPOCH;
	}
}
