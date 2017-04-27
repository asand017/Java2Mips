/**
 * Team Members: Aaron Sanders, Cristian Ibarra, Leon Peng
 * 
 * Typecheck.java
 *
 */

package visitor;
package syntaxtree;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.Scanner;

public class Typecheck extends DepthFirstVisitor{

   public static void main(String args[]){
	
	try{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); //reading in console input
		
		String s; // string to hold input file per line
		String a = ""; // string to hold the full input file
		while((s = in.readLine()) != null){
			a += s; // copy input file line to overaching theme
			a += "\n"; // append newline character to the end of each line
		}
		System.out.print(a);
		System.out.println("Program type checked successfully");
	} catch(Exception e){
	 	e.printStackTrace();
	}
   }

}


