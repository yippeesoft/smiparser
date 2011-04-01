/**
 * Copyright 2011-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jsmiparser.util.location;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class LocationTest {
	@Test
	public void createLocation() {
		Location location = new Location("text.txt", 9, 10);
		assertEquals(10, location.getColumn());
		assertEquals(9, location.getLine());
		assertEquals("text.txt", location.getSource());
		assertEquals("text.txt:9:10", location.toString());

		location = new Location("text.txt", 8);
		location.setColumn(20);
		location.setLine(10);
		location.setSource("text.mib");

		assertEquals("text.mib:10:20", location.toString());
		
		location = new Location("text.txt");
		assertNotNull(location);
	}
}
