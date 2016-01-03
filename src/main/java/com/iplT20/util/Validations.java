package com.iplT20.util;

public class Validations {
	
	public static boolean isNull(String value){
		return value==null? true : false;
	}
	
	public static boolean isBlank(String value){
		return value.equals("") ? true : false;
	}
	
	public static boolean isNullorEmpty(String value){
		return 	value==null || "".equals(value);
	}
	
	public static boolean isNullorEmpty(String... values){
		for(String str:values){
			if(str==null || "".equals(str)){
				return true;
			}
		}
		return false;
	}
	
	
}
