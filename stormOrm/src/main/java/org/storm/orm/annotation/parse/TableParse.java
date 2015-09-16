package org.storm.orm.annotation.parse;

import java.lang.annotation.Annotation;

public class TableParse {

	
	public void Parse(Class[] clazzs){
		
		for(Class clazz:clazzs){
			Annotation[] annotations= clazz.getDeclaredAnnotations();
				for(Annotation a:annotations){
				
					System.out.println(a.toString());
				}
		}


	}
	
	
}
