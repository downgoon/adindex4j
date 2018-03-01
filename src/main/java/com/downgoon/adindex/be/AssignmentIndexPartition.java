/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */
package com.downgoon.adindex.be;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class AssignmentIndexPartition implements Comparable<AssignmentIndexPartition> {

	/**
	 * -1 indicating not inited
	 */
	private int conjunctionSize = -1;

	private Map<AssignmentHashKey, List<PredicatedConjunctionRef>> assignmentIndex = new HashMap<>();

	public AssignmentIndexPartition() {

	}

	public AssignmentIndexPartition(int conjunctionSize) {
		initCheckConjunctionSize(conjunctionSize);
	}

	public void append(Conjunction conjunction, long conjunctionId) {
		initCheckConjunctionSize(conjunction.sizeof());
		conjunction.assignments().forEach(assignment -> {
			assignment.hashKeys().forEach(hashKey -> {
				doAppend(hashKey, new PredicatedConjunctionRef(conjunctionId, assignment.getPredicate()));
			});

		});
	}

	protected void initCheckConjunctionSize(int conjunctionSize) {
		if (this.conjunctionSize < 0) {
			this.conjunctionSize = conjunctionSize;
			return; // init ok
		}

		if (this.conjunctionSize != conjunctionSize) {
			throw new IllegalArgumentException("different conjunction size not allowed in one partition");
		}
	}

	public List<PredicatedConjunctionRef> getPostingConjunctions(AssignmentHashKey assignmentHashKey) {
		return assignmentIndex.get(assignmentHashKey);
	}

	public boolean containsAssignment(AssignmentHashKey assignmentHashKey) {
		return assignmentIndex.containsKey(assignmentHashKey);
	}

	/**
	 * 
	 * contains assignment and its conjunctions predicates are all inclusive
	 */
	public boolean containsAssignmentInclusively(AssignmentHashKey assignmentHashKey) {
		List<PredicatedConjunctionRef> postingList = assignmentIndex.get(assignmentHashKey);
		if (postingList == null) {
			return false; // contains return false
		}

		boolean exclusiveFound = postingList.stream().anyMatch(cref -> cref.isExclusive());
		return !exclusiveFound;
	}

	protected void doAppend(AssignmentHashKey hashKey, PredicatedConjunctionRef predicatedConjunctionRef) {
		List<PredicatedConjunctionRef> postingList = assignmentIndex.get(hashKey);
		if (postingList == null) {
			postingList = new ArrayList<>();
			assignmentIndex.put(hashKey, postingList);
		}
		postingList.add(predicatedConjunctionRef);
	}

	public Stream<Map.Entry<AssignmentHashKey, List<PredicatedConjunctionRef>>> records() {
		return assignmentIndex.entrySet().stream();
	}

	@Override
	public String toString() {
		return "MyAssignmentIndexPartition [assignmentIndex=" + assignmentIndex + "]";
	}

	public String ennotate() {
		StringBuffer sb = new StringBuffer();
		records().forEach(assignmentHashRecord -> {
			sb.append(assignmentHashRecord.getKey()).append(" --> conjunctions") //
					.append(assignmentHashRecord.getValue());
			sb.append(System.lineSeparator());
		});
		return sb.toString();
	}

	@Override
	public int compareTo(AssignmentIndexPartition o) {
		/* compare by conjunction size but in descending order */
		return - (this.conjunctionSize - o.conjunctionSize);
	}

	public int getConjunctionSize() {
		return conjunctionSize;
	}

}
