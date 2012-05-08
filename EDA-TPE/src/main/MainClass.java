package main;


import helpers.Parser;

import java.util.List;

public class MainClass {

	public static void main(String[] args){
		
		List<String> lst = Parser.parseWords("C:\\wordfile.txt");
		
		Dictionary dictionary = new Dictionary();
		
		dictionary.addWord(lst);
		dictionary.printAll();
	
	}
	
}