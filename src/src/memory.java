package src;


public class memory {
	long  [] memory ;
	int count =0; 
	
	
	public memory() {
		memory=new long[2048] ;
		
	}
	
//	public int fetch (int pc) {
//		int inst = memory[pc];
//		pc+=1;
//		return inst;
//	}
	public void putinst(int x) {
		memory[count]=x;
		count++;
	}

}
