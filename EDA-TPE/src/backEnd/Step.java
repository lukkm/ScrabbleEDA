package backEnd;

import java.util.HashMap;
import java.util.Map;

public class Step {

	private Map<Character, Integer> lettersUsed = new HashMap<Character, Integer> ();
	private Character boardLetterUsed;
	private boolean firstStep;
	
	public Step(Character boardLetterUsed, String word, HandLetters letters, boolean firstStep) {
		this.firstStep = firstStep;
		this.boardLetterUsed = boardLetterUsed;
		for (char c : word.toCharArray()) {
			letters.takeLetter(c);
			int aux = 0;
			if (lettersUsed.containsKey(c))
				aux = lettersUsed.get(c);
			lettersUsed.put(c, aux + 1);
		}	
		if (!firstStep)
			letters.putLetter(boardLetterUsed);
	}
	
	public Character getBoardLetterUsed() {
		return this.boardLetterUsed;
	}
	
	public void refreshLetters(HandLetters letters) {
		for (Character c : lettersUsed.keySet()) {
			letters.putLetter(c, lettersUsed.get(c));
		}
		if (!firstStep)
			letters.takeLetter(boardLetterUsed);
	}
	
}
