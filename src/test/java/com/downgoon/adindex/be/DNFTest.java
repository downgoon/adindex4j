/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */
package com.downgoon.adindex.be;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.downgoon.adindex.be.Assignment;
import com.downgoon.adindex.be.Conjunction;
import com.downgoon.adindex.be.DNF;
import com.downgoon.adindex.be.Predicate;
import com.downgoon.adindex.be.Query;

public class DNFTest {
	
	private static final Logger LOG = LoggerFactory.getLogger(DNFTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMatch() {

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

		// match first one
		Query query = new Query(new Assignment("age", Predicate.INCLUSIVE, 20), //
				new Assignment("location", Predicate.INCLUSIVE, "北京"));
		boolean matched = dnf.match(query);
		LOG.info("q: {}, r: {}", query, matched);
		Assert.assertEquals(true, matched);
		
		// match second one
		query = new Query(new Assignment("gender", Predicate.INCLUSIVE, "M"), //
				new Assignment("location", Predicate.INCLUSIVE, "上海"));
		matched = dnf.match(query);
		LOG.info("q: {}, r: {}", query, matched);
		Assert.assertEquals(true, matched);
		
		// not matched
		query = new Query(new Assignment("age", Predicate.INCLUSIVE, 20), //
				new Assignment("location", Predicate.INCLUSIVE, "上海"));
		matched = dnf.match(query);
		LOG.info("q: {}, r: {}", query, matched);
		Assert.assertEquals(false, matched);
		
	}

}
