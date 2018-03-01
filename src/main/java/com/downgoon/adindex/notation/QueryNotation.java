/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */
package com.downgoon.adindex.notation;

import com.downgoon.adindex.be.Conjunction;
import com.downgoon.adindex.be.Query;

public class QueryNotation implements Notational<Query> {

	private ConjunctionNotation conjunctionNotation = new ConjunctionNotation();

	@Override
	public String ennotate(Query query) {
		return conjunctionNotation.ennotate(query);
	}

	@Override
	public Query denotate(String notation) {
		Conjunction conjunction = conjunctionNotation.doDenotate(notation, () -> new Query());
		if (conjunction.getExclusiveSize() > 0) {
			throw new IllegalArgumentException("exclusive assignment cann't be found in query conjunction");
		}
		return (Query) conjunction;
	}

}
