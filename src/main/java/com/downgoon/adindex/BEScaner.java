/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */
package com.downgoon.adindex;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.downgoon.adindex.be.Document;
import com.downgoon.adindex.be.Query;

public class BEScaner implements AdRetrival {

	private Map<Long, Document> docIndex = new LinkedHashMap<>();

	@Override
	public void appendDocument(Document document) {
		Document docFound = docIndex.get(document.getDocId());
		if (docFound != null) {
			throw new IllegalArgumentException("duplicated document id: " + document.getDocId());
		}
		docIndex.put(document.getDocId(), document);
	}

	@Override
	public Set<Long> retrieveDocuments(Query query, int topN) {
		if (topN <= 0) {
			topN = SysCnf.DEFAULT_TOP_N;
		}
		return documents() // scan documents
				.filter(document -> document.match(query)) // where doc = query
				.map(document -> document.getDocId()) // select docId
				.limit(topN) // top N
				.collect(Collectors.toSet()); // select into set
	}

	@Override
	public Stream<Document> documents() {
		return docIndex.values().stream();
	}

	@Override
	public Document getDocument(long documentId) {
		return docIndex.get(documentId);
	}

}
