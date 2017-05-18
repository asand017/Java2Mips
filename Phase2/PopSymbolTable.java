/*
 *  Team Members: Aaron Sanders, Crisitian Ibarra, Leon Peng
 *
 *  PopSymbolTable.java
 *
 *  Populates symbol table
 *
 *
 */

import java.util.*;
import java.io.BufferedReader;
import visitor.*;
import syntaxtree.*;

class PopSymbolTable extends DepthFirstVisitor{
	public static ArrayList<String> sym_scope = new ArrayList();
	
	public static Table table = new Table();

	public static String Type = "";

	public int scope = 0;

	public void visit(MainClass n){

		Type = n.f0.toString();
		
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		
		scope = scope + 1;

		n.f3.accept(this);
		n.f4.accept(this);
		n.f5.accept(this);
		n.f6.accept(this);

		table.add(n.f5.toString(), n.f6.toString());

		n.f7.accept(this);
		n.f8.accept(this);
		n.f9.accept(this);
		n.f10.accept(this);
		
		Type = "String[]";
		
		n.f11.accept(this);
		n.f12.accept(this);
		n.f13.accept(this);

		scope = scope + 1;

		n.f14.accept(this);
		n.f15.accept(this);		
		n.f16.accept(this);
	
		scope = scope - 1;
		
		n.f17.accept(this);

		scope = scope - 1;
	
		System.out.println("cake");

	}

	public void start(BufferedReader in){
		try{

			MiniJavaParser parser = new MiniJavaParser(in);

	    	Goal root = parser.Goal();

		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
