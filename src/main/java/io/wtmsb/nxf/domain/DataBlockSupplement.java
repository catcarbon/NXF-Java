package io.wtmsb.nxf.domain;

import io.wtmsb.nxf.message.radar.NxfRadar;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

import static io.wtmsb.nxf.domain.IRadarComponent.LeaderLineDirection;
import static io.wtmsb.nxf.domain.IRadarComponent.getLeaderLineDirectionOrDefault;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
public final class DataBlockSupplement {
	private static final DataBlockSupplement DEFAULT = new DataBlockSupplement();
	@NonNull
	private LeaderLineDirection leaderLineDirection = LeaderLineDirection.DEFAULT;

	@NonNull @Max(60000)
	private Integer assignedTemporaryAltitude = 0;

	@NonNull @Size(max = 5)
	private String pad1 = "";

	@NonNull @Size(max = 5)
	private String pad2 = "";

	@NonNull @Size(max = 3)
	private String runway = "";

	@NonNull @Size(max = 5)
	private String exitFix = "";

	public DataBlockSupplement(NxfRadar.Track.DataBlockSupplement dsbMsg) {
		leaderLineDirection = getLeaderLineDirectionOrDefault(dsbMsg.getLeaderLineDirectionValue());
		assignedTemporaryAltitude = dsbMsg.getAssignedTemporaryAltitude();
		pad1 = dsbMsg.getPad1();
		pad2 = dsbMsg.getPad2();
		runway = dsbMsg.getRunway();
		exitFix = dsbMsg.getExitFix();
	}

	public boolean isDefault() {
		return this == DEFAULT;
	}

	public static DataBlockSupplement getDefault() {
		return DEFAULT;
	}
}
