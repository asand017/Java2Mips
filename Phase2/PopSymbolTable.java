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
	public static ArrayList<VTable> all_vtables = new ArrayList<VTable>(); // holds all the vtables
	
	public static Table table = new Table();

	public VTable vtable = new VTable(); //Vtable	

	public static ClassRecord classRecord = new ClassRecord(); //ClassRecord

	public static String Type = "";

	public static String ident = "";

	public static String curr_class = ""; //current class for "this" keyword

	public int scope = 0;
	
	//public int index = 0; //starting index for Class Record to be incremented
	//public int offset = 0;

	public void addToAllVtable(VTable x){
		//VTable vtable = new VTable(); 
		all_vtables.add(x);
		VTable temp = all_vtables.get(0);
		System.out.println(temp.getClassName());
		//vtable = new VTable();
		//System.out.println(vtable.getClassName());
	}

	public void printAllVtable(){
		//System.out.println(all_vtables.get(0).getClassName());
		///System.out.println(all_vtables.get(1).getClassName());
		for(int i = 0; i < classRecord.recordSize(); i++){
			
			VTable temp = all_vtables.get(i);
			System.out.print("class name: " + temp.getClassName() + " -> methods: ");
			all_vtables.get(i).printVtable();
		}
	}

    public void addToSymT(String name, String type, String Scope){
	    ArrayList<String> type_scope = new ArrayList<String>();
		type_scope.add(type);
		type_scope.add(Scope);
		table.add(name, type_scope);
		Type = ""; //reset Type var
		ident = ""; //reset identifier var
	}

	public void visit(MainClass n){
		
		Type = n.f0.toString();
	
		n.f0.accept(this);
		n.f1.accept(this);
	
		vtable.setClass(ident);
		

		curr_class = ident; //to know which object 'this' keyword refers to

		classRecord.add(ident);

		//class_ref.put(ident, (String) vtable);

		//ClassRecord.put(ident, Integer.toString(index));
		//index++; //increment index
	
		n.f2.accept(this);

		vtable.setClass(curr_class);

		addToSymT(ident, Type, Integer.toString(scope));

		scope = scope + 1;

		n.f3.accept(this);
		n.f4.accept(this);
		n.f5.accept(this); //void
		n.f6.accept(this); //main
   
		Type = "method";//n.f5.toString();
		
		//vtable.setClass(ident);	
		vtable.add("main"); // add main to vtable for primary class

		addToSymT(n.f6.toString(), Type, Integer.toString(scope));

		n.f7.accept(this);
		n.f8.accept(this);
		n.f9.accept(this);
		n.f10.accept(this);
		
		Type = "String[]";
		//Type = "local variable";		

		n.f11.accept(this);
		n.f12.accept(this);
		n.f13.accept(this);

		scope = scope + 1;

		addToSymT(ident, Type, Integer.toString(scope));

		n.f14.accept(this);
		n.f15.accept(this);

		addToAllVtable(vtable);
		
		n.f16.accept(this);
	
		scope = scope - 1;
		
		n.f17.accept(this);

		scope = scope - 1;
	
	}
	
	public void visit(Identifier n){

		n.f0.accept(this);

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
		}/*else if(Type == "local variable"){
			ident = n.f0.toString();
		}*/else if(Type == "method"){
			ident = n.f0.toString();
		}else if(Type == ""){
			Type = n.f0.toString();
			//Type = "local variable";
		}else{
			ident = n.f0.toString();
		}
	}

	public void visit(VarDeclaration n){
		n.f0.accept(this); // Type()
		n.f1.accept(this); // Identifier()
		n.f2.accept(this); // ;

		addToSymT(ident, Type, Integer.toString(scope));
	}

	public void visit(ArrayType n){

		Type = "int[]";	

		//Type = "local variable";

		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
    }
	
	public void visit(BooleanType n){
		Type = "boolean";
	
		//Type = "local variable";

		n.f0.accept(this);
	}

	
	public void visit(IntegerType n){
		
		Type = "int";
		
		//Type = "local variable";

		n.f0.accept(this);
	}

	public void visit(ClassDeclaration n){
		n.f0.accept(this); // class

		Type = "class";

		n.f1.accept(this); // identifier()
		n.f2.accept(this); // {

		vtable = new VTable();

		classRecord.add(ident);

		//ClassRecord.put(ident, index);
		//index++;

		curr_class = ident;

		addToSymT(ident, Type, Integer.toString(scope));

		scope = scope + 1;
		n.f3.accept(this); // (VarDeclaration())*
		n.f4.accept(this); // (MethodDeclaration())*

		addToAllVtable(vtable);
	
		n.f5.accept(this); // }


		scope = scope - 1;
	}

	public void visit(ClassExtendsDeclaration n){
		n.f0.accept(this); // "class"
	
		Type = "class";
	
		n.f1.accept(this); // Identifier()

		classRecord.add(ident);

		//ClassRecord.put(ident, index);
		//index++;

		curr_class = ident;
		addToSymT(ident, Type, Integer.toString(scope));

		vtable = new VTable();
	
		n.f2.accept(this); // "extends"
		n.f3.accept(this); // Identifier()
		n.f4.accept(this); // "{"

		scope = scope + 1;

		n.f5.accept(this); // (VarDeclaration() )*
		n.f6.accept(this); // (MethodDeclaration() )*

		addToAllVtable(vtable);		

		n.f7.accept(this); // "}"
		
		scope = scope - 1;
	}

	public void visit(MethodDeclaration n){//More work to be done later
		n.f0.accept(this); // "public"

		//Type = "public";
	
		n.f1.accept(this); // Type()
		n.f2.accept(this); // Identifier()
	
		vtable.setClass(curr_class); // connect to parent class
		vtable.add(ident); // add method name

		//System.out.println(Type);
		Type = "method";
		addToSymT(ident, Type, Integer.toString(scope));
	
		n.f3.accept(this); // "("

		scope = scope + 1;	

		n.f4.accept(this); // ( FormalParameterList() )?
		n.f5.accept(this); // ")"
		n.f6.accept(this); // "{"
		n.f7.accept(this); // (VarDeclaration() )*
		n.f8.accept(this); // (Statment() )*
		n.f9.accept(this); // "return"
		n.f10.accept(this);// Expression()
		n.f11.accept(this);// ";"
		n.f12.accept(this);// }

		scope = scope - 1;
	}

	public void visit(FormalParameter n){
		n.f0.accept(this);
		n.f1.accept(this);

		addToSymT(ident, Type, Integer.toString(scope));
	}

	public void visit(ThisExpression n){
		n.f0.accept(this);

		//table.getScope(curr_class);	
		addToSymT(n.f0.toString(), curr_class, table.getScope(curr_class)); //Integer.toString(scope));	
	}

	public void start(BufferedReader in){
		try{

			MiniJavaParser parser = new MiniJavaParser(in);

	    	Goal root = parser.Goal();
			PopSymbolTable n = new PopSymbolTable();
			n.visit(root);

			System.out.println();

			System.out.print("Symbol Table: ");
			table.printall();
	
			System.out.print("\n" + "\n");			

			System.out.print("Class Record: ");
			classRecord.printRecord();

			System.out.println();	

			//System.out.print("vtable: ");
			//vtable.printVtable();
			printAllVtable();
			System.out.println();
			
			//System.out.println(all_vtables.get(0).getClassName());//printVtable();

			//System.out.println(all_vtables);

		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
