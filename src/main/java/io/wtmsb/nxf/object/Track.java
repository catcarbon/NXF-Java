package io.wtmsb.nxf.object;

import lombok.*;

import java.util.List;
import java.util.Vector;

@Getter
@Setter
@Builder(toBuilder = true)
public class Track {
	@NonNull
	private Integer id;

	@NonNull @Builder.Default
	private List<RadarTarget> radarTargets = new Vector<>();

	@NonNull @Builder.Default
	private FlightStrip flightStrip = FlightStrip.getDefault();

	@NonNull @Builder.Default
	private ControllingUnit currentController = ControllingUnit.getDefault();

	@NonNull @Builder.Default
	private ControllingUnit nextController = ControllingUnit.getDefault();

	// reserved 6 to 10
	@NonNull @Builder.Default
	private DataBlockSupplement dataBlockSupplement = DataBlockSupplement.getDefault();
}
