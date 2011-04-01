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
package org.jsmiparser.util.token;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.jsmiparser.util.location.Location;
import org.junit.Test;

public class BigIntegerTokenTest {
	@Test
	public void createsBigIntegerToken() {
		BigIntegerToken token = new BigIntegerToken(100);
		assertEquals(100, token.getValue().intValue());
		assertEquals(100, token.getObject().intValue());

		assertEquals("hardcoded:0:", token.getLocation().toString());

		token = new BigIntegerToken(new Location("text.txt"), true, "10");
		assertNotNull(token);

		token = new BigIntegerToken(new Location("text.txt"), false, "10");
		assertNotNull(token);
	}
}
