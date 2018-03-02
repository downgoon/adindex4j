/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */
package com.downgoon.adindex4j;

import java.util.Set;
import java.util.stream.Stream;

import com.downgoon.adindex4j.be.DNF;
import com.downgoon.adindex4j.be.Document;
import com.downgoon.adindex4j.be.Query;
import com.downgoon.adindex4j.notation.DNFNotation;
import com.downgoon.adindex4j.notation.QueryNotation;

public interface AdRetrival {

	public void appendDocument(Document document);

	default void appendDocument(long documentId, String targetNotation) {
		DNF targeting = new DNFNotation().denotate(targetNotation);
		appendDocument(new Document(documentId, targeting));
	}

	public Set<Long> retrieveDocuments(Query query, int topN);

	default Set<Long> retrieveDocuments(Query query) {
		return retrieveDocuments(query, -1);
	}

	default Set<Long> retrieveDocuments(String queryNotation) {
		Query query = new QueryNotation().denotate(queryNotation);
		return retrieveDocuments(query);
	}

	default Set<Long> retrieveDocuments(String queryNotation, int topN) {
		Query query = new QueryNotation().denotate(queryNotation);
		return retrieveDocuments(query, topN);
	}

	public Stream<Document> documents();

	public Document getDocument(long documentId);

}
