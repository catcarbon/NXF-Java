package io.wtmsb.nxf.object;

import com.google.protobuf.ByteString;
import io.wtmsb.nxf.message.radar.NxfRadar;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FlightStrip implements IRadarComponent {
	@NonNull
	private Integer id = Integer.MIN_VALUE;

	@NonNull
	private String aircraftType = "";

	@NonNull @EqualsAndHashCode.Include
	private String aircraftCallsign = "";

	@NonNull @EqualsAndHashCode.Include
	private ByteString aircraftAddress = DEFAULT_AIRCRAFT_ADDRESS;

	@NonNull
	private WakeCategory wakeCategory = WakeCategory.NO_WEIGHT;

	@NonNull
	private FlightRule flightRule = FlightRule.INSTRUMENT;

	@NonNull
	private String destination = "";

	@NonNull
	private Integer requestedAltitude = 0;

	@NonNull
	private ByteString assignedBeaconCode = DEFAULT_BEACON_CODE;

	@NonNull
	private String routeString = "";

	public FlightStrip(NxfRadar.FlightStrip fsMsg) {
		id = fsMsg.getId();
		aircraftType = fsMsg.getAircraftType();
		aircraftCallsign = fsMsg.getAircraftCallsign();
		aircraftAddress = IRadarComponent.getAircraftAddressOrDefault(fsMsg.getAircraftAddress());
		wakeCategory = IRadarComponent.getWakeCategoryOrDefault(fsMsg.getWakeCategoryValue());
		flightRule = IRadarComponent.getFlightRuleOrDefault(fsMsg.getFlightRuleValue());
		destination = fsMsg.getDestination();
		requestedAltitude = fsMsg.getRequestedAltitude();
		assignedBeaconCode = IRadarComponent.getBeaconCodeOrDefault(fsMsg.getAssignedBeaconCode());
		routeString = fsMsg.getRouteString();
	}

	public boolean isDefault() {
		return id == Integer.MIN_VALUE;
	}
}
