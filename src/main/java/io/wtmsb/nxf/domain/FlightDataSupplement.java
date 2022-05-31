package io.wtmsb.nxf.domain;

import io.wtmsb.nxf.message.radar.NxfRadar;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

import static io.wtmsb.nxf.domain.IRadarComponent.*;

@Getter @Setter @EqualsAndHashCode
public final class FlightDataSupplement {
	private static final FlightDataSupplement DEFAULT = new FlightDataSupplement();

	@NonNull @Max(MAX_ALTITUDE)
	private Integer assignedTemporaryAltitude;

	@NonNull @Max(MAX_ALTITUDE)
	private Integer assignedFinalAltitude;

	@NonNull @Size(max = 5)
	private String pad1;

	@NonNull @Size(max = 5)
	private String pad2;

	@NonNull @Size(max = 3)
	private String runway;

	@NonNull @Size(max = 5)
	private String exitFix;

	@NonNull
	private LeaderLineDirection leaderLineDirection;

	private FlightDataSupplement() {
		this(NxfRadar.FlightDataSupplement.getDefaultInstance());
	}

	public FlightDataSupplement(NxfRadar.FlightDataSupplement supplementMessage) {
		assignedTemporaryAltitude = supplementMessage.getAssignedTemporaryAltitude();
		assignedFinalAltitude = supplementMessage.getAssignedFinalAltitude();
		pad1 = supplementMessage.getPad1();
		pad2 = supplementMessage.getPad2();
		runway = supplementMessage.getRunway();
		exitFix = supplementMessage.getExitFix();
		leaderLineDirection = getLeaderLineDirectionOrDefault(supplementMessage.getLeaderLineDirectionValue());
	}

	public boolean isDefault() {
		return this.equals(DEFAULT);
	}

	public static FlightDataSupplement getDefault() {
		return new FlightDataSupplement();
	}
}
