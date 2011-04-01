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

import org.jsmiparser.phase.xref.XRefProblemReporter;
import org.jsmiparser.util.token.IdToken;

import java.util.ArrayList;
import java.util.List;

public class SmiType extends SmiSymbol {

	public final static SmiPrimitiveType[] APPLICATION_TYPES = {
			SmiPrimitiveType.IP_ADDRESS, SmiPrimitiveType.COUNTER_32,
			SmiPrimitiveType.GAUGE_32,
			// SmiPrimitiveType.UNSIGNED_32, has the same application tag as
			// GAUGE_32
			SmiPrimitiveType.TIME_TICKS, SmiPrimitiveType.OPAQUE, null, // tag 5
																		// is
																		// undefined
			SmiPrimitiveType.COUNTER_64 };

	private SmiType baseType;
	private final SmiPrimitiveType primitiveType;
	private List<SmiNamedNumber> enumValues;
	private List<SmiNamedNumber> bitFields;
	private List<SmiRange> rangeConstraints;
	private List<SmiRange> sizeConstraints;
	private List<SmiField> fields;
	private IdToken elementTypeToken;
	private SmiType elementType;

	public SmiType(IdToken idToken, SmiModule module,
			SmiPrimitiveType primitiveType) {
		super(idToken, module);
		this.primitiveType = primitiveType;
	}

	public SmiType(IdToken idToken, SmiModule module, int applicationTag) {
		super(idToken, module);
		if (applicationTag >= 0) {
			if (applicationTag >= APPLICATION_TYPES.length) {
				throw new IllegalArgumentException("Application tag "
						+ applicationTag + " is invalid at: " + idToken);
			}
			if (APPLICATION_TYPES[applicationTag] == SmiPrimitiveType.GAUGE_32
					&& "Unsigned32".equals(getId())) {
				primitiveType = SmiPrimitiveType.UNSIGNED_32;
			} else {
				primitiveType = APPLICATION_TYPES[applicationTag];
			}
		} else {
			primitiveType = null;
		}
	}

	public SmiType(IdToken idToken, SmiModule module) {
		super(idToken, module);
		primitiveType = null;

		/*
		 * if (idToken != null) { String id = idToken.getId(); if
		 * (id.equals("Integer32")) { m_primitiveType =
		 * SmiPrimitiveType.INTEGER_32; } else if (id.equals("")) {
		 * m_primitiveType = SmiPrimitiveType.; } else if (id.equals("")) {
		 * m_primitiveType = SmiPrimitiveType.; } else if (id.equals("")) {
		 * m_primitiveType = SmiPrimitiveType.; } else if (id.equals("")) {
		 * m_primitiveType = SmiPrimitiveType.; } else if (id.equals("")) {
		 * m_primitiveType = SmiPrimitiveType.; } else if (id.equals("")) {
		 * m_primitiveType = SmiPrimitiveType.; } else if (id.equals("")) {
		 * m_primitiveType = SmiPrimitiveType.; } }
		 */
	}

	/**
	 * The base type from which this type is derived (by giving it named
	 * numbers, constraints, a name...). All types have a base type, except
	 * INTEGER, OCTET STRING, OBJECT IDENTIFIER and BITS.
	 */
	public SmiType getBaseType() {
		return baseType;
	}

	public void setBaseType(SmiType baseType) {
		this.baseType = baseType;
	}

	public SmiPrimitiveType getPrimitiveType() {
		if (enumValues != null) {
			return SmiPrimitiveType.ENUM;
		}
		if (bitFields != null) {
			return SmiPrimitiveType.BITS;
		}

		// TODO fix this hack
		// m_baseType != null && m_baseType == SmiConstants.INTEGER_TYPE
		if ("Integer32".equals(getId())
				&& "SNMPv2-SMI".equals(getModule().getId())) {
			return SmiPrimitiveType.INTEGER_32;
		}

		if (primitiveType == null && baseType != null) {
			return baseType.getPrimitiveType();
		}

		return primitiveType;
	}

