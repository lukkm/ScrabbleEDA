package tests;

import static org.junit.Assert.assertTrue;
import helpers.CharValues;
import helpers.FileException;
import helpers.IOHandler;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import backEnd.Dictionary;
import backEnd.GameLogic;
import backEnd.HandLetters;
import backEnd.Letter;
import backEnd.VisualOperator;

public class TilesTest {

	private GameLogic gameTest;
	private CharValues values = new CharValues();
	private Dictionary dict = new Dictionary(values);
	private IOHandler parser = new IOHandler();

	@Test
	public void timeTest() {
		int expectedTimes[] = {200, 200, 8000, 200, 200, 5000};
		int expectedScores[] = {34, 11, 20, 34, 67, 67};
		
		try {
			dict.addWords(parser.getWords("dictionary.txt"));
		} catch (FileException e) {
			return;
		}
		
		for (int i = 1 ; i < 7 ; i++) {
			HandLetters letters;
			try {
				letters = new HandLetters(parser.getHandLetters("tiles" + i + ".txt"));
			} catch (FileException e) {
				return;
			}
			gameTest = new GameLogic(dict, letters, new VisualOperator(null), expectedTimes[i-1]/1000);
			Set<Letter> out = gameTest.startGame();
			int score = 0;
			for(Letter l : out)
				score += values.getScore(l.getValue());
			assertTrue(expectedScores[i-1] == score);
		}
	}
	
	@Test
	public void pointsTest() {
		int expectedScores[] = {34, 11, 20, 34, 67};
		
		try {
			dict.addWords(parser.getWords("dictionary.txt"));
		} catch (FileException e) {
			return;
		}
		
		for (int i = 1 ; i < 6 ; i++) {
			HandLetters letters;
			try {
				letters = new HandLetters(parser.getHandLetters("tiles" + i + ".txt"));
			} catch (FileException e) {
				return;
			}
			gameTest = new GameLogic(dict, letters, new VisualOperator(null), 0);
			Set<Letter> out = gameTest.startGame();
			int score = 0;
			for(Letter l : out)
				score += values.getScore(l.getValue());
			assertTrue(expectedScores[i-1] == score);
		}
	}

	@Test
	public void parserTest() {
		List<String> wordsList;
		try {
			wordsList = parser.getWords("dictionary.txt");
		} catch (FileException e) {
			System.out.println(e);
			return;
		}
		
		for (String s : wordsList)
			assertTrue(s.matches("[A-Z]{2,7}"));
	}
	
}