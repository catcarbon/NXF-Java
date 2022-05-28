package io.wtmsb.nxf.utility;

import io.wtmsb.nxf.message.radar.NxfRadarClient.*;

public final class ClientMessenger {
	public static ClientRadarEvent createRadarEvent(Object msg, int eventFieldNumber) {
		ClientRadarEvent.Builder cb = ClientRadarEvent.newBuilder();

		return cb.build();
	}
}
