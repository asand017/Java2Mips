/* 
 * Team Members: Aaron Sanders, Crisitian Ibarra, Leon Peng
 *
 * PrinterClass.java
 *
 * Prints translations into vapor file
 *
 */

class PrinterClass {
	int scope;
	
	//Left Bracket
	void incScope() {
		scope++;
	}


	//Right Bracket
	void decScope() {
		scope--;
	}

	void printIdent() {
		for(int i = 0; i < scope; i++){
			System.out.print("\t");
		}
	}



}
