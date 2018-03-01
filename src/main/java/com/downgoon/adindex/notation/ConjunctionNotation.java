/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */
package com.downgoon.adindex.notation;

import java.util.function.Supplier;

import com.downgoon.adindex.be.Assignment;
import com.downgoon.adindex.be.Conjunction;

public class ConjunctionNotation implements Notational<Conjunction> {

	private AssignmentNotation assignmentNotation = new AssignmentNotation();

	@Override
	public String ennotate(Conjunction conjunction) {
		StringBuffer sb = new StringBuffer(NotationDef.CONJUNCTION_BEGIN);
		int initSize = sb.length();
		conjunction.assignments().forEach(e -> {
			sb.append(e).append(NotationDef.AND);
		});
		if (sb.length() > initSize) {
			sb.delete(sb.length() - NotationDef.AND.length(), sb.length());
		}
		sb.append(NotationDef.CONJUNCTION_ENDED);
		return sb.toString();
	}

	@Override
	public Conjunction denotate(String notation) {
		return doDenotate(notation, () -> new Conjunction());
	}

	Conjunction doDenotate(String notation, Supplier<Conjunction> conjunctionSupplier) {
		Conjunction conjunction = conjunctionSupplier.get();
		notation = trimOptionalBoundary(notation);
		for (String assignmentNotationText : notation.split("\\" + NotationDef.AND)) {
			Assignment assign = assignmentNotation.denotate(assignmentNotationText.trim());
			conjunction.and(assign);
		}
		return conjunction;
	}

	protected String trimOptionalBoundary(String notation) {
		String trimmed = notation.trim();
		if (trimmed.startsWith(NotationDef.CONJUNCTION_BEGIN) && trimmed.endsWith(NotationDef.CONJUNCTION_ENDED)) {
			return trimmed.substring(NotationDef.CONJUNCTION_BEGIN.length(),
					trimmed.length() - NotationDef.CONJUNCTION_ENDED.length());
		} else {
			return trimmed;
		}
	}

}
