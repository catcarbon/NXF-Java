package io.wtmsb.nxf.object;

import io.wtmsb.nxf.message.radar.NxfRadar;
import lombok.*;

import static io.wtmsb.nxf.object.IRadarComponent.LeaderLineDirection;
import static io.wtmsb.nxf.object.IRadarComponent.getLeaderLineDirectionOrDefault;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
public final class DataBlockSupplement {
	@NonNull
	LeaderLineDirection leaderLineDirection = LeaderLineDirection.DEFAULT;

	@NonNull
	Integer assignedTemporaryAltitude = 0;

	@NonNull
	String pad1 = "";

	@NonNull
	String pad2 = "";

	@NonNull
	String runway = "";

	@NonNull
	String exitFix = "";

	public DataBlockSupplement(NxfRadar.Track.DataBlockSupplement dsbMsg) {
		leaderLineDirection = getLeaderLineDirectionOrDefault(dsbMsg.getLeaderLineDirectionValue());
		assignedTemporaryAltitude = dsbMsg.getAssignedTemporaryAltitude();
		pad1 = dsbMsg.getPad1();
		pad2 = dsbMsg.getPad2();
		runway = dsbMsg.getRunway();
		exitFix = dsbMsg.getExitFix();
	}

	public boolean isDefault() {
		return leaderLineDirection == LeaderLineDirection.DEFAULT;
	}
}
