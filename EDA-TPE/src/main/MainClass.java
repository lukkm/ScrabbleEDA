package main;

import frontEnd.GameFrame;
import helpers.ArgumentSelector;
import helpers.CharValues;
import helpers.FileException;
import helpers.IOHandler;

import java.util.List;
import java.util.Set;

import backEnd.Dictionary;
import backEnd.GameLogic;
import backEnd.HandLetters;
import backEnd.Letter;
import backEnd.VisualOperator;

public class MainClass {

	public static void main(String[] args){
		
		IOHandler handler = new IOHandler();
		
		ArgumentSelector arguments = null;
		try {
			arguments = new ArgumentSelector(args);
		} catch (Exception e) {
			System.out.println("Argumentos invalidos!");
			return;
		}
		
		long startTime = System.currentTimeMillis();
		
		List<String> list;
		HandLetters letters;
		try {
			letters = new HandLetters(handler.getHandLetters("C:\\Pruebas\\archivos\\" + arguments.getLettersFile()));
			list = handler.getWords("C:\\Pruebas\\archivos\\" + arguments.getDictionaryFile());
		} catch (FileException e) {
			System.out.println(e);
			return;
		}

				
		GameFrame gameFrame = generateGameFrame(arguments.isVisual());
		Dictionary dictionary = new Dictionary(new CharValues());
		dictionary.addWords(list);
		GameLogic game = new GameLogic(dictionary, letters, new VisualOperator(gameFrame), arguments.getMaxTime());		
		
		generateOutputFile(game.startGame(), handler, arguments.getOutputFile());
		
		System.out.println("TIEMPO: " + (System.currentTimeMillis() - startTime));
		
		if (gameFrame != null)
			gameFrame.dispose();
		
	}
	
	private static void generateOutputFile(Set<Letter> out, IOHandler handler, String filename) {
		handler.printSolution(out, "C:\\Pruebas\\archivos\\" + filename);
	}
	
	private static GameFrame generateGameFrame(boolean isVisual) {
		if (isVisual)
			return new GameFrame();
		return null;
	}
}
