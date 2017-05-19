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

    public void addToSymT(String name, String type, String Scope){
	    ArrayList<String> type_scope = new ArrayList<String>();
		type_scope.add(type);
		type_scope.add(Scope);
		table.add(name, type_scope);
	}

	public void visit(MainClass n){
		
		Type = n.f0.toString();
	
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);

		addToSymT(ident, Type, Integer.toString(scope));

		scope = scope + 1;

		n.f3.accept(this);
		n.f4.accept(this);
		n.f5.accept(this); //void
		n.f6.accept(this); //main
   
		Type = n.f5.toString();

		addToSymT(n.f6.toString(), Type, Integer.toString(scope));

		n.f7.accept(this);
		n.f8.accept(this);
		n.f9.accept(this);
		n.f10.accept(this);
		
		Type = "String[]";
		
		n.f11.accept(this);
		n.f12.accept(this);
		n.f13.accept(this);

		scope = scope + 1;

		addToSymT(ident, Type, Integer.toString(scope));

		n.f14.accept(this);
		n.f15.accept(this);		
		n.f16.accept(this);
	
		scope = scope - 1;
		
		n.f17.accept(this);

		scope = scope - 1;
	
	}
	
	public void visit(Identifier n){

		n.f0.accept(this);

		//ident = n.f0.toString();

		if(Type == "class"){
			ident = n.f0.toString(); //store identifier name in ident
		}else if(Type == "String[]"){
			ident = n.f0.toString();
		}else if(Type == "int[]"){
			ident = n.f0.toString();
		}else if(Type == "boolean"){
			ident = n.f0.toString();
		}else if(Type == "int"){
			ident = n.f0.toString();
		}else if(Type == ""){
			Type = n.f0.toString();
		}else{
			ident = n.f0.toString();
		}
	}

	public void visit(VarDeclaration n){
		n.f0.accept(this); // Type()
		n.f1.accept(this); // Identifier()
		n.f2.accept(this); // ;

		addToSymT(ident, Type, Integer.toString(scope));
		Type = "";
		ident = "";
	}

	public void visit(ArrayType n){

		Type = "int[]";	

		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
    }
	
	public void visit(BooleanType n){
		Type = "boolean";
	
		n.f0.accept(this);
	}

	public void visit(IntegerType n){
		Type = "int";
		
		n.f0.accept(this);
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
