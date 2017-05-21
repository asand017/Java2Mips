/*
 * Team Members: Aaron Sanders, Cristian Ibarra, Leon Peng
 *
 * Translate.java
 *
 * Translates Java code to Vapor code
 *
 */

import java.util.*;
import java.io.BufferedReader;
import visitor.*;
import syntaxtree.*;

class Translate extends DepthFirstVisitor{
	public PopSymbolTable names = new PopSymbolTable();
	public PrinterClass indent = new PrinterClass();
	public String statement = "";
	public String string_num = "";
	public int num = 0;
	public int t_num = 1;

	public int branch_label = 0;

	public void dat_seg(ArrayList<VTable> table){
		for(int i = 0; i < table.size(); i++) {
			System.out.println("const vmt_ " + table.get(i).vtable_class);		
	
			for(int j = 0; j < table.get(i).vtable.size(); j++) {
				System.out.println("\t:" + table.get(i).vtable_class +
				"." + table.get(i).vtable.get(j));
			}
			System.out.println();
		}
	}

	public String class_str(ArrayList<VTable> table, String name){
		for(int i = 0; i < table.size(); i++) {
			for(int j = 0; j < table.get(i).vtable.size(); j++) {
				//System.out.println(table.get(i).vtable.get(j) + "=" + name);
				if(table.get(i).vtable.get(j).equals(name)){ 
					return table.get(i).vtable_class;	
				}
			}
			
		}
		return "";
	
	}

	public void visit(CompareExpression n) {
		String comp1 = "";
		String comp2 = "";

		n.f0.accept(this);

		comp1 = statement;
		statement = "";

		n.f1.accept(this);
		n.f2.accept(this);
		
		comp2 = statement;
		indent.printIdent();
		System.out.println("t.0 = LtS(" + comp1 + " " + comp2 + ")");	
		statement = "";
	}
	
	public void visit(IfStatement n){//translates if-statements
		int current_label = branch_label + 1;
		statement = "";	
	
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		
		indent.printIdent();
		System.out.println("if" + branch_label + " t0 goto :if"
		+ (branch_label+1) + "_else"); 
		branch_label++;
		indent.incScope();

		n.f3.accept(this);
		
		statement = "";
	
		n.f4.accept(this);
		n.f5.accept(this);
		
		indent.printIdent();
		System.out.println(statement);
		statement = "";

		indent.printIdent();
		System.out.println("goto :if" + current_label + "_end");
		indent.decScope();

		indent.printIdent();
		System.out.println("if" + current_label + "_else:");
		
		indent.incScope();
		n.f6.accept(this);
		indent.decScope();		

		indent.printIdent();
		System.out.println("if" + current_label + "_end:");
	
	}
	
	public void visit(MethodDeclaration n){//translates a method
		n.f0.accept(this);
		n.f1.accept(this);
		statement = "";
		n.f2.accept(this);

		String c = class_str(names.all_vtables, statement);
		statement = "func " + c + "." + statement + "(this ";
		n.f3.accept(this);
		n.f4.accept(this);
		n.f5.accept(this);
		n.f6.accept(this);

		System.out.println(statement + ")");
		statement = "";
		indent.incScope();
		//indent.printIdent();
		
		n.f7.accept(this);
		n.f8.accept(this);
		
		statement = "";

		n.f9.accept(this);
		n.f10.accept(this);
		n.f11.accept(this);
		n.f12.accept(this);
		
		indent.printIdent();
		System.out.println("ret " + statement);
		statement = "";
		indent.decScope();
	}

	public void visit(Identifier n){//assigning an identifier for translation
		n.f0.accept(this);
		statement = statement + n.f0.toString();
	}
	
	public void visit(IntegerLiteral n){//assigning a number for translation
		n.f0.accept(this);
		statement = statement + n.f0.toString();
	} 
		
	public void visit(AssignmentStatement n){

		n.f0.accept(this);
		statement = statement + " = ";
		n.f1.accept(this);

		n.f2.accept(this);
		n.f3.accept(this);

	}

	public void visit(MessageSend n){
		String t_call;	
		statement = "";	

		n.f0.accept(this);
		n.f1.accept(this);
	
		//statement = "";	
		indent.printIdent();
		System.out.println("t.1 = [" + statement + "]");
		t_call = statement;
		statement = "";
		indent.printIdent();
		System.out.println("t.1 = [t.1+0]");

		n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		n.f5.accept(this);
		
		statement = "";
		indent.printIdent();
		System.out.println("t." + (t_num+1) + " = call t.1(" + t_call +
		" t." + (t_num) + ")");


			
	}

	public void visit(TimesExpression n){
		statement = statement + "MulS(";
		n.f0.accept(this);	
		n.f1.accept(this);

		String mult = statement;
		
		n.f2.accept(this);		

		indent.printIdent();
		System.out.println(mult + " t." + (t_num+1) + ")");  
		statement = "";
		
		
	}
	
	public void visit(ThisExpression n){
		n.f0.accept(this);
		statement = statement + "this";	
	}

	public void visit(MinusExpression n){
		t_num++;
		statement = "t." + t_num + " = Sub(";

		n.f0.accept(this);
		n.f1.accept(this);

		statement = statement + " ";

		n.f2.accept(this);	

		statement = statement + ")";
		
		indent.printIdent();
		System.out.println(statement);
	}

	public void start(BufferedReader in){
		try{
			PopSymbolTable populate = new PopSymbolTable();
			populate.start(in);
			names = populate;

			dat_seg(populate.all_vtables);

			Translate n = new Translate();

			n.visit(populate.root);

			//populate.printAllVtable();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
