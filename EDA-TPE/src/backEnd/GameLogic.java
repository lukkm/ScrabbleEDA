package backEnd;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class GameLogic {

	private Dictionary dictionary;
	private HandLetters letters;
	private Deque<Step> stepStack = new LinkedList<Step> ();
	
	public GameLogic(Dictionary dictionary, HandLetters letters) {
		this.dictionary = dictionary;
		this.letters = letters;
	}
	
	public void startGame(){
		Board board = new Board();
		calculateStep(board);
	}
	
	public void calculateStep(Board board) {
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
		}
		else {
			boolean isFinal = true;
			for (Letter l : board.getAvailableLetters()) {
				wordsList = dictionary.filterWordsWith(letters.getLetters(), l.getValue());
				if (!wordsList.isEmpty())
					isFinal = false;
					locateAllWordsIn(wordsList, l, board);
			}
			if (isFinal)
				board.print();
		}
	}
	
	private void locateAllWords(List<String> wordsList, Board board) {
		for (String s : wordsList) {
			for (int i = 0 ; i < s.length() ; i++) {
				Letter l = new Letter(s.charAt(i), 7, 7, Rotation.HORIZONTAL);
				takeStep(s, l, board, i, true);
			}
			
		}
	}
	
	private void locateAllWordsIn(List<String> wordsList, Letter l, Board board){
		for (String s : wordsList) {
			for (int i = 0 ; i < s.length() ; i++) {
				if (s.charAt(i) == l.getValue()) { 
					takeStep(s, l, board, i, false);
				}
			}
		}
	}
	
	private void takeStep(String word, Letter letter, Board board, int charPosition, boolean firstStep) {
		Board newBoard = locateWord(board, word, letter, charPosition);
		if (newBoard == null)
			return;
		stepStack.push(new Step(letter.getValue(), word, letters.getLetters(), firstStep));
		calculateStep(newBoard);
		stepStack.pop().refreshLetters(letters.getLetters());
	}
	
	private Board locateWord(Board board, String word, Letter l, int letterPosition) {
		
		Board newBoard = new Board(board);
		if (newBoard.addWord(word, l, letterPosition))
			return newBoard;
		return null;
	}
}
