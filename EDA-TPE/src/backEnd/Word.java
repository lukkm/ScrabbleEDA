package backEnd;

import java.util.HashMap;
import java.util.Map;

public class Word {
	
	String value;
	Map<Character, Integer> letterAppearences = new HashMap<Character, Integer> ();
	
	public Word(String value) {
		this.value = value;
		generateLetterAppearences();
	}
	
	private void generateLetterAppearences(){
		for (Character c: value.toCharArray()) {
			if (letterAppearences.containsKey(c))
				letterAppearences.put(c, letterAppearences.get(c) + 1);
			else
				letterAppearences.put(c, 1);
		}	
	}
	
	public String getWord() {
		return this.value;
	}
	
	public int getAppearencesOf(Character c){
		return letterAppearences.get(c);
	}

}
