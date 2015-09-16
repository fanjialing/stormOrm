package org.storm.orm.bean;

import java.util.List;
import java.util.Map;

/**
 * 存储表结构的信息
 * @author Administrator
 *
 */
public class TableInfo {
	/**
	 * 表名
	 */
	private String tname;
	/**
	 * 所有字段的信息
	 */
	private Map<String,ColumnInfo> columns;
	
	private ColumnInfo onlyPriKey;

	
	/**
	 * 唯一主键
	 */
	
	private List<ColumnInfo> priKeys;
	
	
	public TableInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	



	public TableInfo(String tname, Map<String, ColumnInfo> columns,
			 List<ColumnInfo> priKeys) {
		super();
		this.tname = tname;
		this.columns = columns;
		this.priKeys = priKeys;
	}





	public List<ColumnInfo> getPriKeys() {
		return priKeys;
	}





	public void setPriKeys(List<ColumnInfo> priKeys) {
		this.priKeys = priKeys;
	}





	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public Map<String, ColumnInfo> getColumns() {
		return columns;
	}
	public void setColumns(Map<String, ColumnInfo> columns) {
		this.columns = columns;
	}





	public ColumnInfo getOnlyPriKey() {
		return onlyPriKey;
	}





	public void setOnlyPriKey(ColumnInfo onlyPriKey) {
		this.onlyPriKey = onlyPriKey;
	}
	
	
}
