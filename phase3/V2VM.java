/* Team Members: Aaron Sanders, Cristian Ibarra, Leon Peng
 * Phase 3
 * File: V2VM.java
 *
 */

import cs132.util.ProblemException;
import cs132.vapor.parser.VaporParser;
import cs132.vapor.ast.VaporProgram;
import cs132.vapor.ast.VBuiltIn.Op;

import java.io.*;
import java.util.*;

public class V2VM{

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

	public static void main(String args[]){
		try{
			//BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			//InputStreamReader in = new InputStreamReader(System.in);

			PrintStream err = new PrintStream(System.out);

			VaporProgram parse = parseVapor(System.in, err);

			System.out.println(parse.allowStack);			

		} catch(Exception e){
			e.printStackTrace();
		}	
	}
	
}
