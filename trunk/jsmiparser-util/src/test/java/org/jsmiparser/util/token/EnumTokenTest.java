package org.jsmiparser.util.token;

import static org.junit.Assert.assertEquals;

import org.jsmiparser.util.location.Location;
import org.junit.Test;

public class EnumTokenTest {

	@Test
	public void testEnumToken() {
		Enum value = Day.FRIDAY;
		EnumToken token = new EnumToken(new Location("mib"), value);
		assertEquals(Day.FRIDAY, token.getValue());

		token = new EnumToken(new Location("mib"), Day.class, "FRIDAY");

	}

	public enum Day {
		SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
	}

}
