package io.wtmsb.nxf.manager;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import com.google.protobuf.ByteString;
import io.wtmsb.nxf.object.FlightStrip;
import io.wtmsb.nxf.object.IRadarComponent;
import io.wtmsb.nxf.object.RadarTarget;
import io.wtmsb.nxf.object.Track;
import io.wtmsb.nxf.utility.GeoCalculator;
import lombok.Synchronized;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TrackManager {
	private final Map<RadarTarget, Track> targetTrackMap;
	private final ListMultimap<Track, RadarTarget> trackTargetMultiMap;

	private final Map<ByteString, Track> beaconTrackMap;

	private final Map<ByteString, Track> addressTrackMap;

	private final int radarReturnCount;


	public TrackManager() {
		this(6);
	}

	public TrackManager(int customRadarReturnCount) {
		targetTrackMap = new ConcurrentHashMap<>();
		trackTargetMultiMap = Multimaps.invertFrom(Multimaps.forMap(targetTrackMap),
				LinkedListMultimap.create());

		beaconTrackMap = new ConcurrentHashMap<>();
		addressTrackMap = new ConcurrentHashMap<>();
		radarReturnCount = customRadarReturnCount;
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
		Track associatedTrack = new Track();

		if (_closestTarget.isPresent()) {
			RadarTarget closestTarget = _closestTarget.get();
			LocalDateTime closestTargetTime =
					LocalDateTime.ofEpochSecond(closestTarget.getReturnTime(), 0, ZoneOffset.UTC);
			LocalDateTime newTargetTime =
					LocalDateTime.ofEpochSecond(newTarget.getReturnTime(), 0, ZoneOffset.UTC);

			// associate the new target with the track associated with the closest target
			if (GeoCalculator.calculateDistance(closestTarget, newTarget) <= 16.0 &&
					Duration.between(closestTargetTime, newTargetTime).getSeconds() < 45)
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

	@Synchronized
	public void pruneStaleTracks() {

	}
}
