package io.wtmsb.nxf.manager;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.protobuf.ByteString;
import io.wtmsb.nxf.domain.FlightData;
import io.wtmsb.nxf.domain.RadarTarget;
import io.wtmsb.nxf.domain.Track;
import io.wtmsb.nxf.utility.GeoCalculator;
import io.wtmsb.nxf.validation.IcaoAddress;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static io.wtmsb.nxf.domain.IRadarComponent.TransponderMode;

@Service
public class TrackManager {
	private ControllerManager controllerManager;
	private final Map<RadarTarget, Track> targetTrackMap;
	private final ListMultimap<Track, RadarTarget> trackTargetMultiMap;

	private final int radarReturnCount = 6;

	@Autowired
	public TrackManager(ControllerManager controllerManager) {
		this();
		this.controllerManager = controllerManager;
	}

	public TrackManager() {
		// TODO: make private
		targetTrackMap = new ConcurrentHashMap<>();
		trackTargetMultiMap = LinkedListMultimap.create();
	}

	public void addTarget(RadarTarget target, @Min(2) @Max(10) String callsign) {
		if (target.getTransponderMode() == TransponderMode.MODE_C) {
			addCorrelatedTarget(target, callsign);
		} else if (target.getTransponderMode() == TransponderMode.MODE_S) {
			addCorrelatedTarget(target, callsign, target.getModeSAddress());
		} else {
			attemptTargetCorrelation(target);
		}
	}

	public void attemptTargetCorrelation(RadarTarget target) {
		if (target.getTransponderMode() == TransponderMode.PRIMARY) {
			addPrimaryTarget(target);
		} else {
			addModeATarget(target);
		}
	}

	public void addCorrelatedTarget(RadarTarget target, String callsign) {
		throw new UnsupportedOperationException();
	}

	public void addCorrelatedTarget(RadarTarget target, String callsign, @IcaoAddress ByteString address) {
		throw new UnsupportedOperationException();
	}

	@Synchronized
	public void addPrimaryTarget(RadarTarget target) {
		// find the closest track from the target
		Optional<RadarTarget> _closestTarget = targetTrackMap.keySet().stream()
				.min(Comparator.comparingDouble(o -> GeoCalculator.calculateDistance(o, target)));

		// generate a new track by default
		Track associatedTrack = new Track("PRI");

		if (_closestTarget.isPresent()) {
			RadarTarget closestTarget = _closestTarget.get();
			// associate the new target with the track associated with the closest target
			if (GeoCalculator.calculateDistance(closestTarget, target) <= 16.0 &&
					Duration.between(closestTarget.getReturnTime(), target.getReturnTime()).getSeconds() < 45)
			{
				// get the existing track if track's latest return isn't stale or not within 16 miles of the new target
				associatedTrack = targetTrackMap.get(closestTarget);
			}
		}

		targetTrackMap.put(target, associatedTrack);
		trackTargetMultiMap.put(associatedTrack, target);

		List<RadarTarget> targetsList = trackTargetMultiMap.get(associatedTrack);
		while (targetsList.size() > radarReturnCount) {
			targetTrackMap.remove(targetsList.remove(0));
		}
	}

	public void addModeATarget(RadarTarget target) {
		Track associatedTrack = new Track("MODE_A");
	}

	/**
	 *
	 */
	@Synchronized
	public void pruneStaleTracks() {
		//trackTargetMultiMap
	}
}
