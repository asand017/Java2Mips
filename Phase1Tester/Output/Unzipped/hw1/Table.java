/*
 *
 *
 *
 *
 *
 */
import java.util.*;

class Table{
	
	public ArrayList changes = new ArrayList();

	public Map table = new HashMap();
	public Object add(String name, String type){
		changes.add(name);		
		return table.put(name, type);
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

	public boolean check4type(String type){
		return table.containsValue(type);
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
