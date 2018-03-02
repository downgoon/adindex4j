/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */
package com.downgoon.adindex4j;

import java.util.Set;
import java.util.stream.Stream;

import com.downgoon.adindex4j.be.Document;
import com.downgoon.adindex4j.be.Query;

public class BEIndex implements AdRetrival {

	@Override
	public void appendDocument(Document document) {
		// TODO Auto-generated method stub
	}

	@Override
	public Set<Long> retrieveDocuments(Query query, int topN) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stream<Document> documents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Document getDocument(long documentId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
