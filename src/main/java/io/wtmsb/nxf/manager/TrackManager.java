package io.wtmsb.nxf.manager;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import io.wtmsb.nxf.domain.FlightStrip;
import io.wtmsb.nxf.domain.IRadarComponent;
import io.wtmsb.nxf.domain.RadarTarget;
import io.wtmsb.nxf.domain.Track;
import io.wtmsb.nxf.utility.GeoCalculator;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TrackManager {
	private ControllerManager controllerManager;
	private final Map<RadarTarget, Track> targetTrackMap;
	private final ListMultimap<Track, RadarTarget> trackTargetMultiMap;

	private final int radarReturnCount = 6;

	private final int updateFrequency = 3;

	@Autowired
	public TrackManager(ControllerManager controllerManager) {
		this();
		this.controllerManager = controllerManager;
	}

	public TrackManager() {
		targetTrackMap = new ConcurrentHashMap<>();
		trackTargetMultiMap = LinkedListMultimap.create();
	}

	public void addTarget(RadarTarget target) {
		if (target.getTransponderMode() == IRadarComponent.TransponderMode.MODE_A) {
			autoCorrelateTarget(target);
		} else if (target.getTransponderMode() == IRadarComponent.TransponderMode.MODE_C) {
			autoCorrelateTarget(target);
		} else if (target.getTransponderMode() == IRadarComponent.TransponderMode.MODE_S) {
			autoCorrelateTarget(target);
		} else {
			addPrimaryTarget(target);
		}
	}

	public boolean autoCorrelateTarget(RadarTarget target) {

		return false;
	}

	public boolean autoCorrelateFlightStrip(FlightStrip strip) {

		return false;
	}

	@Synchronized
	public void addPrimaryTarget(RadarTarget newTarget) {
		// find the closest track from the target
		Optional<RadarTarget> _closestTarget = targetTrackMap.keySet().stream()
				.min(Comparator.comparingDouble(o -> GeoCalculator.calculateDistance(o, newTarget)));

		// generate a new track by default
		Track associatedTrack = new Track("PRI");

		if (_closestTarget.isPresent()) {
			RadarTarget closestTarget = _closestTarget.get();
			// associate the new target with the track associated with the closest target
			if (GeoCalculator.calculateDistance(closestTarget, newTarget) <= 16.0 &&
					Duration.between(closestTarget.getReturnTime(), newTarget.getReturnTime()).getSeconds() < 45)
			{
				// get the existing track if track's latest return isn't stale or not within 16 miles of the new target
				associatedTrack = targetTrackMap.get(closestTarget);
			}
		}

		targetTrackMap.put(newTarget, associatedTrack);
		trackTargetMultiMap.put(associatedTrack, newTarget);

		List<RadarTarget> targetsList = trackTargetMultiMap.get(associatedTrack);
		while (targetsList.size() > radarReturnCount) {
			targetTrackMap.remove(targetsList.remove(0));
		}
	}

	/**
	 *
	 */
	@Synchronized
	public void pruneStaleTracks() {
		//trackTargetMultiMap
	}
}
