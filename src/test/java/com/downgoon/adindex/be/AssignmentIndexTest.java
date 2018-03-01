/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */

package com.downgoon.adindex.be;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.downgoon.adindex.be.AssignmentIndex;
import com.downgoon.adindex.be.AssignmentIndexPartition;

public class AssignmentIndexTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLowerPartitions() {
		AssignmentIndex index = new AssignmentIndex();
		
		// append partition in unknown order
		index.appendPartition(new AssignmentIndexPartition(0));
		index.appendPartition(new AssignmentIndexPartition(3));
		
		index.appendPartition(new AssignmentIndexPartition(10));
		
		index.appendPartition(new AssignmentIndexPartition(4));
		index.appendPartition(new AssignmentIndexPartition(8));
		
		int querySize = 12;
		List<Integer> candidate = new ArrayList<>();
		index.lowerPartitions(querySize).forEach(p -> {
			candidate.add(p.getConjunctionSize());
		});
		Assert.assertEquals("[10, 8, 4, 3, 0]", candidate.toString());
		
		querySize = 10;
		candidate.clear();
		index.lowerPartitions(querySize).forEach(p -> {
			candidate.add(p.getConjunctionSize());
		});
		Assert.assertEquals("[10, 8, 4, 3, 0]", candidate.toString());
		
		querySize = 9;
		candidate.clear();
		index.lowerPartitions(querySize).forEach(p -> {
			candidate.add(p.getConjunctionSize());
		});
		Assert.assertEquals("[8, 4, 3, 0]", candidate.toString());
		
		querySize = 8;
		candidate.clear();
		index.lowerPartitions(querySize).forEach(p -> {
			candidate.add(p.getConjunctionSize());
		});
		Assert.assertEquals("[8, 4, 3, 0]", candidate.toString());
		
		querySize = 8;
		candidate.clear();
		index.lowerPartitions(querySize).forEach(p -> {
			candidate.add(p.getConjunctionSize());
		});
		Assert.assertEquals("[8, 4, 3, 0]", candidate.toString());
		
		querySize = -1;
		candidate.clear();
		index.lowerPartitions(querySize).forEach(p -> {
			candidate.add(p.getConjunctionSize());
		});
		Assert.assertEquals("[]", candidate.toString());
	}

	
	@Test
	public void testLowerPartitionsEmpty() {
		AssignmentIndex index = new AssignmentIndex();
		index.appendPartition(new AssignmentIndexPartition(3));
		
		int querySize = 2;
		List<Integer> candidate = new ArrayList<>();
		index.lowerPartitions(querySize).forEach(p -> {
			candidate.add(p.getConjunctionSize());
		});
		Assert.assertEquals("[]", candidate.toString());
	}
}
