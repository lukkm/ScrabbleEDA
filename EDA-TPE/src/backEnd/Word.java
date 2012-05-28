package backEnd;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Word {
	
	String value;
	Map<Character, Integer> letterAppearences = new HashMap<Character, Integer> ();
	
	public Word(String value) {
		this.value = value;
		generateLetterAppearences();
	}
	
	/*
	 * generateLetterAppearences()
	 * 
	 * Generates a map with the amount of appearances of each character in the word
	 * and sets the instance variable letterApeearences with it.
	 */
	
	private void generateLetterAppearences(){
		for (Character c: value.toCharArray()) {
			if (letterAppearences.containsKey(c))
				letterAppearences.put(c, letterAppearences.get(c) + 1);
			else
				letterAppearences.put(c, 1);
		}	
	}
	
	/*
	 * getAppearencesOf(Character c)
	 * 
	 * Gets the amount of appearances of the character received by parameter in the word.
	 */
	
	public int getAppearencesOf(Character c){
		return letterAppearences.get(c);
	}
	
	/*
	 * getChars()
	 * 
	 * Returns a set with all the characters that appear in the word.
	 */
	
	public Set<Character> getChars(){
		return letterAppearences.keySet();
	}

	public String getWord() {
		return this.value;
	}
}
