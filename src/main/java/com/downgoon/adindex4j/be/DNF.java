/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */
package com.downgoon.adindex4j.be;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class DNF {

	private List<Conjunction> conjunctions = new ArrayList<>();

	public DNF(Conjunction... conjunctions) {
		this.conjunctions.addAll(Arrays.asList(conjunctions));
	}

	public DNF or(Conjunction conjunction) {
		conjunctions.add(conjunction);
		return this;
	}

	public boolean match(Query query) {
		return conjunctions.stream().anyMatch(conjunction -> conjunction.match(query));
	}

	public Stream<Conjunction> conjunctions() {
		return conjunctions.stream();
	}

	public int getConjunctionCount() {
		return conjunctions.size();
	}

	public Conjunction getConjunction(int index) {
		return conjunctions.get(index);
	}
	
}
