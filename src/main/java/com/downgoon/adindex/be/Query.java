/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */
package com.downgoon.adindex.be;

public class Query extends Conjunction {

	
	public Query(Assignment... assignments) {
		super(assignments);
	}

	@Override
	public Conjunction and(Assignment assignment) {
		doCheckAssignment(assignment);
		return super.and(assignment);
	}
	
	protected void doCheckAssignment(Assignment assignment) {
		if (!assignment.isInclusive()) {
			throw new IllegalArgumentException("assignment in query MUST be inclusive");
		}
	}

}
