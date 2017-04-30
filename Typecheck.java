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

   public static boolean ident = false;

   public static String Type = "";

   public static Table table = new Table();

   public static int scope = 0;

   public void visit(MainClass n) {

	Type = n.f0.toString();

	n.f0.accept(this);
	n.f1.accept(this);
	n.f2.accept(this);
		
	scope = scope + 1; // entering new scope
	
	n.f3.accept(this);
	n.f4.accept(this);
	n.f5.accept(this);
	n.f6.accept(this);
	n.f7.accept(this);
	n.f8.accept(this);
	n.f9.accept(this);
	n.f10.accept(this);
	
	Type = n.f8.toString();
	
	n.f11.accept(this);
	n.f12.accept(this);
	n.f13.accept(this);
	n.f14.accept(this);
	n.f15.accept(this);
	n.f16.accept(this);
	n.f17.accept(this);

   }

   public void visit(Identifier n) { //make sure to check symbol table for duplicate names
	if(Type == "class"){
	    table.add(n.f0.toString(), Type);
	    Type = "";
	}else if(Type == "String"){ //come back to implement cases of pointer types, ie. String[]
	    table.add(n.f0.toString(), "String[]");
	    Type = "";
	}else if(Type == "int[]"){
	    table.add(n.f0.toString(), Type);
	    Type = "";
	}else if(Type == "boolean"){
	    table.add(n.f0.toString(), Type);
	    Type = "";
	}else if(Type == "int"){
	    table.add(n.f0.toString(), Type);
	    Type = "";
	}else if(Type == ""){ //POTENTIAL PROBLEM AREA
	    Type = n.f0.toString();
	}else if(Type != ""){
	    table.add(n.f0.toString(), Type);
	    Type = "";
	}
   }

   public void visit(VarDeclaration n) {
	n.f0.accept(this);
	n.f1.accept(this);
	n.f2.accept(this);
   }

   public void visit(Type n) {
	
	if(!(n.f0 instanceof Identifier)){
		Type = n.f0.toString();
	}

	n.f0.accept(this);
   }

   public void visit(ArrayType n){
	n.f0.accept(this);

	Type = "int[]";

	n.f1.accept(this);
	n.f2.accept(this);
   }

   public void visit(BooleanType n){

	Type = n.f0.toString();

	n.f0.accept(this);
   }

   public void visit(IntegerType n){
	
	Type = n.f0.toString();
	
	n.f0.accept(this);
   }

   public static void main(String args[]){
	
	try{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); //reading in console input

		MiniJavaParser parser = new MiniJavaParser(in);	
		
		Goal root = parser.Goal();
		Typecheck n = new Typecheck();
		n.visit(root);
		
		table.printall();		

		//System.out.println("Program type checked successfully");
	} catch(Exception e){
	 	e.printStackTrace();
	}
   }

}


