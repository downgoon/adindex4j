/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */

package com.downgoon.adindex4j.notation;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.downgoon.adindex4j.be.Assignment;
import com.downgoon.adindex4j.be.Predicate;
import com.downgoon.adindex4j.notation.AssignmentNotation;

public class AssignmentNotationTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEnnotate() {
		Assignment singleAssign = new Assignment("location", Predicate.INCLUSIVE, "北京");
		Assert.assertEquals("location=北京", singleAssign.ennotate());

		Assignment multiAssign = new Assignment("location", Predicate.INCLUSIVE, "北京", "上海", "深圳");
		Assert.assertEquals("location=北京|上海|深圳", multiAssign.ennotate());

		Assignment exclusiveAssign = new Assignment("location", Predicate.EXCLUSIVE, "北京", "上海");
		Assert.assertEquals("location!=北京|上海", exclusiveAssign.ennotate());
	}

	@Test
	public void testDenotate() {
		AssignmentNotation assignNotation = new AssignmentNotation();

		// single-valued case
		Assignment assign = assignNotation.denotate("location=北京");
		Assert.assertEquals("location", assign.getAttribute());
		Assert.assertEquals(Predicate.INCLUSIVE, assign.getPredicate());
		Assert.assertEquals(1, assign.getValueCount());
		Assert.assertEquals("北京", assign.getValue(0));

		// multi-valued case
		assign = assignNotation.denotate("location=北京|上海|深圳");
		Assert.assertEquals("location", assign.getAttribute());
		Assert.assertEquals(Predicate.INCLUSIVE, assign.getPredicate());
		Assert.assertEquals(3, assign.getValueCount());
		Assert.assertEquals("北京", assign.getValue(0));
		Assert.assertEquals("上海", assign.getValue(1));
		Assert.assertEquals("深圳", assign.getValue(2));

		// exclusive case
		assign = assignNotation.denotate("location!=北京|上海");
		Assert.assertEquals("location", assign.getAttribute());
		Assert.assertEquals(Predicate.EXCLUSIVE, assign.getPredicate());
		Assert.assertEquals(2, assign.getValueCount());
		Assert.assertEquals("北京", assign.getValue(0));
		Assert.assertEquals("上海", assign.getValue(1));

		// white spaces trimmed
		assign = assignNotation.denotate("location != 北京 | 上海");
		Assert.assertEquals("location", assign.getAttribute());
		Assert.assertEquals(Predicate.EXCLUSIVE, assign.getPredicate());
		Assert.assertEquals(2, assign.getValueCount());
		Assert.assertEquals("北京", assign.getValue(0));
		Assert.assertEquals("上海", assign.getValue(1));
	}

}
