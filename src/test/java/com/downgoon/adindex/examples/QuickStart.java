/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */

package com.downgoon.adindex.examples;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.downgoon.adindex.AdRetrival;
import com.downgoon.adindex.BEScaner;
import com.downgoon.adindex.be.Assignment;
import com.downgoon.adindex.be.Conjunction;
import com.downgoon.adindex.be.DNF;
import com.downgoon.adindex.be.Document;
import com.downgoon.adindex.be.Predicate;

public class QuickStart {

	private static final Logger LOG = LoggerFactory.getLogger(QuickStart.class);

	public static void main(String[] args) {

		// 5 ad documents targeting several attributes
		AdRetrival adRetrival = new BEScaner();

		// notation form targeting: 1, "location=北京^gender=男"
		adRetrival.appendDocument(1, "location=北京^gender=男");

		// java-object form targeting: 2, "location=上海^gender=女"
		adRetrival.appendDocument(new Document(2,
				new DNF( //
						new Conjunction( //
								new Assignment("location", Predicate.INCLUSIVE, "上海"), //
								new Assignment("gender", Predicate.INCLUSIVE, "女") //
						) //
				)));

		// a DNF with multiple conjunctions
		adRetrival.appendDocument(3, "(location=北京|上海^gender=男) | (location=深圳^gender=女)");
		adRetrival.appendDocument(4, "location!=北京|上海");

		// query
		String query = "location=北京^gender=男";

		// results: [1, 3]
		Set<Long> docIds = adRetrival.retrieveDocuments(query);
		LOG.info("query: {}, results: {}", query, docIds);
	}

}
