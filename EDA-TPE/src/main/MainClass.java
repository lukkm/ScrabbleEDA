package main;


import helpers.Parser;

import java.util.ArrayList;
import java.util.List;

public class MainClass {

	public static void main(String[] args){
		
		List<String> lst = Parser.parseWords("C:\\wordfile.txt");

//		List<String> wordsList = Parser.parseWords("archivo.txt");
		List<String> wordsList = new ArrayList<String> ();
		
		wordsList.add("JUANJO");
		wordsList.add("JUASA");
		wordsList.add("JUASE");
		wordsList.add("CARETA");
		wordsList.add("LUKI");
		
		Dictionary dictionary = new Dictionary();
		
		dictionary.addWord(lst);
		dictionary.printTree('J' - 'A');
	
	}
	
}