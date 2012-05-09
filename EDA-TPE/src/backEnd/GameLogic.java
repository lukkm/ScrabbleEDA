package backEnd;

import java.util.List;

public class GameLogic {

	private Dictionary dictionary;
	private HandLetters letters;
	
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
			locateAllWords(wordsList, board);
		}
		else {
			for (Letter l : board.getAvailableLetters()) {
				wordsList = dictionary.filterWordsWith(letters.getLetters(), l.getValue());
				locateAllWordsIn(wordsList, l, board);
			}
		}
	}
	
	private void locateAllWords(List<String> wordsList, Board board) {
		for (String s : wordsList) {
			for (int i = 0 ; i < s.length() ; i++) {
				Letter l = new Letter(s.charAt(i), 7, 7, Rotation.HORIZONTAL);
				Board newBoard = locateWord(board, s, l, i);
				if (newBoard == null)
					return;
				//Resta de las letters
				calculateStep(newBoard);
				//Suma las letters
			}
			
		}
	}
	
	private void locateAllWordsIn(List<String> wordsList, Letter l, Board board){
		for (String s : wordsList) {
			for (int i = 0 ; i < s.length() ; i++) {
				if (s.charAt(i) == l.getValue()) { 
					Board newBoard = locateWord(board, s, l, i);
					if (newBoard == null)
						return;
					//Resta de las letters
					calculateStep(newBoard);
					//Suma las letters
				}
			}
		}
	}
	
	private Board locateWord(Board board, String word, Letter l, int letterPosition) {
		
		Board newBoard = new Board(board);
		if (newBoard.addWord(word, l, letterPosition))
			return newBoard;
		return null;
	}
}
