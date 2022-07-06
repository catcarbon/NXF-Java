package io.wtmsb.nxf.manager;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import io.wtmsb.nxf.domain.ControllingUnit;
import io.wtmsb.nxf.message.radar.NxfRadar;
import lombok.NonNull;
import lombok.Synchronized;
import org.springframework.stereotype.Service;

@Service
public final class ControllerManager {
	private static final ControllingUnit uncontrolledUnit;
	private static final Table<String, String, ControllingUnit> cache;

	static {
		uncontrolledUnit = new ControllingUnit(NxfRadar.ControllingUnit.getDefaultInstance());
		cache = HashBasedTable.create();
		cache.put(uncontrolledUnit.getFacility(), uncontrolledUnit.getSector(), uncontrolledUnit);
	}

	@Synchronized
	public static ControllingUnit getControllingUnit(String facility, String sector) {
		if (!cache.contains(facility, sector)) {
			cache.put(facility, sector, new ControllingUnit(facility, sector));
		}

		return cache.get(facility, sector);
	}

	public static ControllingUnit getControllingUnit(NxfRadar.@NonNull ControllingUnit nxfCu) {
		String facility = nxfCu.getFacility(), sector = nxfCu.getSector();
		return getControllingUnit(facility, sector);
	}

	public static ControllingUnit getUncontrolledUnit() {
		return uncontrolledUnit;
	}
}