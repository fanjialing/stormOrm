package org.storm.orm.core;

/**
 *  mysql 数据库 和 java 数据类型转换器
 * @author Administrator
 *
 */
public class MysqlTypeConvertor implements TypeConvertor {

	public String databaseType2JavaType(String columnType) {
		
		if("varchar".equalsIgnoreCase(columnType)
			||"char".equalsIgnoreCase(columnType)){
			return "String";
		}
		else if("int".equalsIgnoreCase(columnType)
				||"tinyint".equalsIgnoreCase(columnType)
				||"smallint".equalsIgnoreCase(columnType)
				||"integer".equalsIgnoreCase(columnType)
				){
			return "Integer";
		}else if("bigint".equalsIgnoreCase(columnType)){
			return "Long";
		}else if("double".equalsIgnoreCase(columnType)
				||"float".equalsIgnoreCase(columnType)
				){
			return "Double";
		}else if("clob".equalsIgnoreCase(columnType)){
			return "java.sql.Clob";
		}else if("blob".equalsIgnoreCase(columnType)){
			return "java.sql.Blob";
		}else if("time".equalsIgnoreCase(columnType)){
			return "java.sql.Time";
		}else if("date".equalsIgnoreCase(columnType)){
			return "java.sql.Date";
		}else if("timestamp".equalsIgnoreCase(columnType)){
			return "java.sql.Timestamp";
		}
		
		return null;
	}

	public String javaType2DatabaseType(String javaDataType) {
		// TODO Auto-generated method stub
		return null;
	}

}
