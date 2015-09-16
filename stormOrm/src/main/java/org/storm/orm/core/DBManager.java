package org.storm.orm.core;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.storm.orm.bean.Configuration;

/**
 * 数据库管理
 * @author Administrator
 *
 */
public class DBManager {
	
	private static Configuration conf;
	/**
	 * 类初始化时执行一次  静态代码块
	 */
	static{
		
		Properties pros = new Properties();
		
		try {
			pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config/db.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		conf = new Configuration();
		conf.setDriverClass(pros.getProperty("driverClass"));
		conf.setPoPackage(pros.getProperty("poPackage"));
		conf.setUrl(pros.getProperty("url"));
		conf.setUsername(pros.getProperty("username"));
		conf.setPassword(pros.getProperty("password"));
		conf.setUsingDB(pros.getProperty("usingDB"));
		conf.setSrcPath(pros.getProperty("srcPath"));
	}
	
	
	public static  Connection getConnection(){
		   Connection conn = null;
		   try {
			Class.forName(conf.getDriverClass());
			conn = DriverManager.getConnection(conf.getUrl(),conf.getUsername(),conf.getPassword());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		   return conn;
	}
	
	/**
	 * 反悔Configuration对象
	 * @return
	 */
	public static Configuration getConf(){
		return conf;
	}
	
	
	/**
	 * 关闭连接
	 * @param conn
	 * @param stmt
	 * @param rs
	 */
	public static void close(Connection conn,PreparedStatement ps){
		try {
		if(ps != null){
			ps.close();
		}
		if(conn != null){
			conn.close();
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 关闭连接
	 * @param conn
	 * @param stmt
	 * @param rs
	 */
	public static void close(Connection conn,PreparedStatement ps,ResultSet rs){
		try {
		if(ps != null){
			ps.close();
		}
		if(rs != null){
			rs.close();
		}
		if(conn != null){
			conn.close();
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
