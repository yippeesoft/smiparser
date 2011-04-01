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
package org.jsmiparser.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class TextUtil {
	private static final Logger LOG = Logger.getLogger(TextUtil.class);
	private static final String JAVA_KEYWORDS_PATH = "/org/jsmiparser/util/JavaKeywords.txt";
	static final String KEYWORD_PREFIX = "_";
	static Map<String, String> keywords = makeKeyWordMap();

	static public String makeCodeId(String id) {
		return makeCodeId(id, false);
	}

	static public String makeCodeId(String id, boolean isTypeName) {
		if (id == null || id.length() == 0) {
			id = String.valueOf("_");
		} else {
			StringBuilder buf = null;
			for (int i = 0; i < id.length(); i++) {
				if (!Character.isJavaIdentifierPart(id.charAt(i))) {
					if (buf == null) {
						buf = new StringBuilder(id);
					}
					buf.setCharAt(i, '_');
				}
			}

			if (buf != null) {
				if (isTypeName && Character.isLowerCase(buf.charAt(0))) {
					buf.setCharAt(0, Character.toUpperCase(buf.charAt(0)));
				}
				id = buf.toString();
			} else if (isTypeName && Character.isLowerCase(id.charAt(0))) {
				id = Character.toUpperCase(id.charAt(0)) + id.substring(1);
			}

			if (!Character.isJavaIdentifierStart(id.charAt(0))) {
				id = "_" + id;
			}
		}
		Object o = keywords.get(id);
		if (o != null) {
			id = (String) o;
		}
		return id;
	}

	public static String makeTypeName(String str) {
		return ucFirst(makeCodeId(str, true));
	}

	private static void addKeyWord(Map<String, String> m, String kw) {
		m.put(kw, KEYWORD_PREFIX + kw);
	}

	private static Map<String, String> makeKeyWordMap() {
		BufferedReader reader = null;
		try {
			Map<String, String> m = new HashMap<String, String>();
			reader = new BufferedReader(new InputStreamReader(
					TextUtil.class.getResourceAsStream(JAVA_KEYWORDS_PATH)));
			String keyword = reader.readLine();
			while (keyword != null) {
				addKeyWord(m, keyword);
				keyword = reader.readLine();
			}
			return m;
		} catch (Throwable e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				LOG.error(e.getMessage(), e);
			}
		}
	}

	public static String ucFirst(String str) {
		String result = str;
		if (str.length() > 0 && Character.isLowerCase(str.charAt(0))) {
			StringBuilder b = new StringBuilder(str);
			b.setCharAt(0, Character.toUpperCase(str.charAt(0)));
			result = b.toString();
		}
		return result;
	}

	public static String lcFirst(String str) {
		String result = str;
		if (str.length() > 0 && Character.isUpperCase(str.charAt(0))) {
			StringBuilder b = new StringBuilder(str);
			b.setCharAt(0, Character.toLowerCase(str.charAt(0)));
			result = b.toString();
		}
		return result;
	}

	public static String getPath(Package pkg) {
		LOG.debug("package: " + pkg);
		LOG.debug("getPath() for: " + pkg.getName());
		return "/" + pkg.getName().replace('.', '/');
	}

	public static String deleteChar(String str, char c) {
		if (str.indexOf(c) < 0) {
			return str;
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) != c) {
				sb.append(str.charAt(i));
			}
		}

		return sb.toString();
	}
}
