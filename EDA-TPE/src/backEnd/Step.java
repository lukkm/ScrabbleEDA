package backEnd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Step {

	private Map<Character, Integer> lettersUsed = new HashMap<Character, Integer> ();
	private Letter boardLetterUsed;
	private int charPosition;
	List<Letter> locatedLetters;
	private boolean firstStep;
	private String word;
	
	public Step(List<Letter> locatedLetters, String word, HandLetters letters, boolean firstStep, Letter boardLetterUsed, int charPosition) {
		this.firstStep = firstStep;
		this.word = word;
		this.boardLetterUsed = boardLetterUsed;
		this.locatedLetters = locatedLetters;
		this.charPosition = charPosition;
		for (char c : word.toCharArray()) {
			letters.takeLetter(c);
			int aux = 0;
			if (lettersUsed.containsKey(c))
				aux = lettersUsed.get(c);
			lettersUsed.put(c, aux + 1);
		}	
		if (!firstStep) {
			for (Letter l : locatedLetters)
				letters.putLetter(l.getValue());
		}
	}
	
	public Letter getBoardLetterUsed() {
		return this.boardLetterUsed;
	}
	
	public int getCharPosition() {
		return this.charPosition;
	}
	
	public List<Letter> getLocatedLetters() {
		return this.locatedLetters;
	}
	
	public String getWord() {
		return this.word;
	}
	
	public void refreshLetters(HandLetters letters) {
		for (Character c : lettersUsed.keySet()) {
			letters.putLetter(c, lettersUsed.get(c));
		}
		if (!firstStep){
			for (Letter l : locatedLetters)
				letters.takeLetter(l.getValue());
		}
	}
}
