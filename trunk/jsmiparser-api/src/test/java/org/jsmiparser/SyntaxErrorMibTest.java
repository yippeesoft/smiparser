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
package org.jsmiparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.jsmiparser.smi.SmiVersion;
import org.jsmiparser.util.problem.annotations.ProblemSeverity;
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
public class SyntaxErrorMibTest extends AbstractMibTestCase {

	public SyntaxErrorMibTest() {
		super(SmiVersion.V2, "SYNTAX-ERROR-MIB.txt");
	}

	@Test
	public void test() {
		assertNotNull(getMib());
		int severityCount = getParser().getProblemEventHandler()
				.getSeverityCount(ProblemSeverity.ERROR);
		assertEquals(1, severityCount);
	}
}
