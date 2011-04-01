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
package org.jsmiparser.util.problem;

import java.util.Formatter;

import org.jsmiparser.util.location.Location;
import org.jsmiparser.util.problem.annotations.ProblemSeverity;

public class ProblemEvent {
	private Location location;
	private ProblemSeverity severity;
	private String messageKey;
	private String defaultMessage;
	private Object[] arguments;

	public ProblemEvent(Location location, ProblemSeverity severity,
			String messageKey, String defaultMessage, Object[] arguments) {
		this.location = location;
		this.severity = severity;
		this.messageKey = messageKey;
		this.defaultMessage = defaultMessage;
		this.arguments = arguments;
	}

	public Location getLocation() {
		return location;
	}

	public ProblemSeverity getSeverity() {
		return severity;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public Object[] getArguments() {
		return arguments;
	}

	/**
	 * @todo actually implement localization.
	 * @return The localized message
	 */
	public String getLocalizedMessage() {
		Formatter f = new Formatter();
		f.format(defaultMessage, arguments);
		return f.toString();
	}
}
