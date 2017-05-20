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

	public static ArrayList<String> classRecord = new ArrayList<String>(); //use ArrayList index for i in
	// (class_name1, class_name2,....)

	public void add(String class_name){//pass in a class_ref to be put into the Class Record
		classRecord.add(class_name);
	}
	
	public void printRecord(){
		System.out.println(classRecord);
	}

	public int recordSize(){
		return classRecord.size();
	}
	 
}
