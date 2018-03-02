/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */
package com.downgoon.adindex4j.notation;

import com.downgoon.adindex4j.be.DNF;

public class DNFNotation implements Notational<DNF> {

	private ConjunctionNotation conjunctionNotation = new ConjunctionNotation();

	@Override
	public String ennotate(DNF dnf) {
		StringBuffer sb = new StringBuffer();
		int spotSize = sb.length();
		dnf.conjunctions().forEach(conjunction -> {
			sb.append(conjunctionNotation.ennotate(conjunction));
			sb.append(NotationDef.OR);
		});
		if (sb.length() > spotSize) {
			sb.delete(sb.length() - NotationDef.OR.length(), sb.length());
		}
		return sb.toString();
	}

	private static final String SEP = ("\\" + NotationDef.CONJUNCTION_ENDED) + ("\\" + NotationDef.OR)
			+ ("\\" + NotationDef.CONJUNCTION_BEGIN);

	@Override
	public DNF denotate(String notation) {
		DNF dnf = new DNF();
		// e.g. (age=20|30^location=北京)|(gender=M^location=北京|上海)
		// TODO lexical analysis parser
		String trimmed = normalize(notation);
		String[] conjNotationsTxt = trimmed.split(SEP);
		for (String conjTxt : conjNotationsTxt) {
			dnf.or(conjunctionNotation.denotate(boundaryPatch(conjTxt)));
		}
		return dnf;
	}

	protected String normalize(String notation) {
		// trim while spaces
		return notation.replaceAll(" ", "");
	}

	/**
	 *
	 * return normal
	 * 
	 * @param splitNotation
	 *            (age=20|30^location=北京)|(gender=M^location=北京|上海) are split
	 *            into two parts by the separator ')|(', one is
	 *            '(age=20|30^location=北京' and the other is
	 *            'gender=M^location=北京|上海)'. both are needed to add paddings.
	 * 
	 */
	private String boundaryPatch(String splitNotation) {
		if (splitNotation.startsWith(NotationDef.CONJUNCTION_BEGIN)
				&& !splitNotation.endsWith(NotationDef.CONJUNCTION_ENDED)) {
			return splitNotation + NotationDef.CONJUNCTION_ENDED;
		}

		if (!splitNotation.startsWith(NotationDef.CONJUNCTION_BEGIN)
				&& splitNotation.endsWith(NotationDef.CONJUNCTION_ENDED)) {
			return NotationDef.CONJUNCTION_BEGIN + splitNotation;
		}

		return splitNotation;
	}

}
