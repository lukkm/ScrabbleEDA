package main;


import helpers.CharValues;
import helpers.Parser;

import java.util.List;

import backEnd.Dictionary;

public class MainClass {

	public static void main(String[] args){
		
		CharValues scores = new CharValues();
		
		List<String> lst = Parser.parseWords("C:\\wordfile.txt");
		
		Dictionary dictionary = new Dictionary(scores);
		
		dictionary.addWord(lst);
		dictionary.printAll();
//		dictionary.printAppearences();
		System.out.println(dictionary.getAppearencesWith('A'));
		
	}
	
}
