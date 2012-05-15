package backEnd;

import helpers.Rotation;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class GameLogic {

	private Dictionary dictionary;
	private HandLetters letters;
	private Deque<Step> stepStack = new LinkedList<Step> ();
	private Set<Set<Letter>> previousBoards = new HashSet<Set<Letter>> ();
	private boolean foundSolution = false;
	private Board bestSolution;
	private Deque<Step> bestStack = new LinkedList<Step> ();
	private int count = 0;
	
	public GameLogic(Dictionary dictionary, HandLetters letters) {
		this.dictionary = dictionary;
		this.letters = letters;
	}
	
	public void startGame(){
		Board board = new Board(dictionary);
		this.bestSolution = board;
		calculateStep(board);
		printSteps();
		bestSolution.print();
		System.out.println("TABLEROS: " + count);
	}
	
	public void calculateStep(Board board) {
		if (letters.isEmpty()){
//			board.print();
			this.bestSolution = board;
//			foundSolution = true;
//			System.out.println(count);
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
		List<Letter> locatedLetters = new ArrayList<Letter>(7);
		Board newBoard = locateWord(board, word, letter, charPosition, locatedLetters);
		// la magia se movio un par de lineas para arriba
		if (newBoard == null)
			return;
		if (!previousBoards.add(newBoard.getLettersList())) {
			return;
		}
		// y termina aca, dos lineas...toma
		stepStack.push(new Step(locatedLetters, word, letters, firstStep, letter, charPosition));
		calculateStep(newBoard);
		if (foundSolution)
			return;
		stepStack.pop().refreshLetters(letters);
	}
	
	private Board locateWord(Board board, String word, Letter l, int letterPosition, List<Letter> locatedLetters) {
		
		Board newBoard = new Board(board, dictionary, locatedLetters);
		if (newBoard.addWord(word, l, letterPosition))
			return newBoard;
		isSolution(board);
		return null;
	}
	
	private void isSolution(Board board) {
		count++;
		if (count % 1000 == 0)
			System.out.println(count);
		if (this.bestSolution.getBoardScore() < board.getBoardScore()) {
			stepStack = cloneStack(stepStack);
			this.bestSolution = board;
		}
	}
	
	private Deque<Step> cloneStack(Deque<Step> stack) {
		Deque<Step> auxStack1 = new LinkedList<Step>();
		Deque<Step> auxStack2 = new LinkedList<Step>();
		while (!stack.isEmpty()) {
			Step elem = stack.pop();
			auxStack1.addFirst(elem);
			auxStack2.addFirst(elem);
		}
		this.bestStack = auxStack2;
		return auxStack1;
	}
	
	private void printSteps() {
		Board auxBoard = new Board(this.dictionary);
		while (!bestStack.isEmpty()) {
			Step aux = bestStack.pop();
			System.out.println(aux.getWord());
//			auxBoard = locateWord(auxBoard, aux.getWord(), 
//					aux.getBoardLetterUsed(), aux.getCharPosition(), aux.getLocatedLetters());
//			auxBoard.print();
		}
	}
}
