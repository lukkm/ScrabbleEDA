package main;


import helpers.CharValues;
import helpers.Parser;

import java.util.List;
import java.util.Set;

import backEnd.Dictionary;
import backEnd.GameLogic;
import backEnd.HandLetters;
import backEnd.Letter;

public class MainClass {

	public static void main(String[] args){
		
		CharValues scores = new CharValues();
		
		List<String> list = Parser.parseWords("C:\\Pruebas\\wordfileE.txt");
		HandLetters letters = new HandLetters(Parser.parseLetters("C:\\Pruebas\\lettersE.txt"));
		
		long a = System.currentTimeMillis();
		
		Dictionary dictionary = new Dictionary(scores);
		dictionary.addWords(list);
		GameLogic game = new GameLogic(dictionary, letters);
		Set<Letter> out = game.startGame();
		
		
		System.out.println("TIEMPO: " + (System.currentTimeMillis() - a));
		
//		dictionary.printAll();
//		dictionary.printAppearences();
//		System.out.println(dictionary.getAppearencesWith('A'));
		
	}
	
}
