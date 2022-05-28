package io.wtmsb.nxf.utility;

import io.wtmsb.nxf.message.radar.NxfRadar;
import io.wtmsb.nxf.object.RadarTarget;
import org.locationtech.spatial4j.distance.DistanceUtils;

public final class GeoCalculator {

	public static double calculateDistance(RadarTarget tgt1, RadarTarget tgt2) {
		double lat1 = Double.parseDouble(tgt1.getLat()),
					 lat2 = Double.parseDouble(tgt2.getLat()),
					 lon1 = Double.parseDouble(tgt1.getLon()),
					 lon2 = Double.parseDouble(tgt2.getLon());

		return DistanceUtils.distVincentyRAD(lat1, lon1, lat2, lon2);
	}
}
