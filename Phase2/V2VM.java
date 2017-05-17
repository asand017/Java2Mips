/*
 * Team Members: Aaron Sanders, Cristian Ibarra, Leon Peng
 *
 * J2V.java
 * Java to Vapor main file
 *
 *
 */

import java.io.BufferedReader;
import syntaxtree.*;
import visitor.*;

public class J2V extends DepthFirstVisitor{

	//do all the fun stuff here
	
	public static String data_label = "";
	
	public static String Main = "";

	public class Method {
		private String[] mthd;
		public int size = 0;
		public int index = 0;

		public static void input(String block) {
			mthd[index] = block;
			size++;
			index++;
		}

		public static void print_method() {
			for(int i = 0; i < size; i++) {
				method[i]; //print or otuput to file
				//new line between both methods
			}
		}
	}

	public static void main(String args[]) {
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			//reading in from console input 

			MiniJavaParser parser = new MiniJavaParser(in);

			Goal root = parser.Goal();
		}
	}
}
