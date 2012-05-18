package backEnd;

import helpers.StringIterator;

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
		for (int i = 0; i < 26 ; i++)
			if (letterUsed[i] < letters[i]) {
				if (letterUsed[i] == 0) {
					this.checkSum -= letters[i];
					letters[i] = 0;
				}
				else {
					int difference = letters[i] - letterUsed[i] + 1;
					eraseLetters(i, difference, wordsList);
				}
			}
	}
	
	private void eraseLetters(int charIndex, int difference, List<String> wordsList) {
		int maxTimes = 0;
		for (String s : wordsList) {
			if (s.indexOf('A' + charIndex) != -1) {
				int aux = times(s, difference);
				if (aux > maxTimes)
					maxTimes = aux;
			}
		}
		letters[charIndex] -= (difference - maxTimes);
		this.checkSum -= (difference - maxTimes);
	}
	
	private int times(String word, int difference) {
		StringIterator stringIt = new StringIterator(word);
		int[] letterAppearences = new int[26];
		while (stringIt.hasNext())
			letterAppearences[stringIt.next() - 'A']++;
		int count = difference;
		boolean flag = true;
		for (int i = 0 ; i < 26 && flag ; i++) {
			if (letterAppearences[i] != 0 && letters[i] != 0) {
				int aux = (int) (letters[i] / letterAppearences[i]);
				if ( aux < count)
					count = aux;
			}
		}
		return count;
	}
}
