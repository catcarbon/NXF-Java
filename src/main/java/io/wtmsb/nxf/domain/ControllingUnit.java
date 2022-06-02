package io.wtmsb.nxf.domain;

import io.wtmsb.nxf.manager.ControllerManager;
import io.wtmsb.nxf.message.radar.NxfRadar;
import io.wtmsb.nxf.validation.AlphanumericString;
import lombok.*;

/**
 * Immutable, shared object managed by {@link ControllerManager}
 */
@Getter @AllArgsConstructor @EqualsAndHashCode
public final class ControllingUnit {
	@NonNull @AlphanumericString(maxLength = 3)
	private final String facility;

	@NonNull @AlphanumericString(maxLength = 3)
	private final String sector;

	public ControllingUnit(NxfRadar.ControllingUnit cuMessage) {
		facility = cuMessage.getFacility();
		sector = cuMessage.getSector();
	}
}
