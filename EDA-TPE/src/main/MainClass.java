package main;


import frontEnd.GameFrame;
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
		Parser parser = new Parser();
		List<String> list = parser.parseWords("C:\\Pruebas\\wordfileE.txt");
		HandLetters letters = new HandLetters(parser.parseLetters("C:\\Pruebas\\lettersE.txt"));
		GameFrame gameFrame = new GameFrame();
		
		gameFrame.setVisible(true);
		
		long a = System.currentTimeMillis();
		
		Dictionary dictionary = new Dictionary(scores);
		dictionary.addWords(list);
		
		GameLogic game = new GameLogic(dictionary, letters, gameFrame);
		Set<Letter> out = game.startGame();
		parser.printSolution(out);		
			
		System.out.println("TIEMPO: " + (System.currentTimeMillis() - a));
		
	}
	
}
