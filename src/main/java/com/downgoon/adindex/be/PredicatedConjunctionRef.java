/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */
package com.downgoon.adindex.be;

public class PredicatedConjunctionRef {

	private long conjunctionId;

	private Predicate predicate;

	public PredicatedConjunctionRef(long conjunctionId, Predicate predicate) {
		super();
		this.conjunctionId = conjunctionId;
		this.predicate = predicate;
	}

	public long getConjunctionId() {
		return conjunctionId;
	}

	public void setConjunctionId(long conjunctionId) {
		this.conjunctionId = conjunctionId;
	}

	public boolean isInclusive() {
		return predicate == Predicate.INCLUSIVE;
	}

	public boolean isExclusive() {
		return predicate == Predicate.EXCLUSIVE;
	}

	public Predicate getPredicate() {
		return predicate;
	}

	public void setPredicate(Predicate predicate) {
		this.predicate = predicate;
	}

	@Override
	public String toString() {
		return ennotate();
	}
	
	public String ennotate() {
		return "(" + conjunctionId + ", " + (predicate == Predicate.INCLUSIVE ? "=" : "!=") + ")";
	}

}
