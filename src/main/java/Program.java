import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimaps;
import io.wtmsb.nxf.message.radar.NxfRadar;
import io.wtmsb.nxf.object.DataBlockSupplement;
import io.wtmsb.nxf.object.RadarTarget;
import io.wtmsb.nxf.object.Track;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Program {
	public static void main(String[] args) {
		Map<RadarTarget, Track> forwardMap = Collections.synchronizedMap(new LinkedHashMap<>());
		LinkedListMultimap<Track, RadarTarget> reverseMap =
				Multimaps.invertFrom(Multimaps.forMap(forwardMap), LinkedListMultimap.create());

		Track t1 = new Track(1);
		Track t2 = new Track(2);
		assert !t1.equals(t2);

		for (int i = 0; i < 10; i++){
			RadarTarget rt1 = new RadarTarget(i);
			RadarTarget rt2 = new RadarTarget(i + 10);
			forwardMap.put(rt1, t1);
			reverseMap.put(t1, rt1);
			forwardMap.put(rt2, t2);
			reverseMap.put(t2, rt2);

			List<RadarTarget> targetsList = reverseMap.get(t1);
			while (targetsList.size() > 6) {
				forwardMap.remove(targetsList.remove(0));
			}
		}

		forwardMap.forEach((key, value) -> System.out.println(key + ": " + value));
		reverseMap.asMap().forEach((key, value) -> System.out.println(key + ": " + value));
	}
}
