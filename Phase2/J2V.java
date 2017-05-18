/*
 * Team members: Aaron Sanders, Cristian Ibarra, Leon Peng
 *
 * J2V.java
 * Java to Vapor main file
 *
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import syntaxtree.*;
import visitor.*;

public class J2V{

	//do all the fun stuff here
	
	public static String data_label = "";
	
	public static String Main = "";

	public static class Method {
		public String[] mthd;
		public int size = 0;
		public int index = 0;

		public void input(String block) {
			mthd[index] = block;
			size++;
			index++;
		}
		
		public void print_method() {
			for(int i = 0; i < size; i++){
				System.out.println(mthd[i]); //print or output to file
				//newline between both methods
			}
		}
	}

	public static void main(String args[]) {
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			//reading in from console input

			MiniJavaParser parser = new MiniJavaParser(in);

			Goal root = parser.Goal();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
