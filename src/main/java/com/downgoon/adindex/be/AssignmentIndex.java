/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */
package com.downgoon.adindex.be;

import java.util.TreeSet;
import java.util.stream.Stream;

public class AssignmentIndex {

	/**
	 * a sorted set of index partitions in descending order by their conjunction
	 * size
	 */
	private TreeSet<AssignmentIndexPartition> indexPartitions = new TreeSet<>();

	public void appendPartition(AssignmentIndexPartition partition) {
		indexPartitions.add(partition);
	}

	public Stream<AssignmentIndexPartition> partitions() {
		return indexPartitions.stream();
	}

	/**
	 * get all lower partitions whose conjunction sizes are less than or equal
	 * to the given 'querySize'. the underlying search algorithm is
	 * binary-tree-search whose time complexity is o(logN).
	 * 
	 * @param querySize
	 *            high water mark indicating the <strong>sizeof</strong>
	 *            operation on query conjunction
	 */
	public Stream<AssignmentIndexPartition> lowerPartitions(int querySize) {

		AssignmentIndexPartition highWaterMark = new AssignmentIndexPartition(querySize);

		/*
		 * candidates under the high water mark. the candidates set is just a
		 * view of the portion of the original set, it is backed by the original
		 * one, so changes in candidates are reflected in the original set.
		 */
		return indexPartitions.tailSet(highWaterMark, true).stream();
	}

}
