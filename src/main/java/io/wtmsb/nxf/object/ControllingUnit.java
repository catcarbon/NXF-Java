package io.wtmsb.nxf.object;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public final class ControllingUnit {
	@NonNull @Builder.Default
	String facility = "";

	@NonNull @Builder.Default
	String sector = "";

	public static ControllingUnit getDefault() {
		return ControllingUnit.builder().build();
	}
}
