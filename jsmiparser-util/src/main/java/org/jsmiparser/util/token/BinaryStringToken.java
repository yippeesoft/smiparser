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

import java.math.BigInteger;

import org.jsmiparser.util.location.Location;

public class BinaryStringToken extends StringToken {

	private char radixChar;

	public BinaryStringToken(Location location, String value) {
		super(location, value.substring(1, value.length() - 2));
		radixChar = value.charAt(value.length() - 1);
	}

	public BigInteger getIntegerValue() {
		return new BigInteger(getValue(), 2);
	}

	public String toString() {
		return "'" + getValue() + "'" + radixChar;
	}

	public char getRadixChar() {
		return radixChar;
	}
}
