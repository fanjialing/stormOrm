package org.storm.orm.core;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.storm.orm.bean.ColumnInfo;
import org.storm.orm.bean.JavaFieldGetSet;
import org.storm.orm.bean.TableInfo;
import org.storm.orm.util.JavaFileUtils;
import org.storm.orm.util.StringUtils;

/**
 * 负责管理数据库所有表结构和累结构
 * @author Administrator
 *
 */
public class TableContext {

	/**
	 * 表名为 key   表信息对象为value
	 */
	public static Map<String,TableInfo> tables = new HashMap<String,TableInfo>();
	/**
	 * 将po的 class 对象和表信息对象关联起来 ,便于重用
	 */
	public static Map<Class,TableInfo> poClassTableMap = new HashMap<Class, TableInfo>();
	
	public TableContext(){
		
	}
	
	static{
		try {
			Connection connection  = DBManager.getConnection();
			
			DatabaseMetaData dbmd =  connection.getMetaData();
			
			ResultSet tableRet = dbmd.getTables(null, "%", "%", new String[]{"TABLE"});
			
			while(tableRet.next()){
				String tableName = (String) tableRet.getObject("TABLE_NAME");
				TableInfo ti = new TableInfo(tableName,new HashMap<String, ColumnInfo>(),new ArrayList<ColumnInfo>());
				tables.put(tableName, ti);
				
				ResultSet set = dbmd.getColumns(null, "%", tableName, "%");
				while(set.next()){
					ColumnInfo ci = new ColumnInfo();
					ci.setName(set.getString("COLUMN_NAME"));
					ci.setDataType(set.getString("TYPE_NAME"));
					ci.setKeyType(0);
					ti.getColumns().put(ci.getName(), ci);
				}
				
				ResultSet set2 = dbmd.getPrimaryKeys(null, "%", tableName);
				while(set2.next()){
					ColumnInfo ci = ti.getColumns().get(set2.getObject("COLUMN_NAME"));
					ci.setKeyType(1);
					ti.getPriKeys().add(ci);
				}
				// 取唯一逐渐  ,如果是联合主键则为空
				if(ti.getPriKeys().size()>0){
					ti.setOnlyPriKey(ti.getPriKeys().get(0));
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 updateJavaPOFile();
		 
		 
		 loadPOTables();
		
	}
	
	
	/**
	 *  根据表结构,更新配置的PO包下面的java类
	 */
	public static void updateJavaPOFile(){
		Map<String, TableInfo> tables =  TableContext.tables;
	    for(TableInfo t:tables.values()){
	    	JavaFileUtils.createJavaPOFile(t,new MysqlTypeConvertor());
	    }
	}
	
	/**
	 * 加载PO包下的类
	 */
	public static void loadPOTables(){
		
		for(TableInfo tableInfo:tables.values()){
		
			try {
				Class c = Class.forName(DBManager.getConf().getPoPackage()+"."+StringUtils.firstChar2UpperCase(tableInfo.getTname()));
				poClassTableMap.put(c, tableInfo);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
	}
	

	public static Map<String, TableInfo> getTables() {
		return tables;
	}

	public static void setTables(Map<String, TableInfo> tables) {
		TableContext.tables = tables;
	}

	public static Map<Class, TableInfo> getPoClassTableMap() {
		return poClassTableMap;
	}

	public static void setPoClassTableMap(Map<Class, TableInfo> poClassTableMap) {
		TableContext.poClassTableMap = poClassTableMap;
	}
	
	
	
	public static void main(String[] args) {
		Map<String, TableInfo> tables =  TableContext.tables;
	}
	
}
