/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */

package com.downgoon.adindex4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.downgoon.adindex4j.AdRetrival;
import com.downgoon.adindex4j.BEScaner;

public class BEScanerTest {
	
	private static final Logger LOG = LoggerFactory.getLogger(BEScanerTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRetrieveDocuments() {
		AdRetrival adRetrival = new BEScaner();
		adRetrival.appendDocument(1, "location=北京^gender=男");
		adRetrival.appendDocument(2, "location=上海^gender=女");
		adRetrival.appendDocument(3, "location=北京|上海^gender=男");
		adRetrival.appendDocument(4, "location=北京|上海^gender=女");
		adRetrival.appendDocument(5, "location!=北京|上海");

		LOG.info("{}", adRetrival);
		
		LOG.info("queries on adindex: ");
		LOG.info("-------------------");
		String[] queries = { "location=北京^gender=男", "location=上海^gender=女^age=30", "location=深圳" };
		List<Set<Long>> results = new ArrayList<>(); 
		for (String query : queries) {
			Set<Long> docIds = adRetrival.retrieveDocuments(query);
			results.add(docIds);
			LOG.info("query: {}  result docIds: {}", query, docIds);
		}
		
		Assert.assertEquals("[1, 3]", results.get(0).toString());
		Assert.assertEquals("[2, 4]", results.get(1).toString());
		Assert.assertEquals("[5]", results.get(2).toString());
	}

}
