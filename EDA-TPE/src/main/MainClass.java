package main;


import helpers.CharValues;
import helpers.Parser;

import java.util.List;

import backEnd.Dictionary;
import backEnd.HandLetters;

public class MainClass {

	public static void main(String[] args){
		
		CharValues scores = new CharValues();
		
		List<String> lst = Parser.parseWords("C:\\wordfile.txt");
		HandLetters letters = new HandLetters(Parser.parseLetters("C:\\letters.txt"));
		
		long a = System.currentTimeMillis();
		
		Dictionary dictionary = new Dictionary(scores);
		
		dictionary.addWord(lst);
		
		System.out.println(dictionary.filterWords(letters.getLetters()));
		System.out.println(dictionary.filterWordsWith(letters.getLetters(), 'A'));
		
		System.out.println(System.currentTimeMillis() - a);
		
//		dictionary.printAll();
//		dictionary.printAppearences();
//		System.out.println(dictionary.getAppearencesWith('A'));
		
	}
	
}
