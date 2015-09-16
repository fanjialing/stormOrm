package org.storm.orm.util;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;

/**
 * 反射工具类
 * @author Administrator
 *
 */
@SuppressWarnings("all")
public class ReflectUtils {

	
	/**
	 *  调用OBJ对象的GET方法
	 * @param c
	 * @param fieldName
	 * @param obj
	 * @return
	 */
	public static Object invokeGet(String fieldName,Object obj){
		//通过反射几只调用属性的get方法或set方法
		try {
			Class c = obj.getClass();
			Method m = c.getDeclaredMethod("get"+StringUtils.firstChar2UpperCase(fieldName),null);
			return m.invoke(obj, null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 *  调用OBJ的Set方法
	 * @param columnName 列名
	 * @param obj 对象
	 * @param columnValue  列值
	 */
	public static void invokeSet(String columnName,Object obj,Object columnValue){
		//通过反射几只调用属性的get方法或set方法
		try {
			Class c = obj.getClass();
			if(columnValue!=null){
				Method m = c.getDeclaredMethod("set"+StringUtils.firstChar2UpperCase(columnName),columnValue.getClass());
				m.invoke(obj, columnValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 指定包名,返回当前包下的类
	 * @param pckgname
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Class[] getClasses(String pckgname) 
			   throws ClassNotFoundException { 
			  ArrayList<Class> classes = new ArrayList<Class>(); 
			  // Get a File object for the package 
			  File directory = null; 
			  try { 
			   ClassLoader cld = Thread.currentThread().getContextClassLoader(); 
			   if (cld == null) 
			    throw new ClassNotFoundException("Can't get class loader."); 
			   String path =  pckgname.replace('.', '/'); 
			   System.out.println(path);
			   URL resource = cld.getResource(path); 
			   if (resource == null) 
			    throw new ClassNotFoundException("No resource for " + path); 
			   directory = new File(resource.getFile()); 
			  } catch (NullPointerException x) { 
			   throw new ClassNotFoundException(pckgname + " (" + directory 
			     + ") does not appear to be a valid package a"); 
			  } 
			  if (directory.exists()) { 
			   // Get the list of the files contained in the package 
			   String[] files = directory.list(); 
			   for (int i = 0; i < files.length; i++) { 
			    // we are only interested in .class files 
			    if (files[i].endsWith(".class")) { 
			     // removes the .class extension 
			     classes.add(Class.forName(pckgname + '.' 
			       + files[i].substring(0, files[i].length() - 6))); 
			  } 
			   } 
			  } else 
			   throw new ClassNotFoundException(pckgname 
			     + " does not appear to be a valid package b"); 
			  Class[] classesA = new Class[classes.size()]; 
			  classes.toArray(classesA); 
			  return classesA; 
	}
	
}
