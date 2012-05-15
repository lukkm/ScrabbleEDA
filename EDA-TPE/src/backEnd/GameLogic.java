package backEnd;

import frontEnd.GameFrame;
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
	private Deque<Step> stepStack = new LinkedList<Step>();
	private Set<Set<Letter>> previousBoards = new HashSet<Set<Letter>>();
	private boolean foundSolution = false;
	private boolean hasFinished = false;
	private Board bestSolution;
	private GameFrame visual;
	private long startTime;
	private long maxTime;
	private int count;

	public GameLogic(Dictionary dictionary, HandLetters letters,
			GameFrame visual, int maxTime) {
		this.dictionary = dictionary;
		this.letters = letters;
		this.visual = visual;
		this.maxTime = (long) (maxTime*1000);
		this.startTime = System.currentTimeMillis();
	}

	public Set<Letter> startGame() {
		Board board = new Board(dictionary);
		this.bestSolution = board;
		calculateStep(board);
		bestSolution.print();
		return bestSolution.getLettersList();
	}

	public void calculateStep(Board board) {
		if (maxTime != 0 && System.currentTimeMillis() - this.startTime >= this.maxTime) {
			this.hasFinished = true;
		}
		if (letters.isEmpty()) {
			// board.print();
			this.bestSolution = board;
//			foundSolution = true;
			// System.out.println(count);
			return;
		}
		List<String> wordsList;
		List<Letter> charList = board.getAvailableLetters();
		if (charList.isEmpty()) {
			wordsList = dictionary.filterWords(letters.getLetters());
			// Cambiarlo despues
			if (wordsList.isEmpty()) {
				return;
			}
			// Hasta aca
			locateAllWords(wordsList, board);
			if (foundSolution || hasFinished)
				return;
		} else {
			boolean isFinal = true;
			for (Letter l : charList) {
				letters.putLetter(l.getValue());
				int maxLength = Math.max(l.getX() * l.getRotation().getX(),
						l.getY() * l.getRotation().getY()) + 1;
				wordsList = dictionary.filterWordsWith(letters.getLetters(),
						l.getValue(), maxLength);
				letters.takeLetter(l.getValue());
				if (!wordsList.isEmpty()) {
					isFinal = false;
					locateAllWordsIn(wordsList, l, board);
					if (foundSolution || hasFinished)
						return;
				}
			}
			if (isFinal)
				isSolution(board);
		}
	}

	private void locateAllWords(List<String> wordsList, Board board) {
		for (String s : wordsList) {
			for (int i = 0; i < s.length(); i++) {
				Letter l = new Letter(s.charAt(i), 7, 7, Rotation.HORIZONTAL);
				takeStep(s, l, board, i, true);
				if (foundSolution || hasFinished)
					return;
			}
		}
	}

	private void locateAllWordsIn(List<String> wordsList, Letter l, Board board) {
		for (String s : wordsList) {
			for (int i = 0; i < s.length(); i++) {
				if (s.charAt(i) == l.getValue()) {
					takeStep(s, l, board, i, false);
					if (foundSolution || hasFinished)
						return;
				}
			}
		}
	}

	private void takeStep(String word, Letter letter, Board board,
			int charPosition, boolean firstStep) {
		List<Letter> locatedLetters = new ArrayList<Letter>(7);
		Board newBoard = locateWord(board, word, letter, charPosition,
				locatedLetters);
		// la magia se movio un par de lineas para arriba
		if (newBoard == null)
			return;
		if (visual != null){
			try{
				Thread.sleep(50);
			}catch (Exception e){
				return;
			}
			visual.printBoard(newBoard);
		}
		if (!previousBoards.add(newBoard.getLettersList())) {
			return;
		}
		// y termina aca, dos lineas...toma
		stepStack.push(new Step(locatedLetters, word, letters, firstStep,
				letter, charPosition));
		calculateStep(newBoard);
		if (foundSolution || hasFinished)
			return;
		stepStack.pop().refreshLetters(letters);
	}

	private Board locateWord(Board board, String word, Letter l,
			int letterPosition, List<Letter> locatedLetters) {
		Board newBoard = new Board(board, dictionary, locatedLetters);
		if (newBoard.addWord(word, l, letterPosition))
			return newBoard;
		isSolution(board);
		return null;
	}

	private void isSolution(Board board) {
		if (this.bestSolution.getBoardScore() < board.getBoardScore())
			this.bestSolution = board;
	}
}
