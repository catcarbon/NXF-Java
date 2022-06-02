package io.wtmsb.nxf.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static io.wtmsb.nxf.validation.CallsignValidator.illegalCharacterPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightDataTest {
	private static Validator validator;

	@BeforeAll
	public static void setUp() {
		try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
			validator = factory.getValidator();
		}
	}

	@Test
	public void malformedCallsign() {
		List<FlightData> malformedCallsignFDs = new LinkedList<>(Arrays.asList(
				new FlightData((String) null),  // null
				new FlightData(""),  // empty
				new FlightData(" "),  // contains no text
				new FlightData("N"),  // under length
				new FlightData("N1234567890123"),  // over length
				new FlightData("\u00a0JBU1\u2000")  // contains illegal character
		));

		for (FlightData data : malformedCallsignFDs) {
			Set<ConstraintViolation<FlightData>> violationSet = validator.validate(data);

			assertEquals(1, violationSet.size());
			assertEquals(
					"{io.wtmsb.nxf.validation.Callsign.message}",
					violationSet.iterator().next().getMessage()
			);
		}
	}
}
