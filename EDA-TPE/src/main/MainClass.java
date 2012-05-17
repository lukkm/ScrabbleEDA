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
import backEnd.VisualOperator;

public class MainClass {

	public static void main(String[] args){
		
		CharValues scores = new CharValues();
		Parser parser = new Parser();
		
		List<String> list = null;
		if (args.length >= 1)
			list = parser.parseWords("C:\\Pruebas\\archivos\\" + args[0]);
		
		HandLetters letters = null;
		if (args.length >= 2)
			letters = new HandLetters(parser.parseLetters("C:\\Pruebas\\archivos\\" + args[1]));
		
		GameFrame gameFrame = null;
		if (args.length >= 4)
			gameFrame = setGameFrame(args[3]);
		
		long a = System.currentTimeMillis();
		
		Dictionary dictionary = new Dictionary(scores);
		dictionary.addWords(list);
		
		int maxTime = 0;
		if (args.length >= 6)
			maxTime = getMaxTime(args[4], args[5]);
		
		GameLogic game = new GameLogic(dictionary, letters, new VisualOperator(gameFrame), maxTime);
		
		Set<Letter> out = game.startGame();
		
		String fileName = "out.txt";
		if (args.length >= 3)
			fileName = args[2];
		parser.printSolution(out, "C:\\Pruebas\\archivos\\" + fileName);		
			
		System.out.println("TIEMPO: " + (System.currentTimeMillis() - a));
		
		if (gameFrame != null)
			gameFrame.dispose();
		
	}
	
	private static GameFrame setGameFrame(String arg) {
		GameFrame gameFrame = null;
		if (arg != null && arg.equals("[-visual]")) {
			gameFrame = new GameFrame();
			gameFrame.setVisible(true);
		}
		return gameFrame;
	}
	
	private static int getMaxTime(String arg1, String arg2) {
		if (arg1 != null) {
			if (arg1.substring(0, 9).equals("[-maxtime")) {
				return Integer.valueOf(arg2.substring(0, arg2.length()-1));
			}
			throw new IllegalArgumentException();
		}
		return 0;
	}
}
