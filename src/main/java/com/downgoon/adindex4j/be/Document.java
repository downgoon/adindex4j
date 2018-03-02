/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */
package com.downgoon.adindex4j.be;

public class Document {

	private long docId;

	private DNF targeting;

	public Document(long docId, DNF targeting) {
		super();
		this.docId = docId;
		this.targeting = targeting;
	}

	public boolean match(Query query) {
		return targeting.match(query);
	}

	public long getDocId() {
		return docId;
	}

	public void setDocId(long docId) {
		this.docId = docId;
	}

	public DNF getTargeting() {
		return targeting;
	}

	public void setTargeting(DNF targeting) {
		this.targeting = targeting;
	}

}
