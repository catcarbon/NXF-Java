package io.wtmsb.nxf.domain;

import io.wtmsb.nxf.manager.ControllerManager;
import io.wtmsb.nxf.message.radar.NxfRadar;
import lombok.*;

import javax.validation.constraints.Size;

/**
 * Immutable, shared object managed by {@link ControllerManager}
 */
@Getter @AllArgsConstructor @EqualsAndHashCode
public final class ControllingUnit {
	@NonNull @Size(max = 3)
	private final String facility;

	@NonNull @Size(max = 5)
	private final String sector;

	public ControllingUnit(NxfRadar.Track.ControllingUnit cuMessage) {
		facility = cuMessage.getFacility();
		sector = cuMessage.getSector();
	}
}
