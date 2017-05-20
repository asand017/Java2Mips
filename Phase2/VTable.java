/*
 *
 *
 *
 */

import java.util.*;

class VTable{
	
	public static String vtable_class = ""; //class that the vtable belongs to

	public static ArrayList<String> vtable = new ArrayList<String>(); //vtable

	public void setClass(String class_name){
		vtable_class = class_name;
	}

	public void add(String method){
		vtable.add(method);
	}	

	/*public void printClassVTable(String class_name){
		for(int i = 0; i < vtable.size(); i++){
			//if(vtable.get(i)
		}
	}*/

	public String getClassName(){
		return vtable_class;
	}
		
	public void printVtable(){
		System.out.println(vtable);
	}
}
