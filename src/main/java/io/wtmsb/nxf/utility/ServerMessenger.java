package io.wtmsb.nxf.utility;

import io.wtmsb.nxf.message.radar.NxfRadarServer.*;

public final class ServerMessenger {
	private ServerMessenger() {}
	public static ServerRadarEvent createRadarEvent(Object msg, int eventFieldNumber) {
		ServerRadarEvent.Builder sb = ServerRadarEvent.newBuilder();

		return sb.build();
	}
}
