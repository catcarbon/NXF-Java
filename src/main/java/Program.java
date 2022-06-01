import com.google.common.collect.ListMultimap;
import com.google.protobuf.ByteString;
import io.wtmsb.nxf.domain.RadarTarget;
import io.wtmsb.nxf.domain.Track;
import io.wtmsb.nxf.manager.TrackManager;
import io.wtmsb.nxf.message.radar.NxfRadar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Component
@ComponentScan(basePackages = "io.wtmsb.nxf.manager")
public class Program {
	@Autowired
	private TrackManager manager;

	public static void main(String[] args) {
		try (GenericApplicationContext ctx = new AnnotationConfigApplicationContext(Program.class)) {
			var bean = ctx.getBean(Program.class);
			bean.run();
		}

	}

	public void run() {
		for (int i = 0; i < 10; i++){
			RadarTarget rt1 = new RadarTarget(
				NxfRadar.RadarTarget.newBuilder()
					.setReportedAltitude(10000)
					.setBeaconCode(ByteString.fromHex("0480"))
					.setReturnTime(i)
					.build()
			);
			RadarTarget rt2 = new RadarTarget(
				NxfRadar.RadarTarget.newBuilder()
				.setReportedAltitude(12000)
				.setBeaconCode(ByteString.fromHex("010481")) // should fail
				.setReturnTime(i + 10)
				.build()
			);

			manager.addTarget(rt1, "N1");
			manager.addTarget(rt2, "N2");
		}

		System.out.println("Showing all RadarTarget->Track...");
		manager.getTargetTrackMap().forEach((radarTarget, track) ->
			System.out.println(radarTarget.getReturnTime().toEpochMilli()/1000 + ": " + track.getFlightData().getCallsign())
		);

		System.out.println("Showing all Track->List<RadarTarget>...");
		ListMultimap<Track, RadarTarget> trackRadarTargetListMultimap = manager.getTrackRadarTargetMultiMap();
		trackRadarTargetListMultimap.asMap().forEach((track, radarTargetList) -> {
			System.out.print(track.getFlightData().getCallsign() + ": ");
			for (RadarTarget radarTarget : radarTargetList) {
				System.out.print(radarTarget.getReturnTime().toEpochMilli()/1000 + " ");
			}
			System.out.println();
		});

		System.out.println("Showing all String->Track...");
		manager.getTrackByCallsignMap().forEach((s, track) ->
			System.out.println(s + ": " + track.getFlightData().getCallsign())
		);
	}
}
