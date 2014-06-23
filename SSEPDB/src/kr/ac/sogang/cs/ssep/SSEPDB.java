package kr.ac.sogang.cs.ssep;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SSEPDB {

	public static String myS = "hello";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if(args.length == 0){
			System.err.println("Input File name");
			System.exit(1);
		}
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(args[0]));
			String line;
			
			while((line = br.readLine()) != null){
				System.out.println(line);
			}
			
			br.close();
		} catch(IOException e){
			// Print error message
			e.printStackTrace();
		}
	}

}
