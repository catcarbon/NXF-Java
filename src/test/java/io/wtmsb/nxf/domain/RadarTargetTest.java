package io.wtmsb.nxf.domain;

import com.google.protobuf.ByteString;
import io.wtmsb.nxf.message.radar.NxfRadar;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RadarTargetTest {
	private static Validator validator;

	@BeforeAll
	public static void setUp() {
		try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
			validator = factory.getValidator();
		}
	}

	/**
	 * Testing malformed beacon code. <br/>
	 *
	 * @see RadarTarget#RadarTarget(NxfRadar.RadarTarget)
	 */
	@Test
	public void malformedBeaconCode() {
		/*
			If NxfRadar.RadarTarget#hasBeaconCode() == false, RadarTarget#RadarTarget(NxfRadar.RadarTarget)
			creates a primary target with default beacon code (i.e. validation will pass).
		 */
		List<RadarTarget> malformedBeaconCodeTargets = new LinkedList<>(Arrays.asList(
				new RadarTarget(NxfRadar.RadarTarget.newBuilder()
						.setBeaconCode(ByteString.fromHex("")).build()),
				new RadarTarget(NxfRadar.RadarTarget.newBuilder()
						.setBeaconCode(ByteString.fromHex("FF")).build()),
				new RadarTarget(NxfRadar.RadarTarget.newBuilder()
						.setBeaconCode(ByteString.fromHex("FFFF")).build()),
				new RadarTarget(NxfRadar.RadarTarget.newBuilder()
						.setBeaconCode(ByteString.fromHex("123456")).build())
		));

		for (RadarTarget target : malformedBeaconCodeTargets) {
			Set<ConstraintViolation<RadarTarget>> violationSet = validator.validate(target);

			assertEquals(1, violationSet.size());
			assertEquals(
					"{io.wtmsb.nxf.validation.BeaconCode.message}",
					violationSet.iterator().next().getMessage()
			);
		}
	}

	/**
	 * Testing malformed icao 24-bit address. <br/>
	 *
	 * @see RadarTarget#RadarTarget(NxfRadar.RadarTarget)
	 */
	@Test
	public void malformedIcaoAddress() {
		List<RadarTarget> malformedIcaoAddressTargets = new LinkedList<>(Arrays.asList(
				new RadarTarget(NxfRadar.RadarTarget.newBuilder()
						.setBeaconCode(ByteString.fromHex("0480"))
						.setReportedAltitude(10000)
						.setModeSAddress(ByteString.fromHex("")).build()),
				new RadarTarget(NxfRadar.RadarTarget.newBuilder()
						.setBeaconCode(ByteString.fromHex("0480"))
						.setReportedAltitude(10000)
						.setModeSAddress(ByteString.fromHex("00")).build()),
				new RadarTarget(NxfRadar.RadarTarget.newBuilder()
						.setBeaconCode(ByteString.fromHex("0480"))
						.setReportedAltitude(10000)
						.setModeSAddress(ByteString.fromHex("0000")).build()),
				new RadarTarget(NxfRadar.RadarTarget.newBuilder()
						.setBeaconCode(ByteString.fromHex("0480"))
						.setReportedAltitude(10000)
						.setModeSAddress(ByteString.fromHex("00000000")).build())
		));

		for (RadarTarget target : malformedIcaoAddressTargets) {
			Set<ConstraintViolation<RadarTarget>> violationSet = validator.validate(target);

			assertEquals(1, violationSet.size());
			assertEquals(
					"{io.wtmsb.nxf.validation.IcaoAddress.message}",
					violationSet.iterator().next().getMessage()
			);
		}
	}
}
