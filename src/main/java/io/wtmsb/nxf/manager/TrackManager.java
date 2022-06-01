package io.wtmsb.nxf.manager;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import io.wtmsb.nxf.domain.RadarTarget;
import io.wtmsb.nxf.domain.Track;
import io.wtmsb.nxf.utility.GeoCalculator;
import lombok.NonNull;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static io.wtmsb.nxf.domain.IRadarComponent.TransponderMode;

@Component
public class TrackManager {
	private ControllerManager controllerManager;
	private final Map<RadarTarget, Track> targetTrackMap;
	private final ListMultimap<Track, RadarTarget> trackTargetMultiMap;
	private final Map<String, Track> trackByCallsignMap;

	private final int radarReturnCount = 6;

	{
		targetTrackMap = new ConcurrentHashMap<>();
		trackTargetMultiMap = LinkedListMultimap.create();
		trackByCallsignMap = new ConcurrentHashMap<>();
	}

	private TrackManager() {}

	@Autowired
	public TrackManager(ControllerManager controllerManager) {
		this();
		this.controllerManager = controllerManager;
	}

	@Synchronized
	public Map<RadarTarget, Track> getTargetTrackMap() {
		return targetTrackMap;
	}

	@Synchronized
	public ListMultimap<Track, RadarTarget> getTrackRadarTargetMultiMap() {
		return trackTargetMultiMap;
	}

	@Synchronized
	public Map<String, Track> getTrackByCallsignMap() {
		return trackByCallsignMap;
	}

	public void addTarget(RadarTarget target, @NonNull String callsign) {
		if (callsign.length() == 0) {
			addTargetAndAutoCorrelate(target);
		} else {
			if (target.getTransponderMode() == TransponderMode.MODE_C ||
					target.getTransponderMode() == TransponderMode.MODE_S) {
				// TODO: implement mode S correlation methods
				addCorrelatedTarget(target, callsign);
			} else {
				throw new IllegalArgumentException();
			}
		}
	}

	/**
	 * Add a target and attempt to correlate with a track.
	 *
	 * @param target new target to add
	 */
	@Synchronized
	private void addTargetAndAutoCorrelate(RadarTarget target) {
		throw new UnsupportedOperationException("Auto correlation has not been implemented");
	}

	/**
	 * Add a Mode A/C target and correlate with the track with the given callsignHint
	 *
	 * @param target new target to add
	 * @param callsignHint hint from Nxf data source
	 */
	@Synchronized
	private void addCorrelatedTarget(RadarTarget target, String callsignHint) {
		Track correlatedTrack = trackByCallsignMap.computeIfAbsent(callsignHint, Track::new);
		trackTargetMultiMap.put(correlatedTrack, target);
		targetTrackMap.put(target, correlatedTrack);
		trimHistoryRadarTarget(correlatedTrack);
	}

	/**
	 * Each add target method must call this method to remove excessive radar targets.
	 *
	 * @param track the track to operate on
	 */
	@Synchronized
	private void trimHistoryRadarTarget(Track track) {
		List<RadarTarget> targetsList = trackTargetMultiMap.get(track);
		while (targetsList.size() > radarReturnCount) {
			targetTrackMap.remove(targetsList.remove(0));
		}
	}

	/**
	 * Prune track if the last radar target's return time is over 45 seconds ago.
	 * TODO: implement coast/suspended track management
	 */
	@Synchronized
	private void pruneStaleTracks() {
		Instant currentInstant = Instant.now();

		List<Map.Entry<Track, Collection<RadarTarget>>> toBeRemoved = trackTargetMultiMap.asMap().entrySet().stream()
				.filter(entry -> {
					Optional<RadarTarget> newestTarget =
							entry.getValue().stream().max(Comparator.comparing(RadarTarget::getReturnTime));
					return newestTarget.filter(radarTarget ->
							Duration.between(radarTarget.getReturnTime(), currentInstant).getSeconds() > 45
					).isPresent();
				})
				.collect(Collectors.toList());

		toBeRemoved.forEach(trackCollectionEntry -> {
			targetTrackMap.keySet().removeAll(trackCollectionEntry.getValue());
			trackTargetMultiMap.removeAll(trackCollectionEntry.getKey());
			trackByCallsignMap.remove(trackCollectionEntry.getKey().getFlightData().getCallsign());
		});
	}

	@Synchronized
	private void addPrimaryTarget(RadarTarget target) {
		// find the closest track from the target
		Optional<RadarTarget> _closestTarget = targetTrackMap.keySet().stream()
				.min(Comparator.comparingDouble(o -> GeoCalculator.calculateDistance(o, target)));

		// generate a new track by default
		// TODO: does not work, Track requires unique callsign
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

		trimHistoryRadarTarget(associatedTrack);
	}
}
