/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */
package com.downgoon.adindex4j.be;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import com.downgoon.adindex4j.notation.NotationDef;

public class Conjunction {

	protected List<Assignment> assignments = new ArrayList<>();

	public Conjunction(Assignment... assignments) {
		this.assignments.addAll(Arrays.asList(assignments));
	}

	public Conjunction and(Assignment assignment) {
		this.assignments.add(assignment);
		return this;
	}

	/**
	 * testing a query whether it is satisfied for this conjunction
	 */
	public boolean match(Query query) {
		int k = this.sizeof(); // conjunction size
		int t = query.sizeof(); // query size
		if (!(k <= t)) {
			return false;
		}

		AssignmentIndexPartition cindex = this.hashIndex();

		int foundTimes = 0;
		boolean exclusiveFound = false;
		for (AssignmentHashKey qkey : query.hashKeys()) {
			if (cindex.containsAssignment(qkey)) {
				if (cindex.containsAssignmentInclusively(qkey)) {
					foundTimes++;
				} else {
					exclusiveFound = true;
					break;
				}
			}
		}

		if (exclusiveFound) {
			return false;
		}

		/*
		 * foundTimes > k supports multi-valued query such as
		 * age=20^keywords=年轻|时尚 against the conjunction
		 * age=20|30^keywords=年轻|时尚|潮男
		 */
		return foundTimes >= k;
	}

	/**
	 * the count of assignments which are inclusive
	 * 
	 * @return return inclusive size
	 */
	public int sizeof() {
		return getInclusiveSize();
	}

	/**
	 * the count of assignments which are inclusive
	 * 
	 * @return return inclusive size
	 */
	public int getInclusiveSize() {
		AtomicInteger size = new AtomicInteger();
		assignments.forEach(assignment -> {
			if (assignment.isInclusive()) {
				size.incrementAndGet();
			}
		});
		return size.get();
	}

	/**
	 * the count of assignments which are exclusive
	 * 
	 * @return return exclusive size
	 */
	public int getExclusiveSize() {
		AtomicInteger size = new AtomicInteger();
		assignments.forEach(assignment -> {
			if (assignment.isExclusive()) {
				size.incrementAndGet();
			}
		});
		return size.get();
	}

	/**
	 * the count of assignments which are inclusive or exclusive
	 * 
	 * @return return total size
	 */
	public int getAssignmentCount() {
		return assignments.size();
	}
	
	public Assignment getAssignment(int index) {
		return assignments.get(index);
	}

	/**
	 * assignment stream
	 */
	public Stream<Assignment> assignments() {
		return assignments.stream();
	}

	public HashSet<AssignmentHashKey> hashKeys() {
		HashSet<AssignmentHashKey> keys = new HashSet<>();
		this.assignments().forEach(assignment -> {
			assignment.hashKeys().forEach(hashKey -> {
				keys.add(hashKey); // remove duplications
			});
		});
		return keys;
	}

	public AssignmentIndexPartition hashIndex() {
		AssignmentIndexPartition index = new AssignmentIndexPartition();
		index.append(this, 0);
		return index;
	}

	@Override
	public String toString() {
		return ennotate();
	}

	public String ennotate() {
		StringBuffer sb = new StringBuffer();
		sb.append(NotationDef.CONJUNCTION_BEGIN);
		int spotSize = sb.length();
		assignments.stream().forEach(assignment -> {
			sb.append(assignment.ennotate());
			sb.append(NotationDef.AND);
		});
		if (sb.length() > spotSize) {
			sb.delete(sb.length() - NotationDef.AND.length(), sb.length());
		}
		sb.append(NotationDef.CONJUNCTION_ENDED);
		return sb.toString();
	}

}
