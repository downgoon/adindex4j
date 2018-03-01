/** 
 * @author downgoon@qq.com 
 * @since 2018年3月1日
 */
package com.downgoon.adindex.notation;

public interface Notational<T> {

	public String ennotate(T beObject);

	public T denotate(String notation);

}
