/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */
package com.downgoon.adindex4j.notation;

public interface NotationDef {

	public static final String OR = "|";
	
	public static final String AND = "^";
	
	/** inclusive predicate notation */
	public static final String INCLUSIVE = "=";
	
	/** exclusive predicate notation */
	public static final String EXCLUSIVE = "!=";
	
	public static final String CONJUNCTION_BEGIN = "(";
	
	public static final String CONJUNCTION_ENDED = ")";
	
}
