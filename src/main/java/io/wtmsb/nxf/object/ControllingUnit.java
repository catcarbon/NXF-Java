package io.wtmsb.nxf.object;

import io.wtmsb.nxf.message.radar.NxfRadar;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode
public final class ControllingUnit {
	@NonNull
	String facility = "NoFacility";

	@NonNull
	String sector = "";

	public ControllingUnit(NxfRadar.Track.ControllingUnit controllingUnitMessage) {
		this.setFacility(controllingUnitMessage.getFacility());
		this.setSector(controllingUnitMessage.getSector());
	}

	public boolean isDefault() {
		return facility.equals("NoFacility");
	}
}
