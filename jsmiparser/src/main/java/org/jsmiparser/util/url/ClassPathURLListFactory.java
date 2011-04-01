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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ClassPathURLListFactory extends AbstractURLListFactory {
	protected ClassLoader classLoader;

	public ClassPathURLListFactory() {
		super();
	}

	public ClassPathURLListFactory(String rootPath) {
		super(rootPath);
	}

	public ClassPathURLListFactory(String rootPath, List<String> children) {
		super(rootPath, children);
	}

	public ClassLoader getClassLoader() {
		if (classLoader != null) {
			return classLoader;
		}
		return Thread.currentThread().getContextClassLoader();
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public List<URL> create() {
		List<URL> result = new ArrayList<URL>();
		for (String child : children) {
			String path = getRootPath() + child; // explicitly no
			URL url = getClassLoader().getResource(path);
			if (url == null) {
				throw new IllegalStateException(
						"Classpath resource doesn't exist (perhaps you are missing a slash or have one too much at the beginning?): "
								+ path);
			}
			result.add(url);
		}
		return result;
	}
}
