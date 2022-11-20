package src;

public class instruction {
	long all ;
	int opcode = 0;  
    int R1 = 0;      
    int R2 = 0;     
    int R3 = 0;     
    int shamt = 0;   
    int imm = 0;     
    int address = 0; 
    int writeback = 0;
    long result ;
	public instruction(long all) {
		this.all = all ;
		
	}

}
