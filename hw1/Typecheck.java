/**
 * Team Members: Aaron Sanders, Cristian Ibarra, Leon Peng
 * 
 * Typecheck.java
 *
 * We finished off on VarDeclaration. Continue tracing through the MainClass production and continue
 * populating the symbol table.
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

   //2 global vars to hold left and right sides of conditional operators
   public static String lhs = "";
   public static String rhs = "";
   public static boolean allow = false;

   public void enable_comp(String a){ //enables comparision of 2 side of a conditional
	if(allow && lhs == ""){
		lhs = a;
		
	}else{
		rhs = a;
	}
   }

   public void reset(){ //reset compared value variables
	allow = false;
	lhs = "";
	rhs = "";
   }

   public void compare(){ // compare lhs and rhs of comparators
	if(lhs == rhs){
	    ExpType = lhs;
	}else{
	    //System.out.println(lhs);
	    //System.out.println(rhs);
	    System.out.println("Type Error");
	    System.exit(1);
	}
   }

   // Variables specifically for Assignment Statement - Start
   public static String AssnStatIdType = ""; //The type of the lhs identifier

   public static String Stat = ""; //The currently active state

   public static String ExpType = ""; //The type of the rhs expression
   // End
   
   //public static String Exp2Type = ""; //Array Assignment Statement 

   public static boolean firstpass = true;

   public static String Exp = "";

   public static boolean ident = false;

   public static boolean of_type = false;

   public static String Type = "";

   public static Table table = new Table();

   public static Symbol sym = new Symbol();
	
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

	table.add(n.f5.toString(), n.f6.toString());
	/*sym.scope = scope; // record symbol table set change
	sym.type = n.f6.toString();
	sym.name = n.f5.toString();
	table.changes.add(sym);*/

	n.f7.accept(this);
	n.f8.accept(this);
	n.f9.accept(this);
	n.f10.accept(this);
	
	Type = "String[]";
	
	n.f11.accept(this);

	n.f12.accept(this);
	n.f13.accept(this);
	
	scope = scope + 1; // entering new scope

	n.f14.accept(this);
	n.f15.accept(this);
	n.f16.accept(this);

	scope = scope - 1; // exiting scope

	n.f17.accept(this);

	scope = scope - 1; //exiting scope
   }

   public void visit(Identifier n) { //make sure to check symbol table for duplicate names
	if(firstpass){ //populating symbol table
	
		if(Type == "class"){
		    table.add(n.f0.toString(), Type);
		    /*sym.scope = scope; // record symbol table set change
		    sym.type = Type;
		    sym.name = n.f0.toString();
    		    table.changes.add(sym);*/
		    Type = "";
		}else if(Type == "String[]"){
		    table.add(n.f0.toString(), "String[]");
		    /*sym.scope = scope; // record symbol table set change
		    sym.type = "String[]";
		    sym.name = n.f0.toString();
		    table.changes.add(sym);*/
		    Type = "";
		}else if(Type == "String"){
		    table.add(n.f0.toString(), Type);
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
		}else if(Type != ""){ //if a identifier is being used has a object/data type. i.e. 'Fac x'
		    
		    if(of_type == false) {
			Type = n.f0.toString();
			of_type = true;
		    }

		    else {
			table.add(n.f0.toString(), Type);
			Type = "";
			of_type = false;
		    }

		}
	}else{ // check statements and expressions
		
		if(Type == "" && table.check4name(n.f0.toString())){
		    System.out.print("Type error"); // duplicate name attempting to be used
		    System.exit(1);
		}

		if(Type == "" && !table.check4name(n.f0.toString())){
		    System.out.print("Type error: undeclared variable"); // variable hasn't been declared
		    System.exit(1);
		}

		if(Exp == "AndExpression"){ // PE && PE
		    enable_comp(table.getType(n.f0.toString()));
		    Exp = "";
		}else if(Exp == "CompareExpression"){ // PE < PE
		    enable_comp(table.getType(n.f0.toString()));
		    Exp = "";
		}else if(Exp == "PlusExpression"){ // PE + PE
		    enable_comp(table.getType(n.f0.toString()));
		    Exp = "";
		}else if(Exp == "MinusExpression"){ // PE - PE
		    enable_comp(table.getType(n.f0.toString()));
		    Exp = "";
		}else if(Exp == "TimesExpression"){ // PE * PE
		    //if(Stat == "MessageSend"){
			//enable_comp(table.getType(n.f0.toString()));		   	
		   	//Stat = "";
			//Exp = "";
		    //}else{
		    enable_comp(table.getType(n.f0.toString()));
		    Exp = "";
		    //}
		}

		if(Stat == "AssignmentStatement"){ // ID = E;
		    //enable_comp(table.getType(n.f0.toString()));
		    AssnStatIdType = table.getType(n.f0.toString());
		    //System.out.println(AssnStatIdType);
		    Stat = "";
		}else if(Stat == "ArrayAssignmentStatement"){
		    AssnStatIdType = table.getType(n.f0.toString());
		    Stat = "";
		}

		if(Stat == "MessageSend"){
		    enable_comp(table.getType(n.f0.toString()));
		    //System.out.println("MessageSend");
		    Stat = "";
		    ExpType = table.getType(n.f0.toString());
			
		    //Exp = "";
		}

	}
   }

   public void visit(ClassDeclaration n){

	Type = n.f0.toString();

	n.f0.accept(this);
	n.f1.accept(this);
	n.f2.accept(this);
		
	scope = scope + 1;
	
	n.f3.accept(this);
	n.f4.accept(this);
	n.f5.accept(this);

	scope = scope - 1;
   }

   public void visit(ClassExtendsDeclaration n){

	Type = n.f0.toString();

	scope = scope + 1;

	n.f0.accept(this);
	n.f1.accept(this);
	n.f2.accept(this);
	n.f3.accept(this);	
	n.f4.accept(this);
	n.f5.accept(this);
	n.f6.accept(this);
	n.f7.accept(this);

	scope = scope - 1;
   }

   public void visit(MethodDeclaration n){
	n.f0.accept(this);
	n.f1.accept(this);
	n.f2.accept(this);
	n.f3.accept(this);
	n.f4.accept(this);
	n.f5.accept(this);
	n.f6.accept(this);

	scope = scope + 1; // increment scope

	n.f7.accept(this);
	n.f8.accept(this);
	n.f9.accept(this);
	n.f10.accept(this);
	n.f11.accept(this);
	n.f12.accept(this);

	scope = scope - 1; // decrement scope
   }

   public void visit(AndExpression n){ // #1(bool) && #2(bool)

	Exp = "AndExpression";
	
	allow = true;	

	n.f0.accept(this);
	n.f1.accept(this);

	Exp = "AndExpression";

	n.f2.accept(this);

	compare();
	reset();

   }

   public void visit(CompareExpression n){
	
	Exp = "CompareExpression";

	allow = true;

	
	n.f0.accept(this);
	n.f1.accept(this);

	Exp = "CompareExpression";
	
	n.f2.accept(this);

	//System.out.println(lhs);
	//System.out.println(rhs);
	compare();
	reset();
   }

   public void visit(PlusExpression n){

	Exp = "PlusExpression";

	allow = true;

	n.f0.accept(this);
	n.f1.accept(this);
		
	Exp = "PlusExpression";
	
	n.f2.accept(this);

	compare();
	reset();

   }
 
   public void visit(MinusExpression n){

	Exp = "MinusExpression";

	allow = true;

	n.f0.accept(this);
	n.f1.accept(this);

	Exp = "MinusExpression";

	n.f2.accept(this);

	compare();
	reset();

   }


   public void visit(TimesExpression n){

	Exp = "TimesExpression";

	allow = true;

	n.f0.accept(this);
	n.f1.accept(this);

	Exp = "TimesExpression";

	n.f2.accept(this);
	
	//System.out.println(lhs);
	//System.out.println(rhs);
	compare();
	if(Stat != "AssignmentStatement"){
		reset();

	}else if(Stat == "AssignmentStatement"){
		//ExpType = lhs;
	}
   }

   public void visit(ArrayLookup n){ // PE[PE] --> May need to make sure 2nd PE is int
	Exp = "ArrayLookup";

	n.f0.accept(this);
	n.f1.accept(this);
	n.f2.accept(this);
	n.f3.accept(this);

   }

   public void visit(AssignmentStatement n){

	Stat = "AssignmentStatement";	

	//allow = true;
	n.f0.accept(this);
	n.f1.accept(this);

	Stat = "AssignmentStatement";
	n.f2.accept(this);
	n.f3.accept(this);

	//if(!firstpass){//secondpass
		//System.out.println(AssnStatIdType);
		//System.out.println(ExpType);
	//	if(AssnStatIdType != ExpType){
	//	     System.out.println("Type error");
	//	     System.exit(0);
	//	}
	//}

	//if(AssnStatIdType != ExpType){ // lhs not same type as rhs
	//    System.out.println("Type error");
	//    System.exit(0);
	//}
	
	//compare();
	//reset();
	

   }

   public void visit(ArrayAssignmentStatement n){
	Stat = "ArrayAssignmentStatement";

	n.f0.accept(this);
	n.f1.accept(this);
	n.f2.accept(this);
	
	if(ExpType != "int"){
	    System.out.println("Type error");
	    System.exit(1);
	}
	
	n.f3.accept(this);
	n.f4.accept(this);
	n.f5.accept(this);

	if(AssnStatIdType != ExpType){
	    System.out.println("Type error");
	    System.exit(0);
	}

	n.f6.accept(this);

   }

   public void visit(MessageSend n){
	
	Stat = "MessageSend";
	
	n.f0.accept(this);
	n.f1.accept(this);
	n.f2.accept(this); // go into Identifier visitor
	n.f3.accept(this);
	n.f4.accept(this);
	n.f5.accept(this);

   }

