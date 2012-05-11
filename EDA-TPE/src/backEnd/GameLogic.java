package backEnd;

import helpers.Rotation;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class GameLogic {

	private Dictionary dictionary;
	private HandLetters letters;
	private Deque<Step> stepStack = new LinkedList<Step> ();
	private Set<Board> previousBoards = new HashSet<Board> ();
	private boolean foundSolution = false;
	private Board bestSolution;
	private int count = 0;
	
	public GameLogic(Dictionary dictionary, HandLetters letters) {
		this.dictionary = dictionary;
		this.letters = letters;
	}
	
	public void startGame(){
		Board board = new Board(dictionary);
		this.bestSolution = board;
		calculateStep(board);
		bestSolution.print();
		System.out.println(count);
	}
	
	public void calculateStep(Board board) {
		count++;
		if (letters.isEmpty()){
//			board.print();
			this.bestSolution = board;
			foundSolution = true;
			System.out.println(count);
			return;
		}
		List<String> wordsList;
		List<Letter> charList = board.getAvailableLetters();
		if (charList.isEmpty()) {
			wordsList = dictionary.filterWords(letters.getLetters());
			//Cambiarlo despues
			if (wordsList.isEmpty()){
				return;
			}
			//Hasta aca
			locateAllWords(wordsList, board);
			if (foundSolution)
				return;
		}
		else {
			boolean isFinal = true;
			for (Letter l : charList) {
				letters.putLetter(l.getValue());
				int maxLength = Math.max(l.getX() * l.getRotation().getX(), l.getY() * l.getRotation().getY()) + 1;
				wordsList = dictionary.filterWordsWith(letters.getLetters(), l.getValue(), maxLength);
				letters.takeLetter(l.getValue());
				if (!wordsList.isEmpty()){
					isFinal = false;
					locateAllWordsIn(wordsList, l, board);
					if (foundSolution)
						return;
				}
			}
			if (isFinal) {
				isSolution(board);
			}
		}
	}
	
	private void locateAllWords(List<String> wordsList, Board board) {
		for (String s : wordsList) {
			for (int i = 0 ; i < s.length() ; i++) {
				Letter l = new Letter(s.charAt(i), 7, 7, Rotation.HORIZONTAL);
				takeStep(s, l, board, i, true);
				if (foundSolution)
					return;
			}
			
		}
	}
	
	private void locateAllWordsIn(List<String> wordsList, Letter l, Board board){
		for (String s : wordsList) {
			for (int i = 0 ; i < s.length() ; i++) {
				if (s.charAt(i) == l.getValue()) { 
					takeStep(s, l, board, i, false);
					if (foundSolution)
						return;
				}
			}
		}
	}
	
	private void takeStep(String word, Letter letter, Board board, int charPosition, boolean firstStep) {
		Board newBoard = locateWord(board, word, letter, charPosition);
		// la magia se movio un par de lineas para arriba
		if (!previousBoards.add(newBoard))
			return;
		// y termina aca, dos lineas...toma
		if (newBoard == null)
			return;
		stepStack.push(new Step(letter.getValue(), word, letters, firstStep));
		calculateStep(newBoard);
		if (foundSolution)
			return;
		stepStack.pop().refreshLetters(letters);
	}
	
	private Board locateWord(Board board, String word, Letter l, int letterPosition) {
		
		Board newBoard = new Board(board, dictionary);
		if (newBoard.addWord(word, l, letterPosition))
			return newBoard;
		isSolution(board);
		return null;
	}
	
	private void isSolution(Board board) {
		if (this.bestSolution.getBoardScore() < board.getBoardScore())
			this.bestSolution = board;
//		board.print();
	}
}
