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
	//public static ArrayList<String> type_scope = new ArrayList<String>(); // (type, scope)
	
	public static Table table = new Table();

	public static String Type = "";

	public static String ident = "";

	public int scope = 0;

	public void visit(MainClass n){
		
		ArrayList<String> type_scope = new ArrayList<String>();
		Type = n.f0.toString();
		type_scope.add(Type);
		type_scope.add(Integer.toString(scope));
	
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);

		table.add(ident, type_scope);
		
		scope = scope + 1;

		n.f3.accept(this);
		n.f4.accept(this);
		n.f5.accept(this); //void
		n.f6.accept(this); //main
   
		ArrayList<String> type_scope1 = new ArrayList<String>();
		Type = n.f5.toString();
		type_scope1.add(Type);
		type_scope1.add(Integer.toString(scope));
		table.add(n.f6.toString(), type_scope1);

		n.f7.accept(this);
		n.f8.accept(this);
		n.f9.accept(this);
		n.f10.accept(this);
		
		Type = "String[]";
		
		n.f11.accept(this);
		n.f12.accept(this);
		n.f13.accept(this);

		scope = scope + 1;
		ArrayList<String> type_scope2 = new ArrayList<String>();
		type_scope2.add(Type);
		type_scope2.add(Integer.toString(scope));
		table.add(ident, type_scope2);

		n.f14.accept(this);
		n.f15.accept(this);		
		n.f16.accept(this);
	
		scope = scope - 1;
		
		n.f17.accept(this);

		scope = scope - 1;
	
	}
	
	public void visit(Identifier n){
		if(Type == "class"){
			ident = n.f0.toString(); //store identifier name in ident
		}else if(Type == "String[]"){
			ident = n.f0.toString();
		}
	}

	public void start(BufferedReader in){
		try{

			MiniJavaParser parser = new MiniJavaParser(in);

	    	Goal root = parser.Goal();
			PopSymbolTable n = new PopSymbolTable();
			n.visit(root);

			table.printall();

		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
