package main;


import helpers.CharValues;
import helpers.Parser;

import java.util.List;
import java.util.Map;

import backEnd.Dictionary;

public class MainClass {

	public static void main(String[] args){
		
		CharValues values = new CharValues("C:\\puntos.txt");
		
		Map<Character, Integer> mapa = values.getValues();
		
		List<String> lst = Parser.parseWords("C:\\wordfile.txt");
		
		Dictionary dictionary = new Dictionary();
		
		dictionary.addWord(lst);
		dictionary.printAll();
	
	}
	
}
