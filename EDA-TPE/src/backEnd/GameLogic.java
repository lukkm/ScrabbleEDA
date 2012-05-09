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
		calculateStep(board, letters.getLetters());
	}
	
	public void calculateStep(Board board, int[] letters) {
		List<String> wordsList = new ArrayList<String>();
		List<Letter> charList = board.getLetters();
		if (charList.isEmpty()) {
			wordsList = dictionary.filterWords(letters);
		}
		else {
			for (Letter c : board.getLetters()) {
				wordsList.addAll(dictionary.filterWordsWith(letters, c));
			}
		}
		
		for (String word : wordsList) {
			locateWord(board, word);
		}
	}
	
	private void locateWord(Board board, String word) {
		
		Board newBoard = new Board(board);
		newBoard.addWord(word, startingPoint);
	}
}
