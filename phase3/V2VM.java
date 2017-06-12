/* Team Members: Aaron Sanders, Cristian Ibarra, Leon Peng
 * Phase 3
 * File: V2VM.java
 *
 */

import cs132.util.ProblemException;
import cs132.vapor.parser.VaporParser;
import cs132.vapor.ast.VaporProgram;
import cs132.vapor.ast.VBuiltIn.Op;
import cs132.util.CommandLineLauncher.TextOutput;
import cs132.util.*;
import cs132.vapor.ast.*;

import java.io.*;
import java.util.*;

public class V2VM{

	public static ArrayList<String> locals = new ArrayList<String>();

	public static int num_blanks = 0;

	public static int local_var = 0;

	public static VaporProgram parseVapor(InputStream in, PrintStream err) throws
	IOException {
		Op[] ops = {
			Op.Add, Op.Sub, Op.MulS, Op.Eq, Op.Lt, Op.LtS,
			Op.PrintIntS, Op.HeapAllocZ, Op.Error,
		};
		boolean allowLocals = true;
		String[] registers = null;
		boolean allowStack = false;

		VaporProgram tree;
		try {
			tree = VaporParser.run(new InputStreamReader(in), 1, 1,
					   java.util.Arrays.asList(ops),
					   allowLocals, registers, allowStack);

			
		}catch (ProblemException ex) {
			err.println(ex.getMessage());
			return null;
		}

		return tree;
	}

	public static void print(String output){	
		String temp = "";
		for(int i = 0; i < num_blanks; i++){
			temp += " ";
		}
		
		System.out.println(temp + output);
	
	}

	public static void main(String args[]){
		try{

			PrintStream err = new PrintStream(System.out);

			VaporProgram parse = parseVapor(System.in, err);

			for(VDataSegment segm : parse.dataSegments){ //print out data segments
				String mute = "const";
				
				if(segm.mutable == true){
					mute = "var";
				}
				
				print(mute + " " + segm.ident);
				
				num_blanks++;
				for(VOperand value : segm.values){
					print(value.toString());
				}
				num_blanks--;
			}
			//print("");

			for(VFunction funcs : parse.functions){ //visit each function
				System.out.println();		
				
				System.out.print("func " + funcs.ident + " ");			
	
				//System.out.println("[in " + funcs.stack.in + ", out " + funcs.stack.out +
				//	", local " + funcs.stack.local + "]");

				boolean match = false;			
				num_blanks++;	
				for(int i = 0; i < funcs.vars.length; i++){
					for(int y = 0; y < funcs.params.length; y++){
						if(funcs.vars[i] == funcs.params[y].toString()){
							match = true;
						} 
					}

					if(funcs.vars[i].length() > 1){
						if(funcs.vars[i].charAt(0) == 't' && funcs.vars[i].charAt(1) == '.'){
							match = true;
						}
					}

					if(!match){
						if(!locals.contains(funcs.vars[i])){
							locals.add(funcs.vars[i]);
							local_var++;
						}
					}
					
					match = false;
	
				}
				num_blanks--;

				

				System.out.println("[in " + funcs.stack.in + ", out " + funcs.stack.out +
					", local " + (funcs.stack.local + local_var) + "]");
	
				for(int i = 0; i < funcs.labels.length; i++){
					System.out.println(funcs.labels[i].ident + ":");
				}

				if(!locals.isEmpty()){
					//System.out.println(locals);
				}

				locals.clear();
				local_var = 0;
			}

		} catch(Exception e){
			e.printStackTrace();
		}	
	}
	
}
