/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */
package com.downgoon.adindex4j.be;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import com.downgoon.adindex4j.notation.NotationDef;

public class Assignment {

	private String attribute;
	private Predicate predicate;
	private List<Object> multiValues;

	public Assignment(String attribute, Predicate predicate, Object... multiValues) {
		this.attribute = attribute;
		this.predicate = predicate;
		this.multiValues = Arrays.asList(multiValues);
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public Predicate getPredicate() {
		return predicate;
	}

	public void setPredicate(Predicate predicate) {
		this.predicate = predicate;
	}

	public Stream<Object> multiValues() {
		return multiValues.stream();
	}
	
	public int getValueCount() {
		return multiValues.size();
	}
	
	public Object getValue(int number) {
		return multiValues.get(number);
	}

	public void setMultiValues(List<Object> multiValues) {
		this.multiValues = multiValues;
	}

	public boolean isInclusive() {
		return predicate == Predicate.INCLUSIVE;
	}

	public boolean isExclusive() {
		return predicate == Predicate.EXCLUSIVE;
	}

	public boolean isSingleValued() {
		return multiValues != null && multiValues.size() == 1;
	}
	
	public boolean isMultiValued() {
		return multiValues != null && multiValues.size() > 1;
	}

	public Stream<AssignmentHashKey> hashKeys() {
		return multiValues.stream().map(value -> {
			return new AssignmentHashKey(attribute, value);
		});
	}

	
	@Override
	public String toString() {
		return ennotate();
	}

	public String ennotate() {
		StringBuffer sb = new StringBuffer();
		sb.append(attribute).append(predicate);
		int spotSize = sb.length();
		multiValues.stream().forEach(value -> {
			sb.append(value).append(NotationDef.OR);
		});
		if (sb.length() > spotSize) {
			sb.delete(sb.length() - NotationDef.OR.length(), sb.length());
		}
		return sb.toString();
	}

}
