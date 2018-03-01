/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */
package com.downgoon.adindex.notation;

import com.downgoon.adindex.be.Assignment;
import com.downgoon.adindex.be.Predicate;

public class AssignmentNotation implements Notational<Assignment> {

	private PredicateNotation predicateNotation = new PredicateNotation(); 
	
	@Override
	public String ennotate(Assignment assignment) {
		StringBuffer sb = new StringBuffer();
		sb.append(assignment.getAttribute());
		sb.append(predicateNotation.ennotate(assignment.getPredicate()));
		int spotSize = sb.length();
		assignment.multiValues().forEach(value -> {
			sb.append(value).append(NotationDef.OR);
		});
		if (sb.length() > spotSize) {
			sb.delete(sb.length() - NotationDef.OR.length(), sb.length());
		}
		return sb.toString();
	}

	@Override
	public Assignment denotate(String notation) {
		Predicate predicate = null;
		String attribute = null;
		String multiValuesNotation = null;

		int exclusiveIdx = notation.indexOf(NotationDef.EXCLUSIVE);
		if (exclusiveIdx != -1) {
			predicate = Predicate.EXCLUSIVE;
			attribute = notation.substring(0, exclusiveIdx).trim();
			multiValuesNotation = notation.substring(exclusiveIdx + NotationDef.EXCLUSIVE.length()).trim();
			return new Assignment(attribute, predicate, denoteMultiValues(multiValuesNotation));
		}

		int inclusiveIdx = notation.indexOf(NotationDef.INCLUSIVE);
		if (inclusiveIdx != -1) {
			predicate = Predicate.INCLUSIVE;
			attribute = notation.substring(0, inclusiveIdx).trim();
			multiValuesNotation = notation.substring(inclusiveIdx + NotationDef.INCLUSIVE.length()).trim();
			return new Assignment(attribute, predicate, denoteMultiValues(multiValuesNotation));
		}

		throw new IllegalArgumentException("bad notation for assignment: " + notation);
	}

	protected Object[] denoteMultiValues(String multiValuesNotation) {
		String[] stringValues = multiValuesNotation.split("\\" + NotationDef.OR);
		Object[] objectValues = new Object[stringValues.length];
		for (int i = 0; i < stringValues.length; i++) {
			objectValues[i] = stringValues[i].trim();
			// TODO Type Converting: Integer, Double
		}
		return objectValues;
	}

}
