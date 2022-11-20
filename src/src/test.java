package src;

import java.io.FileNotFoundException;

public class test {

	public static void main(String[] args) {
		
		try {
			readFromFile x = new readFromFile("a.txt");
			x.readGivenFile();
		} catch (FileNotFoundException e) {
			System.out.println("--------ERROR 404-------- ");
			System.out.println("Warning!!!!File Not Found");
		}
		
		
	}

}
