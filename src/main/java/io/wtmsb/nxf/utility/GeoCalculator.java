package io.wtmsb.nxf.utility;

import io.wtmsb.nxf.domain.RadarTarget;
import org.locationtech.spatial4j.distance.DistanceUtils;

public final class GeoCalculator {

	public static double calculateDistance(RadarTarget tgt1, RadarTarget tgt2) {
		double lat1 = tgt1.getLat(), lat2 = tgt2.getLat(),
					 lon1 = tgt1.getLon(), lon2 = tgt2.getLon();

		return DistanceUtils.distVincentyRAD(lat1, lon1, lat2, lon2);
	}
}
