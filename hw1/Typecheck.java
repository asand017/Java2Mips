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
import java.util.Scanner;
import visitor.*;
import syntaxtree.*;


public class Typecheck extends DepthFirstVisitor{

   public void visit(Goal n) {
	//System.out.print("made it \n");
	n.f0.accept(this);
	n.f1.accept(this);
	n.f2.accept(this);
   }

   //public void visit(Identifier n) {
	//System.out.print(n.f0.toString());
	//System.out.print("\n");
   //}

   public static void main(String args[]){
	
	try{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); //reading in console input

		MiniJavaParser parser = new MiniJavaParser(in);	
		
		Goal root = parser.Goal();
		Typecheck n = new Typecheck();
		n.visit(root);
		
		
		System.out.print(parser.getToken(1));		

	 	//parser.ReInit(in);	
		//MainClass mc = parser.MainClass(); 

		
		System.out.println("Program type checked successfully");
	} catch(Exception e){
	 	e.printStackTrace();
	}
   }

}


