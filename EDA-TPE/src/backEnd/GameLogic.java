package backEnd;

import java.util.ArrayList;
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
		List<Letter> charList = board.getLetters();
		if (charList.isEmpty()) {
			wordsList = dictionary.filterWords(letters.getLetters());
			locateAllWords(wordsList, board);
		}
		else {
			for (Letter l : board.getLetters()) {
				wordsList = dictionary.filterWordsWith(letters.getLetters(), l.getValue());
				locateAllWordsIn(wordsList, l, board);
			}
		}
	}
	
	private void locateAllWords(List<String> wordsList, Board board) {
		for (String s : wordsList) {
			for (char c : s.toCharArray()) {
				Letter l = new Letter(c, 7, 7, Rotation.HORIZONTAL);
				Board newBoard = locateWord(board, s, l);
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
			Board newBoard = locateWord(board, s, l);
			if (newBoard == null)
				return;
			//Resta de las letters
			calculateStep(newBoard);
			//Suma las letters
		}
	}
	
	private Board locateWord(Board board, String word, Letter l, int letterPosition) {
		
		Board newBoard = new Board(board);
		if (newBoard.addWord(word, l, letterPosition))
			return newBoard;
		return null;
	}
}
