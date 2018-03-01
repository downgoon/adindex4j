/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */
package com.downgoon.adindex.be;

import com.downgoon.adindex.notation.NotationDef;

public enum Predicate {
	INCLUSIVE, EXCLUSIVE;

	public String toString() {
		return this == INCLUSIVE ? NotationDef.INCLUSIVE : NotationDef.EXCLUSIVE;
	}

}
