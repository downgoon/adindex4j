/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */
package com.downgoon.adindex4j.notation;

import com.downgoon.adindex4j.be.Predicate;

public class PredicateNotation implements Notational<Predicate> {

	@Override
	public String ennotate(Predicate predict) {
		return predict == Predicate.INCLUSIVE ? NotationDef.INCLUSIVE : NotationDef.EXCLUSIVE;
	}

	@Override
	public Predicate denotate(String notation) {
		if (Predicate.INCLUSIVE.equals(notation)) {
			return Predicate.INCLUSIVE;
		}
		if (Predicate.EXCLUSIVE.equals(notation)) {
			return Predicate.EXCLUSIVE;
		}

		throw new IllegalArgumentException("bad notation for Predicate: " + notation);
	}

}
