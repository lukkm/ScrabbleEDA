package backEnd;

public class HandLetters {

	int[] letters = new int[26];
	
	public HandLetters(String letters){
		
		for(char c : letters.toUpperCase().toCharArray()){
			this.letters[c - 'A']++;
		}
		
	}
	
	public int[] getLetters(){
		return letters;
	}
	
}
