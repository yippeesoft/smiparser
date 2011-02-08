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
package org.jsmiparser.smi;

import org.jsmiparser.util.token.IdToken;
import org.jsmiparser.util.token.QuotedStringToken;
import org.jsmiparser.phase.xref.XRefProblemReporter;

import java.util.List;

/**
 * Besides the OBJECT-TYPE fields that are specific to SNMP variable
 * definitions, this class also contains some methods that make it easier to
 * deal with the recursive nature of the SmiType definitions.
 */
public class SmiVariable extends SmiObjectType {
	private final QuotedStringToken unitsToken;
	private final SmiDefaultValue defaultValue;

	public SmiVariable(IdToken idToken, SmiModule module, SmiType type,
			QuotedStringToken unitsToken, SmiDefaultValue defaultValue) {
		super(idToken, module);
		setType(type);
		this.unitsToken = unitsToken;
		this.defaultValue = defaultValue;
		if (this.defaultValue != null) {
			this.defaultValue.variable = this;
		}
	}

	public String getCodeConstantId() {
		return getModule().getMib().getCodeNamingStrategy()
				.getCodeConstantId(this);
	}

	public String getFullCodeConstantId() {
		return getModule().getMib().getCodeNamingStrategy()
				.getFullCodeConstantId(this);
	}

	public String getCodeOid() {
		return getOidStr();
	}

	public String getCodeId() {
		return getModule().getMib().getCodeNamingStrategy().getVariableId(this);
	}

	public String getRequestMethodId() {
		return getModule().getMib().getCodeNamingStrategy()
				.getRequestMethodId(this);
	}

	public String getGetterMethodId() {
		return getModule().getMib().getCodeNamingStrategy()
				.getGetterMethodId(this);
	}

	public String getSetterMethodId() {
		return getModule().getMib().getCodeNamingStrategy()
				.getSetterMethodId(this);
	}

	public SmiRow getRow() {
		if (getNode() != null && getNode().getParent() != null) {
			SmiOidValue oidValue = getNode().getParent().getSingleValue(
					SmiOidValue.class, getModule());
			if (oidValue instanceof SmiRow) {
				return (SmiRow) oidValue;
			}
		}
		return null;
	}

	public SmiTable getTable() {
		SmiRow row = getRow();
		if (row != null) {
			return row.getTable();
		}
		return null;
	}

	public boolean isColumn() {
		return getRow() != null;
	}

	public boolean isScalar() {
		return getRow() == null;
	}

	public String getUnits() {
		return unitsToken != null ? unitsToken.getValue() : null;
	}

	public QuotedStringToken getUnitsToken() {
		return unitsToken;
	}

	public SmiTextualConvention getTextualConvention() {
		SmiType smiType = type;
		while (smiType != null) {
			if (smiType instanceof SmiTextualConvention) {
				return (SmiTextualConvention) smiType;
			}
			smiType = smiType.getBaseType();
		}
		return null;
	}

	public SmiPrimitiveType getPrimitiveType() {
		return type.getPrimitiveType();
	}

	public List<SmiNamedNumber> getEnumValues() {
		SmiType smiType = type;
		while (smiType != null) {
			if (smiType.getEnumValues() != null) {
				return smiType.getEnumValues();
			}
			smiType = smiType.getBaseType();
		}
		return null;
	}

	public List<SmiNamedNumber> getBitFields() {
		SmiType smiType = type;
		while (smiType != null) {
			if (smiType.getBitFields() != null) {
				return smiType.getBitFields();
			}
			smiType = smiType.getBaseType();
		}
		return null;
	}

	public List<SmiRange> getRangeConstraints() {
		SmiType smiType = type;
		while (smiType != null) {
			if (smiType.getRangeConstraints() != null) {
				return smiType.getRangeConstraints();
			}
			smiType = smiType.getBaseType();
		}
		return null;
	}

	public List<SmiRange> getSizeConstraints() {
		SmiType smiType = type;
		while (smiType != null) {
			if (smiType.getSizeConstraints() != null) {
				return smiType.getSizeConstraints();
			}
			smiType = smiType.getBaseType();
		}
		return null;
	}

	public SmiDefaultValue getDefaultValue() {
		return defaultValue;
	}

	public SmiNamedNumber resolveBitField(IdToken idToken,
			XRefProblemReporter reporter) {
		for (SmiNamedNumber nn : getBitFields()) {
			if (nn.getId().equals(idToken.getId())) {
				return nn;
			}
		}
		reporter.reportCannotFindBitField(idToken);
		return null;
	}

	public SmiNamedNumber resolveEnumConstant(IdToken idToken,
			XRefProblemReporter reporter) {
		for (SmiNamedNumber nn : getEnumValues()) {
			if (nn.getId().equals(idToken.getId())) {
				return nn;
			}
		}
		reporter.reportCannotFindEnumConstant(idToken);
		return null;
	}
}
