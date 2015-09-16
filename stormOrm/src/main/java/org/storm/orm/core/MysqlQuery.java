package org.storm.orm.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
import org.storm.orm.util.StringUtils;

@SuppressWarnings("all")
public class MysqlQuery extends Query{

	@Override
	public Object queryPagenate(int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	



}
