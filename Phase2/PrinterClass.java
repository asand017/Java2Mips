/*
 *
 *
 *
 */

class Printer() {
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
