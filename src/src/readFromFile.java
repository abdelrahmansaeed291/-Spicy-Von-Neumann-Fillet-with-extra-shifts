package src;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class readFromFile {
     String fileName;
    static HashMap <Long , String> in = new HashMap <Long , String > ()  ;
    public readFromFile(String fileName ) {
    this.fileName=fileName;
    }
	public void readGivenFile() throws FileNotFoundException {
		memory memory= new memory();
		FileReader reader = new FileReader(fileName);
		Scanner sc = new Scanner(reader);
		readFromFile r = new readFromFile(fileName);
		int i = 0;
        while(sc.hasNextLine()) {
        	String l = sc.nextLine();
        	long x=r.translator(l);
        	memory.memory[i]= x;
        	i++;
        	memory.count++;
        	in.put(x, l);
        }
        registers regi = new registers();
		excute e = new excute(memory, regi);
		e.excute1();
		System.out.println("the register file is ");
		System.out.print("[");
		   for(int j = 0 ; j < regi.generalregister.length ; j++)
		   {
			   System.out.print ( "R" +j+ " = "+ regi.generalregister[j] +"  ,  "  );
		   }
	        System.out.println("]" );
	        
	    	System.out.println("the memory is ");       
		System.out.print("[");
	   for(int j = 0 ; j < memory.memory.length ; j++)
	   {
		   System.out.print (memory.memory[j] +","  );
	   }
        System.out.println("]" );
        
	
}
	public long translator(String insta) {
		String op="";
		String[] words = insta.split(" ");
		String x = words[0].toLowerCase();
		String y = words[1].toLowerCase();
		String res = "";
		

		switch(x) {
		case "add":op  ="0000";break;
		case "sub":op  ="0001";break;
		case "muli":op ="0010";break;
		case "addi":op ="0011";break;
		case "bne":op  ="0100";break;
		case "andi":op ="0101";break;
		case "ori":op  ="0110";break;
		case "j":op    ="0111";break;
		case "sll":op  ="1000";break;
		case "srl":op  ="1001";break;
		case "lw":op   ="1010";break;
		case "sw":op   ="1011";break;
		default : break;
		}
		res+=op;
	    if(!x.equals("j")) {
	    int temp =Integer.parseInt(y.substring(1));      
	    String dest = Integer.toBinaryString(temp);
	    while(dest.length()<5) {
	    	dest = '0'+dest;
	    }
	    res+=dest;
	    String z = words[2].toLowerCase();
	    int temp2 =Integer.parseInt(z.substring(1)); 
	    String src1 = Integer.toBinaryString(temp2);
	    while(src1.length()<5) {
	    	src1 = '0'+src1;
	    }
	    
	    res+=src1;
	    String l = words[3].toLowerCase();
	    if(op=="0000" || op == "0001") {
	    	int temp3 =Integer.parseInt(l.substring(1));      
		    String src2 = Integer.toBinaryString(temp3);
		    while(src2.length()<5) {
		    	src2 = '0'+src2;
		    }
		    res+=src2+"0000000000000";
	    }else if(op=="1000"||op=="1001"){
	    	String conv = Integer.toBinaryString(Integer.parseInt(l));
	    	while(conv.length()<13) {
	    		conv='0'+conv;
	    	}
	    	res+="00000"+conv;
	    }else {
	    	String conv = Integer.toBinaryString(Integer.parseInt(l));
	    	while(conv.length()<18) {
	    		conv='0'+conv;
	    	}
	    	res+=conv;
	    }
	
	    }else {
	    	String conv = Integer.toBinaryString(Integer.parseInt(y));
	    	
	    	while(conv.length()<28) {
	    		conv='0'+conv;
	    	}
	    	res+=conv;	
	    	
	    }
	 //   System.out.println("long is " + res);
		return Long.parseLong(res,2);
			}
}