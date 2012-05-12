package main;


import helpers.CharValues;
import helpers.Parser;

import java.util.List;

import backEnd.Dictionary;
import backEnd.GameLogic;
import backEnd.HandLetters;

public class MainClass {

	public static void main(String[] args){
		
		CharValues scores = new CharValues();
		
		List<String> lst = Parser.parseWords("C:\\Pruebas\\wordfileE3.txt");
		HandLetters letters = new HandLetters(Parser.parseLetters("C:\\Pruebas\\lettersE.txt"));
		
		long a = System.currentTimeMillis();
		
		Dictionary dictionary = new Dictionary(scores);
		
		dictionary.addWords(lst);
		
		GameLogic game = new GameLogic(dictionary, letters);
		
		game.startGame();
		
		System.out.println("TIEMPO: " + (System.currentTimeMillis() - a));
		
//		dictionary.printAll();
//		dictionary.printAppearences();
//		System.out.println(dictionary.getAppearencesWith('A'));
		
	}
	
}
