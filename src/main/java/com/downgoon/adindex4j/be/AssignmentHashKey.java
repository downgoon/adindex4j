/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */
package com.downgoon.adindex4j.be;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class AssignmentHashKey {

	private String attribute;

	private Object value;

	public AssignmentHashKey(String attribute, Object value) {
		super();
		this.attribute = attribute;
		this.value = value;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "(" + attribute + ", " + value + ")";
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append("(").append(this.attribute).append(",") //
				.append(this.value).append(")").toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AssignmentHashKey other = (AssignmentHashKey) obj;
		if (attribute == null) {
			if (other.attribute != null)
				return false;
		} else if (!attribute.equals(other.attribute))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
