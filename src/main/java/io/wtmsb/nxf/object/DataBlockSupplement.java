package io.wtmsb.nxf.object;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public final class DataBlockSupplement {
	public enum LeaderLineDirection {
		DEFAULT,
		NW, N, NE, W,
		HIDE,
		E, SW, S, SE
	}

	@NonNull @Builder.Default
	LeaderLineDirection leaderLineDirection = LeaderLineDirection.DEFAULT;

	@NonNull
	Integer assignedTemporaryAltitude;

	@NonNull @Builder.Default
	String pad1 = "";

	@NonNull @Builder.Default
	String pad2 = "";

	@NonNull @Builder.Default
	String runway = "";

	@NonNull @Builder.Default
	String exitFix = "";

	public static DataBlockSupplement getDefault() {
		return DataBlockSupplement.builder().build();
	}
}
