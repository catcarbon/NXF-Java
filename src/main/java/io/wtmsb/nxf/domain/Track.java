package io.wtmsb.nxf.domain;

import io.wtmsb.nxf.manager.ControllerManager;
import io.wtmsb.nxf.message.radar.NxfRadar;
import lombok.*;

@Getter @Setter
public class Track {
	@NonNull
	private FlightStrip flightStrip;

	@NonNull
	private ControllingUnit currentController;

	@NonNull
	private ControllingUnit nextController;

	@NonNull
	private DataBlockSupplement dataBlockSupplement;

	@Getter(value = AccessLevel.NONE) @Setter(value = AccessLevel.NONE)
	private boolean isPrimary = true;

	@Getter(value = AccessLevel.NONE) @Setter(value = AccessLevel.NONE)
	private boolean isCorrelated = false;

	public Track(String aircraftCallsign) {
		flightStrip = new FlightStrip(aircraftCallsign);
		dataBlockSupplement = new DataBlockSupplement();
		currentController = nextController = ControllerManager.getDefaultControllingUnit();
	}

	public Track(NxfRadar.Track tMsg) {
		flightStrip = new FlightStrip(tMsg.getFlightStrip());
		dataBlockSupplement = new DataBlockSupplement(tMsg.getDataBlockSupplement());
		currentController = ControllerManager.getControllingUnit(tMsg.getCurrentController());
		nextController = ControllerManager.getControllingUnit(tMsg.getNextController());
	}

	public boolean isPrimaryTrack() {
		return isPrimary;
	}

	public boolean isUncorrelatedTrack() {
		return isCorrelated;
	}

	public void correlateFlightStrip(FlightStrip fs) {
		flightStrip = fs;
		isCorrelated = true;
		isPrimary = false;
	}

	public void detachFlightStrip() {
		flightStrip.setAircraftCallsign("WHO");
		isCorrelated = false;
	}

	public void setPrimary() {
		isCorrelated = false;
		isPrimary = true;
	}

	public boolean isUncontrolledTrack() {
		return currentController == ControllerManager.getDefaultControllingUnit();
	}
}
