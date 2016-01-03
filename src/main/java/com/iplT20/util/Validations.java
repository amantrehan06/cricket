package com.iplT20.util;

public class Validations {
	
	public static boolean isNull(String value){
		return value==null? true : false;
	}
	
	public static boolean isBlank(String value){
		return value.equals("") ? true : false;
	}
}
