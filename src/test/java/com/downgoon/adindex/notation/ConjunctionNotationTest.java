/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */

package com.downgoon.adindex.notation;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.downgoon.adindex.be.Assignment;
import com.downgoon.adindex.be.Conjunction;
import com.downgoon.adindex.be.Predicate;

public class ConjunctionNotationTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEnnotate() {
		// multi assignments
		Conjunction conjunction = new Conjunction(new Assignment("age", Predicate.INCLUSIVE, 20, 30)) //
				.and(new Assignment("location", Predicate.INCLUSIVE, "北京"));
		Assert.assertEquals("(age=20|30^location=北京)", conjunction.ennotate());

		// single assignments
		conjunction = new Conjunction(new Assignment("age", Predicate.INCLUSIVE, 20, 30));
		Assert.assertEquals("(age=20|30)", conjunction.ennotate());

		// exclusive assignment
		conjunction = new Conjunction(new Assignment("age", Predicate.INCLUSIVE, 20, 30), //
				new Assignment("location", Predicate.INCLUSIVE, "北京"), //
				new Assignment("keywords", Predicate.EXCLUSIVE, "最好", "第一"));
		Assert.assertEquals("(age=20|30^location=北京^keywords!=最好|第一)", conjunction.ennotate());
	}

	@Test
	public void testDenotate() {
		ConjunctionNotation conjunctionNotation = new ConjunctionNotation();

		// multi assignments
		Conjunction conjunction = conjunctionNotation.denotate("(age=20|30|40^location=北京)");
		doDenoateAsserts(conjunction);
		
		// white spaces
		conjunction = conjunctionNotation.denotate("( age = 20 | 30 | 40 ^ location = 北京 )");
		doDenoateAsserts(conjunction);
		
		// no boundary
		conjunction = conjunctionNotation.denotate(" age = 20 | 30 | 40 ^ location = 北京 ");
		doDenoateAsserts(conjunction);
		
	}

	private void doDenoateAsserts(Conjunction conjunction) {
		Assert.assertEquals(2, conjunction.getAssignmentCount());
		Assert.assertEquals("age", conjunction.getAssignment(0).getAttribute());
		Assert.assertEquals(true, conjunction.getAssignment(0).isInclusive());
		Assert.assertEquals(3, conjunction.getAssignment(0).getValueCount());
		Assert.assertEquals("20", conjunction.getAssignment(0).getValue(0));
		Assert.assertEquals("30", conjunction.getAssignment(0).getValue(1));
		Assert.assertEquals("40", conjunction.getAssignment(0).getValue(2));
	}

}
