package backEnd;

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
	
	/*
	 * getLetters()
	 * 
	 * Returns the array of available letters.
	 */
	
	public int[] getLetters(){
		return letters;
	}
	
	public boolean isEmpty() {
		return checkSum == 0;
	}
	
	/*
	 * takeLetter(char c)
	 * 
	 * Takes one letter from the array of available letters.
	 */
	
	public void takeLetter(char c) {
		letters[c - 'A']--;
		checkSum--;
	}
	
	/*
	 * putLetter(char c)
	 * 
	 * Puts one letter into the array of available letters.
	 */
	
	public void putLetter(char c) {
		putLetter(c, 1);
	}
	
	/*
	 * putLetter(char c, int quantity)
	 * 
	 * Puts a quantity of letters into the array of available letters.
	 */
	
	public void putLetter(char c, int quantity) {
		letters[c - 'A'] += quantity;
		checkSum += quantity;
	}
	
	/* eraseLetters(int[] letterUsed, List<String> wordsList)
	 * 
	 * Given an array with the information of the letters used, erases that amount
	 * of letters from the array of letters available to use, also refreshing the
	 * checkSum of this array.
	 */
	
	public void eraseLetters(int[] letterUsed) {
		for (int i = 0; i < 26 ; i++){
			if (letterUsed[i] == 0) {
				this.checkSum -= letters[i];
				letters[i] = 0;
			}
		}
	}
}
