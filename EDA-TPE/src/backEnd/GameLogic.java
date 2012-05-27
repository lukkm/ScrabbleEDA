package backEnd;

import helpers.Rotation;
import helpers.StringIterator;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class GameLogic {

	private Dictionary dictionary;
	private HandLetters letters;
	private Board bestSolution;
	private VisualOperator visualOp;
	private Deque<Step> stepStack = new LinkedList<Step>();
	private Set<Set<Letter>> previousBoards = new HashSet<Set<Letter>>();
	private boolean firstStep = true;
	private long startTime, maxTime;


	public GameLogic(Dictionary dictionary, HandLetters letters,
					VisualOperator visualOp, int maxTime) {
		this.dictionary = dictionary;
		this.letters = letters;
		this.visualOp = visualOp;
		this.maxTime = (long) (maxTime * 1000);
		this.startTime = System.currentTimeMillis();
	}
	
	/*
	 * startGame()
	 * 
	 *  Creates an instance of an empty board and calls the recursive method
	 *  calculateStep() which sets the instance variable bestSolution with another 
	 *  board containing the solution having the best score.
	 *  After calling calculateStep(), prints the best solution and returns its 
	 *  letter list. 
	 */

	public Set<Letter> startGame() {
		Board board = new Board(dictionary);
		this.bestSolution = board;
		calculateStep(board);
		bestSolution.print();
		return bestSolution.getLettersList();
	}
	
	/*
	 * calculateStep(Board board)
	 *
	 *  Given one board and the dictionary as instance variable, calculates all
	 *  possible words that can be added in the board that cross at least one 
	 *  of the letters in it. 
	 *  Once this is done, calls the method locateAllWords() which if responsible
	 *  for checking where is it possible to cross the words.
	 *  
	 *  Once a final solution is reached, calls the isSolution() method which 
	 *  determines if the solution found is the better than the other solutions
	 *  found before.
	 *  
	 *  This method also validates that the time hasn't yet exceeded the maximum time
	 *  and that the solution found in this board isn't the best possible solution.
	 *  In any of this 2 cases, returns true, disarming the recursion and returning 
	 *  to the main program with a solution. 
	 *  
	 */

	public boolean calculateStep(Board board) {
		if (maxTime != 0
				&& System.currentTimeMillis() - this.startTime >= this.maxTime) {
			return true;
		}
		if (letters.isEmpty()) {
			this.bestSolution = board;
			return true;
		}
		List<String> wordsList;
		List<Letter> charList = board.getAvailableLetters();
		if (charList.isEmpty()) {
			
			wordsList = dictionary.filterWords(letters.getLetters());
			if (wordsList.isEmpty())
				return false;
			
			if (firstStep) {
				this.letters.eraseLetters(getUnusedLetters(wordsList), wordsList);
				firstStep = false;
			}
			return locateAllWords(wordsList, null, board);
			
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
					if (locateAllWords(wordsList, l, board))
						return true;
				}
			}
			if (isFinal)
				isSolution(board);
		}
		return false;
	}
	
	/*
	 * locateAllWords(List<String> wordsList, Letter l, Board board)
	 * 
	 * Given a list of words, one board and one anchor Letter on it, tries
	 * to put every word of the list in every possible shift crossing the 
	 * Letter that is already set on the board.
	 * Once the shift is calculated, calls the method takeStep() to actually
	 * locate it on the board.
	 * 
	 * Returns true when the best solution is found or the maximum time is exceeded. 
	 */

	private boolean locateAllWords(List<String> wordsList, Letter l, Board board) {
		for (String s : wordsList) {
			for (int i = 0; i < s.length(); i++) {
				if (l == null)
					l = new Letter(s.charAt(i), 7, 7, Rotation.HORIZONTAL);
				if (s.charAt(i) == l.getValue()) 
					if (takeStep(s, l, board, i, false))
						return true;
			}
		}
		return false;
	}
	
	/*
	 * takeStep(String word, Letter letter, Board board,
	 * 		int charPosition, boolean firstStep)
	 *
	 * Given one board, one word, an anchor letter also contained in the word
	 * and its relative position into the word, calls the function locateWord()
	 * which returns a new board with the word positioned if it is possible to put 
	 * it, or null if it's not possible to put the word in that location.
	 * 
	 * Once this is done, adds the new board into a set, in order to filter the 
	 * intermediate solutions that have already been processed. ("PODA" algorithm).
	 * 
	 * Also, calls the method printBoard() of the visual operator, printing each
	 * new board generated.
	 */

	private boolean takeStep(String word, Letter letter, Board board,
			int charPosition, boolean firstStep) {
		List<Letter> locatedLetters = new ArrayList<Letter>(7);
		Board newBoard = locateWord(board, word, letter, charPosition,
				locatedLetters);
		if (newBoard == null)
			return false;
		
		visualOp.printBoard(newBoard);
		
		if (!previousBoards.add(newBoard.getLettersList())) {
			return false;
		}
		
		stepStack.push(new Step(locatedLetters, word, letters, firstStep,
				charPosition));
		boolean ret = calculateStep(newBoard);
		stepStack.pop().refreshLetters(letters);
		return ret;
	}
	
	/*
	 * locateWord(Board board, String word, Letter l, 
	 * 		int letterPosition, List<Letter> locatedLetters)
	 * 
	 * Given one board, one word, an anchor letter also contained in the word
	 * and its relative position into the word, clones the board and calls the
	 * method addWord() in the new board, in order to add to it the word anchored to
	 * the letter received by parameter.
	 * 
	 * If the word can't be added to the board, checks with the method isSolution()
	 * if the score of the new board is better than the previous one.
	 * 
	 */

	private Board locateWord(Board board, String word, Letter l,
			int letterPosition, List<Letter> locatedLetters) {
		Board newBoard = board.cloneBoard(locatedLetters);
		if (newBoard.addWord(word, l, letterPosition))
			return newBoard;
		isSolution(board);
		return null;
	}

	private void isSolution(Board board) {
		if (this.bestSolution.getBoardScore() < board.getBoardScore())
			this.bestSolution = board;
	}
	
	private int[] getUnusedLetters(List<String> wordsList) {
		int[] letterUsed = new int[26];
		for (String s : wordsList) {
			StringIterator itString = new StringIterator(s);
			while (itString.hasNext()) {
				letterUsed[itString.next() - 'A']++;
			}
		}
		return letterUsed;
	}
}
