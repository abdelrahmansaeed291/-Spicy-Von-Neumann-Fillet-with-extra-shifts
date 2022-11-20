package src;


public class registers {
	int generalregister [] ;
	final int R0 =0 ;
	static int pc =0;
	
	public registers () {
		generalregister =new int [32];
	    generalregister [0] =0 ;
	}

}