/*   public void visit(ArrayAllocationExpression n){
	n.f0.accept(this);
	n.f1.accept(this);
	n.f2.accept(this);
	n.f3.accept(this);
	n.f4.accept(this);
	//enable_comp("int[]");
   }

   public void visit(AllocationExpression n){
	n.f0.accept(this);
	n.f1.accept(this);
	n.f2.accept(this);
	n.f3.accept(this);
   }
*/


   public void visit(IntegerLiteral n){
	n.f0.accept(this);
	if(Stat == "AssignmentStatement"){
	    ExpType = "int";
	}
	//System.out.println(n.f0.toString());	
	//enable_comp("int");
   }

   public void visit(TrueLiteral n){
	n.f0.accept(this);
	enable_comp("boolean");
   }

   public void visit(FalseLiteral n){
	n.f0.accept(this);
	enable_comp("boolean");
   }

   /*public void visit(PrimaryExpression n){

	n.f0.accept(this);
   }*/


   public void visit(Type n) {
	
	Type = n.f0.toString();

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
		
		//System.out.println(scope);

		Goal root = parser.Goal();
		Typecheck n = new Typecheck();
		n.visit(root); //first pass
		firstpass = false;
		n.visit(root); //second pass

		//table.printall();	

		System.out.println("Program type checked successfully");
	} catch(Exception e){
	 	e.printStackTrace();
	}
   }

}


