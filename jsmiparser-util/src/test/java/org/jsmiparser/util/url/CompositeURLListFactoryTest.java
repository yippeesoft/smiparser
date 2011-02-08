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
package org.jsmiparser.util.url;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CompositeURLListFactoryTest {

	@Test
	public void testCompositeURLListFactory() throws Exception {
		CompositeURLListFactory factory = new CompositeURLListFactory();
		List<URLListFactory> children = createChildren();
		factory.setChildren(children);
		assertEquals(children, factory.getChildren());
		assertEquals(1, factory.create().size());
	}

	@Test
	public void testCompositeURLListFactoryWithMultiURLFactory()
			throws Exception {
		List<URLListFactory> children = createChildren();
		CompositeURLListFactory factory = new CompositeURLListFactory(children.get(0));
		assertEquals(children, factory.getChildren());
		assertEquals(1, factory.create().size());
	}

	private List<URLListFactory> createChildren() throws Exception {
		List<URLListFactory> children = new ArrayList<URLListFactory>();
		URLListFactory factory = createMock(URLListFactory.class);
		List<URL> urls = new ArrayList<URL>();
		URL url = this.getClass().getResource("log4j.properties");
		urls.add(url);
		expect(factory.create()).andReturn(urls);
		replay(factory);

		children.add(factory);
		return children;
	}

}
