package io.wtmsb.nxf.domain;

import com.google.protobuf.ByteString;
import io.wtmsb.nxf.message.radar.NxfRadar;
import io.wtmsb.nxf.validator.ByteStringSize;
import lombok.*;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.time.Instant;

/**
 * Mutable object managed by {@link io.wtmsb.nxf.manager.TrackManager}
 */
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FlightStrip implements IRadarComponent {
	@NonNull @Size(min = 2, max = 10)
	@EqualsAndHashCode.Include
	private String aircraftCallsign;

	@NonNull @ByteStringSize(AIRCRAFT_ADDRESS_SIZE)
	@EqualsAndHashCode.Include
	private ByteString aircraftAddress = DEFAULT_AIRCRAFT_ADDRESS;

	@NonNull @Size(max = 10)
	private String aircraftType = "";

	@NonNull
	private WakeCategory wakeCategory = WakeCategory.NO_WEIGHT;

	@NonNull
	private FlightRule flightRule = FlightRule.INSTRUMENT;

	@NonNull @Size(max = 4)
	private String destination = "";

	@NonNull @Max(100000)
	private Integer requestedAltitude = 0;

	@NonNull @ByteStringSize(BEACON_CODE_SIZE)
	private ByteString assignedBeaconCode = DEFAULT_BEACON_CODE;

	@NonNull @Size(max = 2000)
	private String routeString = "";

	@NonNull
	private Instant lastUpdated = Instant.EPOCH;

	public FlightStrip(@NonNull String _aircraftCallsign) {
		aircraftCallsign = _aircraftCallsign;
	}

	public FlightStrip(NxfRadar.FlightStrip fsMsg) {
		if (!StringUtils.hasText(fsMsg.getAircraftCallsign())) {
			throw new IllegalArgumentException("Malformed aircraftCallsign in FlightStrip message");
		} else {
			aircraftCallsign = fsMsg.getAircraftCallsign();
		}

		aircraftAddress = fsMsg.getAircraftAddress();
		wakeCategory = IRadarComponent.getWakeCategoryOrDefault(fsMsg.getWakeCategoryValue());
		flightRule = IRadarComponent.getFlightRuleOrDefault(fsMsg.getFlightRuleValue());
		assignedBeaconCode = IRadarComponent.getBeaconCodeOrDefault(fsMsg.getAssignedBeaconCode());
		aircraftType = fsMsg.getAircraftType();
		destination = fsMsg.getDestination();
		requestedAltitude = fsMsg.getRequestedAltitude();
		routeString = StringUtils.trimWhitespace(fsMsg.getRouteString());
	}
}
