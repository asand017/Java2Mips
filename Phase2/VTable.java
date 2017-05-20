/*
 *
 *
 *
 */

import java.util.*;

class VTable{
	public static ArrayList<String> vtable = new ArrayList<String>(); //vtable

	public void add(String method){
		vtable.add(method);
	}
		
	public void printVtable(){
		System.out.println(vtable);
	}
}
