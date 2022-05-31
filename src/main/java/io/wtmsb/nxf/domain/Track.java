package io.wtmsb.nxf.domain;

import com.google.protobuf.ByteString;
import io.wtmsb.nxf.validation.BeaconCode;
import io.wtmsb.nxf.validation.IcaoAddress;
import lombok.*;

@Getter @Setter
public class Track {
	@NonNull
	private FlightData flightData;

	@Getter(value = AccessLevel.NONE) @Setter(value = AccessLevel.NONE)
	private boolean isPrimary = true;

	@Getter(value = AccessLevel.NONE) @Setter(value = AccessLevel.NONE)
	private boolean isCorrelated = false;

	/**
	 * Constructor for creating a new primary track
	 *
	 * @param callsign callsign for this track
	 */
	public Track(String callsign) {
		flightData = new FlightData(callsign);
	}

	/**
	 * For creating a new mode A or C track
	 *
	 * @param callsign callsign for this track
	 * @param beaconCode 12-bit octal beacon code enclosed in 2 byte
	 */
	public Track(String callsign, @BeaconCode ByteString beaconCode) {
		flightData = new FlightData(callsign);
		flightData.setHasAssignedBeaconCode(true);
		flightData.setAssignedBeaconCode(beaconCode);
	}

	/**
	 * For creating a new mode S track
	 *
	 * @param callsign callsign for this track
	 * @param icaoAddress 3 byte ICAO address associated with this track
	 */
	public Track(String callsign, @BeaconCode ByteString beaconCode, @IcaoAddress ByteString icaoAddress) {
		this(callsign, beaconCode);
		flightData.setHasIcaoAddress(true);
		flightData.setIcaoAddress(icaoAddress);
	}
}
