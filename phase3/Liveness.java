/* Team Members: Aaron Sanders, Cristian Ibarra, Leon Peng
 * Phase 3
 * File: Liveness.java
 *
 */

import cs132.util.*;
import cs132.vapor.parser.*;
import cs132.vapor.ast.*;
import java.io.*;
import java.util.*;
import cs132.vapor.ast.VInstr.Visitor;

//Live interval logic
public abstract class Liveness extends Visitor<Exception>{

	public String name;
	public int start;
	public int end;		

	public void set_interval(String n, int st, int en){
		name = n;
		start = st;
		end = en;	
	}
	
}
