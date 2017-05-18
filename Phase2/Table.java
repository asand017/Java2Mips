/*
 *
 *
 *
 *
 *
 */
import java.util.*;

class Table{
	
	public Map table = new HashMap();
	public Object add(String name, ArrayList<String> type_scope){
		// type_scope --> (type, scope)
		return table.put(name, type_scope); // (ident/name, (type, scope))
	}

	public Object delete(String name){
		return table.remove(name);
	}

	public void printall(){
		System.out.print("\t" + table);
	}
	
	public boolean check4name(String name){
		return table.containsKey(name);
	}

	public boolean check4type(ArrayList<String> type_scope){
		return table.containsValue(type_scope);
	}
	
	public String getType(String name){
		return (String) table.get(name);
	}

	public boolean compTypes(String name1, String name2){
		if(table.get(name1) == table.get(name2)){
			return true;
		}else{
			return false;

		}
	}

}
