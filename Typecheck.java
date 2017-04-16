/**
 * Team Members: Aaron Sanders, Cristian Ibarra, Leon Peng
 * 
 * Typecheck.java
 *
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.FileInputStream;

public class Typecheck{

   public static void main(String args[]){
	
	try{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String s;
		while((s = in.readLine()) == ){
			//String s = in.readLine();
			System.out.println(s);
			//System.out.println("Program type checked successfully");
		}
	} catch(Exception e){
	 	e.printStackTrace();
	}
   }

}