	public SmiVarBindField getVarBindField() {
		return getPrimitiveType().getVarBindField();
	}

	public List<SmiNamedNumber> getEnumValues() {
		return enumValues;
	}

	public void setEnumValues(List<SmiNamedNumber> enumValues) {
		if (enumValues != null) {
			setType(enumValues);
		}
		this.enumValues = enumValues;
	}

	private void setType(List<SmiNamedNumber> enumValues) {
		for (SmiNamedNumber namedNumber : enumValues) {
			namedNumber.setType(this);
		}
	}

	public List<SmiNamedNumber> getBitFields() {
		return bitFields;
	}

	public void setBitFields(List<SmiNamedNumber> bitFields) {
		if (bitFields != null) {
			setType(bitFields);
		}
		this.bitFields = bitFields;
	}

	public String getCodeId() {
		return getModule().getMib().getCodeNamingStrategy().getTypeId(this);
	}

	public SmiNamedNumber getBiggestEnumValue() {
		int currentBiggest = Integer.MIN_VALUE;
		SmiNamedNumber result = null;
		for (SmiNamedNumber ev : enumValues) {
			if (ev.getValue().intValue() > currentBiggest) {
				currentBiggest = ev.getValue().intValue();
				result = ev;
			}
		}
		return result;
	}

	public SmiNamedNumber getSmallestEnumValue() {
		int currentSmallest = Integer.MAX_VALUE;
		SmiNamedNumber result = null;

		for (SmiNamedNumber ev : enumValues) {
			if (ev.getValue().intValue() < currentSmallest) {
				currentSmallest = ev.getValue().intValue();
				result = ev;
			}
		}
		return result;
	}

	public SmiNamedNumber findEnumValue(int i) {
		for (SmiNamedNumber ev : enumValues) {
			if (ev.getValue().intValue() == i) {
				return ev;
			}
		}
		return null;
	}

	public SmiNamedNumber findEnumValue(String id) {
		for (SmiNamedNumber ev : enumValues) {
			if (ev.getId().equals(id)) {
				return ev;
			}
		}
		return null;
	}

	public List<SmiRange> getRangeConstraints() {
		return rangeConstraints;
	}

	public void setRangeConstraints(List<SmiRange> rangeConstraints) {
		this.rangeConstraints = rangeConstraints;
	}

	public List<SmiRange> getSizeConstraints() {
		return sizeConstraints;
	}

	public void setSizeConstraints(List<SmiRange> sizeConstraints) {
		this.sizeConstraints = sizeConstraints;
	}

	public void addField(IdToken col, SmiType fieldType) {
		if (fields == null) {
			fields = new ArrayList<SmiField>();
		}
		fields.add(new SmiField(this, col, fieldType));
	}

	public List<SmiField> getFields() {
		return fields;
	}

	public IdToken getElementTypeToken() {
		return elementTypeToken;
	}

	public void setElementTypeToken(IdToken elementTypeToken) {
		this.elementTypeToken = elementTypeToken;
	}

	public SmiType getElementType() {
		return elementType;
	}

	public void setElementType(SmiType elementType) {
		// TODO set primitive type
		this.elementType = elementType;
	}

	public SmiType resolveThis(XRefProblemReporter reporter, SmiType ignored) {
		if (baseType != null) {
			baseType = baseType.resolveThis(reporter, this);
		}
		return this;
	}

	public void resolveReferences(XRefProblemReporter reporter) {
		assert (getIdToken() != null);
		assert (!(this instanceof SmiReferencedType));

		if (baseType != null) {
			baseType = baseType.resolveThis(reporter, this);
		}

		if (elementTypeToken != null) {
			elementType = getModule().resolveReference(elementTypeToken,
					SmiType.class, reporter);
		}
		if (fields != null) {
			for (SmiField field : fields) {
				field.resolveReferences(reporter);
			}
		}
	}
}
