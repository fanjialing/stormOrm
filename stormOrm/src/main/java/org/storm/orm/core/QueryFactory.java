package org.storm.orm.core;


/**
 * 创建Query对象的工厂类
 * @author Administrator
 *
 */
public class QueryFactory {
	
	
	
	private static MysqlQuery mysqlQuery;

	/**
	 *  设置单例模式的 变量
	 */
	private static QueryFactory uniqueInstance = null;  
	
	
	private QueryFactory(){
		
	}
	
	public static QueryFactory getQueryFactory(){
		   if (uniqueInstance == null) {  
	            uniqueInstance = new QueryFactory();  
	        }  
	        return uniqueInstance;  
	}
	
	public Query createQuery(){
		return new MysqlQuery();
	}
	
	


}
