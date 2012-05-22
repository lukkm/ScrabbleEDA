package backEnd;

import java.util.List;

public class HandLetters {

	int[] letters = new int[26];
	int[] differences = new int[26];
	int checkSum;
	
	public HandLetters(String letters){
		
		for(char c : letters.toUpperCase().toCharArray()){
			this.letters[c - 'A']++;
			checkSum++;
		}
	}
	
	public int[] getLetters(){
		return letters;
	}
	
	public boolean isEmpty() {
		return checkSum == 0;
	}
	
	public void takeLetter(char c) {
		letters[c - 'A']--;
		checkSum--;
	}
	
	public void putLetter(char c) {
		putLetter(c, 1);
	}
	
	public void putLetter(char c, int quantity) {
		letters[c - 'A'] += quantity;
		checkSum += quantity;
	}
	
	public void eraseLetters(int[] letterUsed, List<String> wordsList) {
		for (int i = 0; i < 26 ; i++){
			if (letterUsed[i] == 0) {
				this.checkSum -= letters[i];
				letters[i] = 0;
			}
		}
	}
}
