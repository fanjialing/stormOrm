package org.storm.orm.core;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.storm.orm.bean.ColumnInfo;
import org.storm.orm.bean.TableInfo;
import org.storm.orm.util.JDBCUtils;
import org.storm.orm.util.ReflectUtils;

/**
 * 负责查询 
 * @author Administrator
 *
 */
@SuppressWarnings("all")
public abstract class Query {
	/**
	 * 采用模版方法模式将JDBC操作封装成模版,便于重用
	 * @param sql
	 * @param params
	 * @param clazz
	 * @param back
	 * @return
	 */
	public Object executeQueryTemplate(String sql,Object[] params,Class clazz,CallBack back){
		Connection conn = DBManager.getConnection();
		List list = null; // 存放查询结果的容器
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			System.out.println(ps);

			JDBCUtils.handeParams(ps, params);
			System.out.println(ps);
			rs = ps.executeQuery();
			return  back.doExecute(conn, ps, rs);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.close(conn, ps,rs);
		}
		
		return null;
	}

	public int executeDML(String sql, Object[] params) {
		
		
		Connection conn = DBManager.getConnection();
		int count = 0;
		
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(sql);
			System.out.println(ps);

			JDBCUtils.handeParams(ps, params);
			count = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.close(conn, ps);
		}
		
		
		return count;
	}

	public void insert(Object obj) {
		Class c = obj.getClass();
		List<Object> params = new ArrayList<Object>();  //存储参数对象
		
		TableInfo tableInfo = TableContext.poClassTableMap.get(c);

		Field[] fs =  c.getDeclaredFields();
	
		StringBuffer sql = new StringBuffer("insert into "+tableInfo.getTname()+" (");
		
		int countNotNUllField = 0;  //计算不为null的值
		
		for(Field f:fs){
			String fieldName = f.getName();
			Object fieldValue = ReflectUtils.invokeGet(fieldName, obj);
			
			if(fieldValue!=null){
				countNotNUllField++;
				sql.append(fieldName+",");
				params.add(fieldValue);
			}
			
		}
		
		sql.setCharAt(sql.length() - 1, ')');
		sql.append(" values (");
		for(int i=0;i<countNotNUllField;i++){
			sql.append("?,");
		}
		
		sql.setCharAt(sql.length() - 1, ')');

		executeDML(sql.toString(), params.toArray());
	}

	public int delete(Class clazz, Object id) {
		
		TableInfo tableInfo = TableContext.poClassTableMap.get(clazz);
		//获得主键
		ColumnInfo onlyPriKey =  tableInfo.getOnlyPriKey();
		
		String sql = "delete from "+tableInfo.getTname()+" where "+onlyPriKey.getName()+"=? ";
		
		executeDML(sql,new Object[]{id});
		return 0;
	}

	public int delete(Object obj) {
		Class c = obj.getClass();
		
		TableInfo tableInfo = TableContext.poClassTableMap.get(c);
		
		ColumnInfo onlyPriKey =  tableInfo.getOnlyPriKey();
		
		Object priKeyValue = ReflectUtils.invokeGet(onlyPriKey.getName(), obj);
		
		delete(c,priKeyValue);
		return 0;
	}

	public int update(Object obj, String[] fieldNames) {
		Class c = obj.getClass();
		List<Object> params = new ArrayList<Object>();  //存储参数对象
		
		TableInfo tableInfo = TableContext.poClassTableMap.get(c);
		
		ColumnInfo priKey = tableInfo.getOnlyPriKey();
		
		StringBuffer sql = new StringBuffer(" update "+tableInfo.getTname()+" set ");
		
		for(String fname:fieldNames){
			System.out.println(fname);

			Object fvalue = ReflectUtils.invokeGet(fname, obj);
			params.add(fvalue);
			
			sql.append(fname+"=?,");
		}
		
		sql.setCharAt(sql.length()-1, ' ');
		
		sql.append(" where ");
		
		sql.append(priKey.getName()+"=? ");
		
		params.add(ReflectUtils.invokeGet(priKey.getName(), obj));
		
		return executeDML(sql.toString(), params.toArray());
	}

	public List queryRows(final String sql,final Class clazz,final Object[] params) {
		
		
		return (List)executeQueryTemplate(sql, params, clazz, new CallBack() {
			
			public Object doExecute(Connection conn, PreparedStatement ps, ResultSet rs) {
					
				 List list = null;

				 try {
						ResultSetMetaData  metaData = rs.getMetaData();
						//多行
						while(rs.next()){
							if(list == null){
								list = new ArrayList();
							}
							//调用javabean的无参构造器
							Object rowObj = clazz.newInstance();
							//多列
						for(int i=0;i<metaData.getColumnCount();i++){
								
							
								String columnName = metaData.getColumnLabel(i+1);
								
								Object columnValue = rs.getObject(i+1);
								
								ReflectUtils.invokeSet(columnName, rowObj, columnValue);
							
						}
						
							list.add(rowObj);
						}	
					} catch (Exception e) {
							e.printStackTrace();

					}				
					return list;
		}
		});
		
	}

	public Object queryUniqueRow(String sql, Class clazz, Object[] params) {
		List list = queryRows(sql, clazz, params);
		return (list == null && list.size()>0)?null:list.get(0);
	}

	public Object queryValue(String sql, Object[] params) {

		return executeQueryTemplate(sql, params, null, new CallBack() {
			
			public Object doExecute(Connection conn, PreparedStatement ps, ResultSet rs) {
			
				Object value = null;
				
				try {
					while(rs.next()){
						value = rs.getObject(1);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return value;
			}
		});
		
		
	}

	public Number queryNumber(String sql, Object[] params) {

		return (Number)queryValue(sql, params);
	}
	
	public abstract Object queryPagenate(int pageNum, int pageSize);
	
}
