/* Team Members: Aaron Sanders, Cristian Ibarra, Leon Peng
 * Phase 3
 * File: FlowGraphNode.java
 *
 *
 */

import cs132.util.*;
import cs132.vapor.parser.*;
import cs132.vapor.ast.*;
import java.io.*;
import java.util.*;

public class FlowGraphNode{
	public int current_line;
	public ArrayList<Integer> succ = new ArrayList<Integer>();
	public ArrayList<Integer> pred = new ArrayList<Integer>();

	public void set(int curr){
		current_line = curr;	
	}	
	
	public void add_out(int out){//add out-edge to successor node
		succ.add(out);
	}

	public void add_in(int in){//add in-edge to predecessor node
		pred.add(in);
	} 
	
}
