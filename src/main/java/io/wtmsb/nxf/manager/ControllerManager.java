package io.wtmsb.nxf.manager;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import io.wtmsb.nxf.domain.ControllingUnit;
import io.wtmsb.nxf.message.radar.NxfRadar;
import lombok.NonNull;
import lombok.Synchronized;
import org.springframework.stereotype.Component;

@Component
public final class ControllerManager {
	private static final ControllingUnit defaultControllingUnit = new ControllingUnit("", "");
	private static final Table<String, String, ControllingUnit> cache = HashBasedTable.create();

	static {
		cache.put("", "", defaultControllingUnit);
	}

	@Synchronized
	public static ControllingUnit getControllingUnit(String facility, String sector) {
		if (!cache.contains(facility, sector)) {
			cache.put(facility, sector, new ControllingUnit(facility, sector));
		}

		return cache.get(facility, sector);
	}

	public static ControllingUnit getControllingUnit(NxfRadar.Track.@NonNull ControllingUnit nxfCu) {
		String facility = nxfCu.getFacility(), sector = nxfCu.getSector();
		return getControllingUnit(facility, sector);
	}

	public static ControllingUnit getDefaultControllingUnit() {
		return defaultControllingUnit;
	}
}
