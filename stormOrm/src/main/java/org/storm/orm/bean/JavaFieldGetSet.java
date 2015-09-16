package org.storm.orm.bean;

/**
 * javsa 属性 和 get  set 方法的源代码
 * @author Administrator
 *
 */
public class JavaFieldGetSet {

	/**
	 * 属性的源码信息   如: private int userid;
	 */
	private String fieldInfo;
	
	/**
	 *  get 方法源码信息
	 */
	private String getInfo;
	
	/**
	 * set 方法源码信息
	 */
	private String setInfo;
	
	
	

	public JavaFieldGetSet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JavaFieldGetSet(String fieldInfo, String getInfo, String setInfo) {
		super();
		this.fieldInfo = fieldInfo;
		this.getInfo = getInfo;
		this.setInfo = setInfo;
	}

	public String getFieldInfo() {
		return fieldInfo;
	}

	public void setFieldInfo(String fieldInfo) {
		this.fieldInfo = fieldInfo;
	}

	public String getGetInfo() {
		return getInfo;
	}

	public void setGetInfo(String getInfo) {
		this.getInfo = getInfo;
	}

	public String getSetInfo() {
		return setInfo;
	}

	public void setSetInfo(String setInfo) {
		this.setInfo = setInfo;
	}

	@Override
	public String toString() {
		return "JavaFieldGetSet [fieldInfo=" + fieldInfo + ", getInfo="
				+ getInfo + ", setInfo=" + setInfo + "]";
	}
	
	
	
	
}
