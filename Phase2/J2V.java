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
	
	public static void main(String args[]) {
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			//reading in from console input 

			MiniJavaParser parser = new MiniJavaParser(in);

			Goal root = parser.Goal();
		}
	}
}
