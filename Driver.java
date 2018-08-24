package apps;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import apps.Graph;

public class Driver {
	
	public static void main(String args[]) throws FileNotFoundException{
		
		Scanner asdf = new Scanner(new File("test7.txt"));
		Graph a = new Graph(asdf);
	
		
	
		//Friends.shortestChain(a, "P", "M");
     	Friends.cliques(a, "ru");
		
		//Friends.connectors(a);
	}
	
	

}
