package io.wtmsb.nxf.object;

import io.wtmsb.nxf.message.radar.NxfRadar;
import lombok.*;

@Getter @Setter @NoArgsConstructor
public class Track {
	@NonNull
	private Integer id = Integer.MIN_VALUE;

	@NonNull
	private FlightStrip flightStrip = new FlightStrip();

	@NonNull
	private ControllingUnit currentController = new ControllingUnit();

	@NonNull
	private ControllingUnit nextController = new ControllingUnit();

	@NonNull
	private DataBlockSupplement dataBlockSupplement = new DataBlockSupplement();

	private boolean isPrimary = true;

	public Track(int id) {
		this.id = id;
	}

	public Track(NxfRadar.Track tMsg) {
			id = tMsg.getId();
			//radarTargets = new Vector<>();
			flightStrip = new FlightStrip(tMsg.getFlightStrip());
			currentController = new ControllingUnit(tMsg.getCurrentController());
			nextController = new ControllingUnit(tMsg.getNextController());
			dataBlockSupplement = new DataBlockSupplement(tMsg.getDataBlockSupplement());
	}

	public boolean isPrimaryTrack() {
		return isPrimary;
	}

	public boolean isUncorrelatedTrack() {
		return flightStrip.isDefault();
	}
}
