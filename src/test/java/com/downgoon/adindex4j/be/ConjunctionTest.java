/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */

package com.downgoon.adindex4j.be;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.downgoon.adindex4j.be.Assignment;
import com.downgoon.adindex4j.be.Conjunction;
import com.downgoon.adindex4j.be.Predicate;
import com.downgoon.adindex4j.be.Query;

public class ConjunctionTest {

	private static final Logger LOG = LoggerFactory.getLogger(ConjunctionTest.class);
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMatchSingleValuedQuery() {

		// location=北京^age=20|30
		Conjunction conjunction = new Conjunction(new Assignment("age", Predicate.INCLUSIVE, 20, 30)) //
				.and(new Assignment("location", Predicate.INCLUSIVE, "北京"));
		Assert.assertEquals(2, conjunction.sizeof());

		LOG.info("\r\ntestMatchSingleValuedQuery hash index of conjunction: {}\r\n", conjunction);
		LOG.info(conjunction.hashIndex().ennotate());

		// conjunctionSize == querySize
		Query query1 = new Query(new Assignment("age", Predicate.INCLUSIVE, 20), //
				new Assignment("location", Predicate.INCLUSIVE, "北京"));
		Assert.assertEquals(2, query1.sizeof());

		boolean matched1 = conjunction.match(query1);
		LOG.info("q: {}, r: {}", query1, matched1);
		Assert.assertTrue(matched1);

		// conjunctionSize < querySize
		Query query2 = new Query(new Assignment("age", Predicate.INCLUSIVE, 30), //
				new Assignment("location", Predicate.INCLUSIVE, "北京"), //
				new Assignment("gender", Predicate.INCLUSIVE, "M"));
		Assert.assertEquals(3, query2.sizeof());

		boolean matched2 = conjunction.match(query2);
		LOG.info("q: {}, r: {}", query2, matched2);
		Assert.assertTrue(matched2);

		// conjunctionSize > querySize
		Query query3 = new Query(new Assignment("location", Predicate.INCLUSIVE, "北京"));
		Assert.assertEquals(1, query3.sizeof());

		boolean matched3 = conjunction.match(query3);
		LOG.info("q: {}, r: {}", query3, matched3);
		Assert.assertFalse(matched3);

	}

	@Test
	public void testMatchMultiValuedQuery() {

		// age=20|30^keywords=年轻|时尚|潮男
		Conjunction conjunction = new Conjunction(new Assignment("age", Predicate.INCLUSIVE, 20, 30)) //
				.and(new Assignment("keywords", Predicate.INCLUSIVE, "年轻", "时尚", "潮男"));
		Assert.assertEquals(2, conjunction.sizeof());

		LOG.info("\r\ntestMatchMultiValuedQuery hash index of conjunction: {}\r\n", conjunction);
		LOG.info(conjunction.hashIndex().ennotate());

		// multi-valued query
		Query query1 = new Query(new Assignment("age", Predicate.INCLUSIVE, 20), //
				new Assignment("keywords", Predicate.INCLUSIVE, "年轻", "时尚", "靓丽"));
		Assert.assertEquals(2, query1.sizeof());

		boolean matched1 = conjunction.match(query1);
		LOG.info("q: {}, r: {}", query1, matched1);
		Assert.assertTrue(matched1);

	}
}
