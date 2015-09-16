package org.storm.orm.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.storm.orm.bean.ColumnInfo;
import org.storm.orm.bean.JavaFieldGetSet;
import org.storm.orm.bean.TableInfo;
import org.storm.orm.core.DBManager;
import org.storm.orm.core.MysqlTypeConvertor;
import org.storm.orm.core.TableContext;
import org.storm.orm.core.TypeConvertor;

/**
 * 
 * @author Administrator
 *
 */
public class JavaFileUtils {
	
	/**
	 * 根据 ColumnInfo 生成java源码
	 * @param column  字段信息
	 * @param convertor 类型转换器
	 * @return java 属性和 set/get 方法源码
	 */
	public static JavaFieldGetSet createFieldGetSetSRC(ColumnInfo column,TypeConvertor convertor)
	{
		JavaFieldGetSet jfgs = new JavaFieldGetSet();
		
		
		String javaFieldType  = convertor.databaseType2JavaType(column.getDataType());		
		
		jfgs.setFieldInfo("\tprivate "+javaFieldType+" "+column.getName()+";\n");
		
		//生成get方法
		StringBuffer getSrc = new StringBuffer();
		getSrc.append("\tpublic "+javaFieldType+" get"+StringUtils.firstChar2UpperCase(column.getName()+"(){\n"));
		getSrc.append("\t\treturn "+column.getName()+";\n");
		getSrc.append("\t}\n");
		jfgs.setGetInfo(getSrc.toString());
		
		//生成set方法的源码
		StringBuffer setSrc = new StringBuffer();
		setSrc.append("\tpublic void set"+StringUtils.firstChar2UpperCase(column.getName()+"("));
		setSrc.append(javaFieldType+" "+column.getName()+"){\n");
		setSrc.append("\t\tthis."+column.getName()+"="+column.getName()+";\n");
		setSrc.append("\t}\n");
		jfgs.setSetInfo(setSrc.toString());
		
		return jfgs;
	}
	
	/**
	 * 根据表信息生成java类的源代码
	 * @param tableInfo
	 * @param convertor
	 * @return
	 */
	public static String createJavaSRC(TableInfo tableInfo,TypeConvertor convertor){
		
		Map<String,ColumnInfo> columns =  tableInfo.getColumns();
		List<JavaFieldGetSet> javaFields = new ArrayList<JavaFieldGetSet>();
		
		for(ColumnInfo c:columns.values()){
			javaFields.add(createFieldGetSetSRC(c,convertor));
		}
		
		
		StringBuilder src = new StringBuilder();
		// 生成 package;
		src.append("package "+DBManager.getConf().getPoPackage()+";\n\n");
		// 生成 import语句
		src.append("import java.sql.*;\n");
		src.append("import java.util.*;\n\n");
		// 生成类声明语句
		src.append("public class "+StringUtils.firstChar2UpperCase(tableInfo.getTname())+"{\n\n");
		// 生成属性
		for(JavaFieldGetSet f:javaFields){
			src.append(f.getFieldInfo());
		}
		
		src.append("\n\n");
		//生成 set get 方法列表
		
		for(JavaFieldGetSet f:javaFields){
			src.append(f.getGetInfo());
			src.append(f.getSetInfo());
		}
		
		for(JavaFieldGetSet f:javaFields){
		}
		
		src.append("}\n");
		return src.toString();
	}
	
	/**
	 * 生成java源码文件
	 * @param tableInfo
	 * @param convertor
	 */
	public  static void createJavaPOFile(TableInfo tableInfo,TypeConvertor convertor){
    	String src  = createJavaSRC(tableInfo,convertor);
    	
    	BufferedWriter bw = null;
    	
    	String srcPath = DBManager.getConf().getSrcPath()+"\\src\\";
    	String poPackage = DBManager.getConf().getPoPackage().replaceAll("\\.", "\\\\");
    	File file = new File(srcPath+poPackage);
    	if(!file.exists()){
    		file.mkdir();
    	}
    	
    	
    	try {
			
    	 	bw = new BufferedWriter(new FileWriter(file.getAbsolutePath()+"\\"+StringUtils.firstChar2UpperCase(tableInfo.getTname()+".java")));
        	
        	bw.write(src);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bw!=null){
				try {
					bw.flush();
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    	

	}
	
	
	public static void main(String[] args) {
		
		Map<String, TableInfo> tables =  TableContext.tables;
	    for(TableInfo t:tables.values()){
	    	createJavaPOFile(t,new MysqlTypeConvertor());
	    }
	}
	

}
