package org.jsmiparser.util.token;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigInteger;

import org.jsmiparser.util.location.Location;
import org.junit.Test;

/*
 * Copyright 2007 Davy Verstappen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class BinaryStringTokenTest {
	@Test
	public void testGood() {
		BinaryStringToken t = new BinaryStringToken(new Location("testcase"),
				"'101'B");
		BigInteger value = t.getIntegerValue();
		assertEquals(5, value.intValue());
	}

	@Test
	public void testBad() {
		BinaryStringToken t = new BinaryStringToken(new Location("testcase"),
				"7FFF");
		try {
			t.getIntegerValue();
			fail();
		} catch (Exception ignored) {
			// ignore
		}
	}

	@Test
	public void testToString() {
		String str = "'101'B";
		BinaryStringToken t = new BinaryStringToken(new Location("testcase"),
				str);
		assertEquals(str, t.toString());
	}

}
