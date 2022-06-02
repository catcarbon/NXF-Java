package io.wtmsb.nxf.domain;

import com.google.protobuf.ByteString;
import io.wtmsb.nxf.manager.ControllerManager;
import io.wtmsb.nxf.message.radar.NxfRadar;
import io.wtmsb.nxf.validation.*;
import lombok.*;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.PastOrPresent;
import java.time.Instant;

/**
 * Mutable object managed by {@link io.wtmsb.nxf.manager.TrackManager}
 */
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FlightData implements IRadarComponent {
	@Callsign
	@EqualsAndHashCode.Include
	private String callsign;

	@EqualsAndHashCode.Include
	private boolean hasIcaoAddress = false;

	@IcaoAddress
	@EqualsAndHashCode.Include
	private ByteString icaoAddress = DEFAULT_ICAO_ADDRESS;

	@EqualsAndHashCode.Include
	private boolean hasAssignedBeaconCode = false;

	@BeaconCode
	@EqualsAndHashCode.Include
	private ByteString assignedBeaconCode = DEFAULT_BEACON_CODE;

	@NonNull @PastOrPresent
	@EqualsAndHashCode.Include
	private Instant lastUpdated = Instant.now();

	@AlphanumericString(maxLength = 4)
	private String aircraftType = "";

	@AlphanumericString(maxLength = 1)
	private String equipmentSuffix = "";

	@NonNull
	private FlightRule flightRule = FlightRule.INSTRUMENT;

	@AlphanumericString(maxLength = 4)
	private String departurePoint = "";

	@AlphanumericString(maxLength = 4)
	private String destination = "";

	@NonNull @Max(MAX_ALTITUDE)
	private Integer requestedAltitude = 0;

	@RouteString(maxLength = 2000)
	private String routeString = "";

	@NonNull
	private ControllingUnit currentController = ControllerManager.getUncontrolledUnit();

	@NonNull
	private ControllingUnit nextController = ControllerManager.getUncontrolledUnit();

	@NonNull @Max(MAX_ALTITUDE)
	private Integer assignedTemporaryAltitude = 0;

	@NonNull @Max(MAX_ALTITUDE)
	private Integer assignedFinalAltitude = 0;

	@NonNull
	private FlightDataSupplement supplement = FlightDataSupplement.getDefault();

	public FlightData(@Callsign String _callsign) {
		callsign = _callsign;
	}

	public FlightData(NxfRadar.FlightData fDataMessage) {
		this(fDataMessage.getIdentification().getCallsign());
		if (fDataMessage.getIdentification().hasIcaoAddress()) {
			icaoAddress = fDataMessage.getIdentification().getIcaoAddress();
		}
		assignedBeaconCode = fDataMessage.getAssignedBeaconCode();
		flightRule = IRadarComponent.getFlightRuleOrDefault(fDataMessage.getFlightRuleValue());

		aircraftType = fDataMessage.getAircraftType();
		departurePoint = fDataMessage.getDestination();
		requestedAltitude = fDataMessage.getRequestedAltitude();
		routeString = StringUtils.trimWhitespace(fDataMessage.getRouteString());
	}
}
