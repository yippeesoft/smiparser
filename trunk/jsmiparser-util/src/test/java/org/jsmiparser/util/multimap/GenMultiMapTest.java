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
package org.jsmiparser.util.multimap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class GenMultiMapTest {
	@Test
	public void testGenMultiMap() {
		GenMultiMap genMultiMap = GenMultiMap.hashMap();
		genMultiMap.put("1", "1");
		genMultiMap.put("2", "2");

		assertEquals(2, genMultiMap.size());
		assertTrue(genMultiMap.containsKey("1"));
		assertTrue(genMultiMap.containsValue("1"));
		assertTrue(!genMultiMap.isEmpty());

		assertEquals("1", genMultiMap.getOne("1"));

		assertTrue(genMultiMap.values().size() == 2);
		genMultiMap.remove("1");

		assertEquals(1, genMultiMap.size());

		genMultiMap.clear();
		assertEquals(0, genMultiMap.size());

		Map map = new HashMap();
		map.put("3", "3");
		genMultiMap.putAll(map);
		assertTrue(genMultiMap.containsKey("3"));

		List<String> list = new ArrayList<String>();
		list.add("4");
		genMultiMap.put("4", list);
		assertEquals(2, genMultiMap.size());

		assertEquals(list, genMultiMap.getAll("4"));

		assertEquals(0, genMultiMap.getAll("5").size());
	}
}
