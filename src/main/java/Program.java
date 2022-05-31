import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimaps;
import io.wtmsb.nxf.domain.RadarTarget;
import io.wtmsb.nxf.domain.Track;

import java.util.*;

public class Program {
	public static void main(String[] args) {
		Map<RadarTarget, Track> forwardMap = Collections.synchronizedMap(new LinkedHashMap<>());
		LinkedListMultimap<Track, RadarTarget> reverseMap =
				Multimaps.invertFrom(Multimaps.forMap(forwardMap), LinkedListMultimap.create());

		Track t1 = new Track("N1");
		Track t2 = new Track("N2");
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

		forwardMap.forEach((key, value) ->
				System.out.println(key.getReturnTime().toEpochMilli()/1000 + ": " + value.getFlightData().getCallsign()));

		reverseMap.asMap().forEach(
				(key, value) -> {
					System.out.print(key.getFlightData().getCallsign() + ": ");
					value.forEach(x -> {
						System.out.print(x.getReturnTime().toEpochMilli()/1000 + " ");
					});
					System.out.println();
				});
	}
}
