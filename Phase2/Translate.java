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
import java.io.BufferedWriter;
import visitor.*;
import syntaxtree.*;

class Translate extends DepthFirstVisitor{
	public String h_alloc  = "";
	public PopSymbolTable names = new PopSymbolTable();
	public PrinterClass indent = new PrinterClass();
	//public PrintWriter writer = new Print
	public String statement = "";
	public String string_num = "";
	public int num = 0;
	public int t_num = 0;
	public int t_origin = 0;
	public int null_c = 1;

	public boolean in_main = false;

	public int branch_label = 0;

	public void dat_seg(ArrayList<VTable> table){
		for(int i = 0; i < table.size(); i++) {
			System.out.println("const vmt_" + table.get(i).vtable_class);		
	
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

	public int returnFieldNum(String class_name){
		if(names.field_vars.containsKey(class_name)){
			return Integer.parseInt(names.field_vars.get(class_name));
		}
		return 0;
	}
	
	public void visit(MainClass n){

		in_main = true;

		System.out.println("func Main()");
		indent.incScope();
		indent.printIdent();
		int allc = returnFieldNum(names.f_alloc.get(0));
		//System.out.println("here " + h_alloc);
		System.out.println("t." + t_num + " = HeapAllocZ(" + (allc*4+4) + ")");
		indent.printIdent();
		//System.out.println(names.alloc);
		
		System.out.println("[t." + t_num + "] = :vmt_" + names.f_alloc.get(0));
		
		indent.printIdent();
		System.out.println("if t." + t_num + " goto :null" + null_c);
		indent.printIdent();
		System.out.println("Error(\"null pointer\")");
		indent.printIdent();
		System.out.println("null" + null_c + ":");	

		//statement = "t." + t_num;
		
		null_c++;
		//t_num++;

		//statement = "t." + t_num;

		n.f0.accept(this);
		n.f1.accept(this);
		
		n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		n.f5.accept(this);
		n.f6.accept(this);
		n.f7.accept(this);
		n.f8.accept(this);
		n.f9.accept(this);
		n.f10.accept(this);
		n.f11.accept(this);
		n.f12.accept(this);
		n.f13.accept(this);
		n.f14.accept(this);
		n.f15.accept(this);

		t_num++;

		
		//System.out.println(statement);
		n.f16.accept(this);
		n.f17.accept(this);

		System.out.println("\tret");
		System.out.println();

		indent.decScope();
	
		t_num = 0;
		in_main = false;
		
	}

	public void visit(PrintStatement n){

		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		
		//t_num++;

		indent.printIdent();
		System.out.println("PrintIntS(t." + t_num  + ")");

	}

	public void visit(CompareExpression n) {
		String comp1 = "";
		String comp2 = "";

		n.f0.accept(this);

		comp1 = statement;
		
		//if(!in_main){
		//	statement = "";
		//}
		statement = "";
		n.f1.accept(this);
		n.f2.accept(this);
	
		//statement = "";	
		comp2 = statement;
		indent.printIdent();
		System.out.println("t." + t_num + " = LtS(" + comp1 + " " + comp2 + ")");
		
		if(!in_main){
			statement = "";
		}
	}
	
	public void visit(IfStatement n){//translates if-statements
		int current_label = branch_label + 1;
		statement = "";	
	
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		
		indent.printIdent();
		System.out.println("if" + branch_label + " t." + t_num + " goto :if"
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

		t_num = 0;
	}

	public void visit(Identifier n){//assigning an identifier for translation
		n.f0.accept(this);
		if(in_main){
			statement = "t." + t_num ;
		}else{
			statement = statement + n.f0.toString();
		}
	}
	
	public void visit(IntegerLiteral n){//assigning a number for translation
		n.f0.accept(this);
		if(!in_main) {
			statement = statement + n.f0.toString();
		} else{
			statement = n.f0.toString();
		}
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
		t_call = statement;
		t_num++;
		t_origin = t_num;
		System.out.println("t." + t_num + " = [" + statement + "]");
		//t_call = statement;
		//statement = "";
		indent.printIdent();
		System.out.println("t." + t_num + " = [t." + t_num + "+0]");

		t_num++;
		
		statement = "";
		n.f2.accept(this);
		statement = "";
		n.f3.accept(this);
		statement = "";
		n.f4.accept(this);
		//statement = "";
		n.f5.accept(this);
		
		//if(!in_main){
		//statement = "";
		//}
		//t_num++;
		indent.printIdent();
		System.out.println("t." + (t_num) + " = call t." + (t_origin) +"(" + t_call + 
		" "+ statement + ")");


			
	}

	public void visit(TimesExpression n){
		boolean emp = false;
		String mult = "";

		if(statement == "") {
			statement = statement + "t." + t_num + " = MulS(";
			emp = true;
		}else {
			statement = statement + "MulS(";
		}
		n.f0.accept(this);	
		n.f1.accept(this);

		if(emp) {
			statement = statement + " ";
		} else {
			mult = statement;
		}
		
		n.f2.accept(this);		
		
		if(emp) {
			statement = statement + ")";
			indent.printIdent();
			System.out.println(statement);
			statement = "t." + t_num;
			//System.out.println(statement);
			t_num++;	
		} else {
			indent.printIdent();
			System.out.println(mult + " t." + (t_num) + ")");  
		}

		if(!in_main){
			//statement = "";
		}
		//t_num++;
		
	}
	
	public void visit(ThisExpression n){
		n.f0.accept(this);
		statement = statement + "this";	
	}

	public void visit(MinusExpression n){
		//t_num++;
		String sub = "";
		boolean emp = false;
		if(statement == ""){
			statement = statement +"t." + t_num + " = Sub(";
			emp = true;
		} else {
			statement = statement + "Sub(";
		}

		n.f0.accept(this);
		n.f1.accept(this);
		
		if(emp) {
			statement = statement + " ";
		}else {
			 sub = statement;
		}

		n.f2.accept(this);	

		if(emp) {
			statement = statement + ")";
			indent.printIdent();
			System.out.println(statement);
			statement = "t." + t_num;
			t_num++;
		} else {
			indent.printIdent();
			System.out.println(sub + " t." + (t_num) + ")");  
		}
	}

	public void visit(PlusExpression n){
		
		String sub = "";
		boolean emp = false;
		if(statement == ""){
			statement = statement +"t." + t_num + " = Add(";
			emp = true;
		} else {
			statement = statement + "Add(";
		}

		n.f0.accept(this);
		n.f1.accept(this);
		
		if(emp) {
			statement = statement + " ";
		}else {
			 sub = statement;
		}

		n.f2.accept(this);	

		if(emp) {
			statement = statement + ")";
			indent.printIdent();
			System.out.println(statement);
			statement = "t." + t_num;
			t_num++;
		} else {
			indent.printIdent();
			System.out.println(sub + " t." + (t_num) + ")");  
		}
	}
	
	public void visit(TrueLiteral n){
		n.f0.accept(this);
		if(!in_main) {
			statement = statement + "1";
		} else{
			statement = "1";
		}

	}
	
	public void visit(FalseLiteral n){
		n.f0.accept(this);
		if(!in_main) {
			statement = statement + "0";
		} else{
			statement = "0";
		}

	}

	public void start(BufferedReader in){
		try{
			PopSymbolTable populate = new PopSymbolTable();
			populate.start(in);
			names = populate;
			//h_alloc = populate.alloc;
			//System.out.println(populate.alloc + " here11111111");

			dat_seg(populate.all_vtables);

			Translate n = new Translate();

			n.visit(populate.root);

			/*for(int i = 0; i < names.classRecord.classRecord.size(); i++){
				System.out.println(returnFieldNum(names.classRecord.classRecord.get(i)));
			}*/

			//populate.printAllVtable();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
