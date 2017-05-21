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

	public static ArrayList<String> methods_list = new ArrayList<String>();
	
	public static Table table = new Table();

	public static Goal root = null;

	public String alloc = "";
	public static ArrayList<String> f_alloc = new ArrayList<String>();
	public boolean get_alloc = false;
	public boolean alloc_flag = false;

	public VTable vtable = new VTable(); //Vtable	

	public static ClassRecord classRecord = new ClassRecord(); //ClassRecord

	public static String Type = "";

	public static String ident = "";

	public static String curr_class = ""; //current class for "this" keyword

	public int scope = 0;

	public static Map<String, String> field_vars = new HashMap<String, String>(); // (class_name, field_var_num)

	public boolean before_meth = true;

	public int field_num = 0;
	
	public void addToAllVtable(String class_name, ArrayList<String> methods){
		VTable vtables = new VTable(); 
		vtables.vtable_class = class_name;
		
		for(int i = 0; i < methods.size(); i++){
			vtables.vtable.add(methods.get(i));
		}		

		all_vtables.add(vtables);
		//VTable temp = all_vtables.get(0);
		//System.out.println(temp.getClassName());
	}

	public void printAllVtable(){
		//System.out.println(all_vtables.get(0).getClassName());
		///System.out.println(all_vtables.get(1).getClassName());
		for(int i = 0; i < classRecord.recordSize(); i++){
			String temp_class = classRecord.getName(i);
			for(int j = 0; j < all_vtables.size(); j++){	
				if(temp_class == all_vtables.get(j).getClassName()){
					VTable temp = all_vtables.get(j);
					System.out.print("class name: " + temp_class + " -> methods: ");
					temp.printVtable();
					//all_vtables.get(i).printVtable();
				}
			}
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
	
		curr_class = ident; //to know which object 'this' keyword refers to
		
		n.f2.accept(this);

		addToSymT(ident, Type, Integer.toString(scope));
		scope = scope + 1;

		n.f3.accept(this);
		n.f4.accept(this);
		n.f5.accept(this); //void
		n.f6.accept(this); //main
   
		Type = "method";//n.f5.toString();
		

		addToSymT(n.f6.toString(), Type, Integer.toString(scope));

		n.f7.accept(this);
		n.f8.accept(this);
		n.f9.accept(this);
		n.f10.accept(this);
		
		//methods_list.add("main");

		Type = "String[]";
		//Type = "local variable";		

		n.f11.accept(this);
		n.f12.accept(this);
		n.f13.accept(this);

		scope = scope + 1;

		addToSymT(ident, Type, Integer.toString(scope));
		
		//alloc = ident;
		alloc_flag = true;
		n.f14.accept(this);
		n.f15.accept(this);
		//System.out.println(alloc + " here.......");	
		alloc_flag = false; // IF WE HAVE TIME -> make sure it can handle multiple method calls to different classes
		get_alloc = false;
		f_alloc.add(alloc);
		//System.out.println(f_alloc + " here.......");	
		//addToAllVtable(curr_class, methods_list);
		
		n.f16.accept(this);
	
		scope = scope - 1;
		
		n.f17.accept(this);

		scope = scope - 1;
		//System.out.println(f_alloc);//.get(0));// + " here.......");	
	}

	public void visit(MessageSend n){
		if(alloc_flag){
			//System.out.println("here");
			get_alloc = true;
			alloc_flag = false;
		}
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		n.f5.accept(this);
		

	
	}
	
	public void visit(Identifier n){

		n.f0.accept(this);
		
		if(get_alloc) {
			alloc = n.f0.toString();
			get_alloc = false;
			//System.out.println(n.f0.toString() + " " + alloc + "here");
		}

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

		if(before_meth){
			field_num++;
		}

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

		vtable.setClass(ident);

		classRecord.add(ident);

		//ClassRecord.put(ident, index);
		//index++;

		curr_class = ident;

		addToSymT(ident, Type, Integer.toString(scope));

		scope = scope + 1;
		n.f3.accept(this); // (VarDeclaration())*

		field_vars.put(curr_class, Integer.toString(field_num));
		before_meth = false;

		n.f4.accept(this); // (MethodDeclaration())*

		addToAllVtable(curr_class, methods_list);
	
		n.f5.accept(this); // }

		methods_list.clear();

		scope = scope - 1;
	
		before_meth = true;
		field_num = 0;
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
		vtable.setClass(ident);
	
		n.f2.accept(this); // "extends"
		n.f3.accept(this); // Identifier()
		n.f4.accept(this); // "{"

		scope = scope + 1;

		n.f5.accept(this); // (VarDeclaration() )*

		field_vars.put(curr_class, Integer.toString(field_num));
		before_meth = false;		

		n.f6.accept(this); // (MethodDeclaration() )*

		addToAllVtable(curr_class, methods_list);		

		n.f7.accept(this); // "}"

		methods_list.clear();		

		scope = scope - 1;

		before_meth = true;
		field_num = 0;
	}

	public void visit(MethodDeclaration n){//More work to be done later
		n.f0.accept(this); // "public"

		//Type = "public";
	
		n.f1.accept(this); // Type()
		n.f2.accept(this); // Identifier()
	
		//vtable.setClass(curr_class); // connect to parent class
		//vtable.add(ident); // add method name
		
		methods_list.add(ident);
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

	    	root = parser.Goal();
			PopSymbolTable n = new PopSymbolTable();
			n.visit(root);
			//System.out.println(f_alloc);//.get(0) + "stop/////////");
			//parser.ReInit(in);

			//System.out.println(field_vars);

			//System.out.print("Symbol Table: ");
			//table.printall();
	
			//System.out.print("\n" + "\n");			

			//System.out.print("Class Record: ");
			//classRecord.printRecord();

			//System.out.println();	

			//System.out.print("vtable: ");
			//vtable.printVtable();
			//printAllVtable();
			//System.out.println();
			
			//System.out.println(all_vtables.get(0).getClassName());//printVtable();

			//System.out.println(all_vtables);

		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
