/*
 * Team Members: Aaron Sanders, Cristian Ibarra, Leon Peng
 *
 * ClassRecord.java
 *
 * Class Record class
 *
 */

import java.util.*;

class ClassRecord {		

	//public static Map class_ref = new HashMap(); // (class_name, ArrayList<String> vtable)
	//public static ArrayList<String> vtable = new ArrayList<String>(); // // (method1, method2,...)
	public static ArrayList<Map> classRecord = new ArrayList<Map>(); //use ArrayList index for i in
	// (class_ref1, class_ref2,....)

	public void add(Map class_reference){//pass in a class_ref to be put into the Class Record
		classRecord.add(class_reference);
	}
	
	public void printRecord(){
		System.out.println(classRecord);
	}
	 
}
