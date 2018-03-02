/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */

package com.downgoon.adindex4j.notation;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.downgoon.adindex4j.be.Assignment;
import com.downgoon.adindex4j.be.Conjunction;
import com.downgoon.adindex4j.be.DNF;
import com.downgoon.adindex4j.be.Predicate;
import com.downgoon.adindex4j.notation.DNFNotation;

public class DNFNotationTest {

	private static final Logger LOG = LoggerFactory.getLogger(DNFNotationTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEnnotate() {
		DNF dnf = new DNF( //

				new Conjunction( //
						new Assignment("age", Predicate.INCLUSIVE, 20, 30), //
						new Assignment("location", Predicate.INCLUSIVE, "北京") //
				), //

				new Conjunction( //
						new Assignment("gender", Predicate.INCLUSIVE, "M"), //
						new Assignment("location", Predicate.INCLUSIVE, "北京", "上海") //
				) //
		);

		DNFNotation dnfNotation = new DNFNotation();
		String notation = dnfNotation.ennotate(dnf);

		LOG.info("notation: {}", notation);
		Assert.assertEquals("(age=20|30^location=北京)|(gender=M^location=北京|上海)", notation);
	}

	@Test
	public void testDenotate() {
		DNFNotation dnfNotation = new DNFNotation();
		DNF dnf = dnfNotation.denotate("(age=20|30^location=北京)|(gender=M^location=北京|上海)");
		doDenotateAsserts(dnf);

		// white spaces
		dnf = dnfNotation.denotate(" ( age = 20 | 30 ^ location = 北京 ) | ( gender = M ^ location = 北京 | 上海 ) ");
		doDenotateAsserts(dnf);

		LOG.info("dnf: {}", dnfNotation.ennotate(dnf));

	}

	private void doDenotateAsserts(DNF dnf) {
		Assert.assertEquals(2, dnf.getConjunctionCount());

		Assert.assertEquals("age", dnf.getConjunction(0).getAssignment(0).getAttribute());
		Assert.assertEquals(true, dnf.getConjunction(0).getAssignment(0).isInclusive());
		Assert.assertEquals(2, dnf.getConjunction(0).getAssignment(0).getValueCount());
		Assert.assertEquals("20", dnf.getConjunction(0).getAssignment(0).getValue(0));
		Assert.assertEquals("30", dnf.getConjunction(0).getAssignment(0).getValue(1));

		Assert.assertEquals("location", dnf.getConjunction(0).getAssignment(1).getAttribute());
		Assert.assertEquals(true, dnf.getConjunction(0).getAssignment(1).isInclusive());
		Assert.assertEquals(1, dnf.getConjunction(0).getAssignment(1).getValueCount());
		Assert.assertEquals("北京", dnf.getConjunction(0).getAssignment(1).getValue(0));

		Assert.assertEquals("gender", dnf.getConjunction(1).getAssignment(0).getAttribute());
		Assert.assertEquals(true, dnf.getConjunction(1).getAssignment(0).isInclusive());
		Assert.assertEquals(1, dnf.getConjunction(1).getAssignment(0).getValueCount());
		Assert.assertEquals("M", dnf.getConjunction(1).getAssignment(0).getValue(0));

		Assert.assertEquals("location", dnf.getConjunction(1).getAssignment(1).getAttribute());
		Assert.assertEquals(true, dnf.getConjunction(1).getAssignment(1).isInclusive());
		Assert.assertEquals(2, dnf.getConjunction(1).getAssignment(1).getValueCount());
		Assert.assertEquals("北京", dnf.getConjunction(1).getAssignment(1).getValue(0));
		Assert.assertEquals("上海", dnf.getConjunction(1).getAssignment(1).getValue(1));
	}

}
