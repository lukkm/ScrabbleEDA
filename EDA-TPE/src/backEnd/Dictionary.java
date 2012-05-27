package backEnd;

import helpers.CharValues;
import helpers.StringIterator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dictionary {

	private Node[] trees = new Node[26];
	private Map<Character, List<Node>> charAppearences = new HashMap<Character, List<Node>>();
	private CharValues scores;
	
	public Dictionary(CharValues scores) {
		this.scores = scores;
	}
	
	/*
	 * getScore(Letter l)
	 * 
	 * Returns the score of a letter.
	 */

	public int getScore(Letter l) {
		return scores.getScore(l.getValue());
	}
	
	/*
	 * containsWord(String s)
	 * 
	 * Given a word, returns true if its contained in the dictionary, false if not.
	 */

	public boolean containsWord(String s) {
		StringIterator it = new StringIterator(s);
		if (!it.hasNext())
			return false;
		char firstChar = it.next();
		if (trees[firstChar - 'A'] == null)
			return false;
		return trees[firstChar - 'A'].containsWord(it);
	}
	
	/*
	 * addWords(List<String> wordsList)
	 * 
	 * Given a list of words, adds it to the dictionary.
	 */
	
	public void addWords(List<String> wordsList) {
		for (String word : wordsList)
			addWord(word);
		updateAppearences();
	}
	
	/*
	 * addWord(String word)
	 * 
	 * Given a word, adds it to the dictionary.
	 */

	private void addWord(String word) {

		StringIterator it = new StringIterator(word);
		Character firstChar = it.next();

		int offSet = firstChar - 'A';

		if (trees[offSet] == null)
			trees[offSet] = new Node(firstChar, "");
		trees[offSet].addWord(it);
	}
	
	/*
	 * updateAppearences()
	 * 
	 * Updates the instance variable charAppearences with the list of every first
	 * appearance of every letter in a word.
	 */

	private void updateAppearences() {
		for (char i = 'A'; i <= 'Z'; i++) {
			charAppearences.put(i, new ArrayList<Node>());
		}

		for (Character c : charAppearences.keySet()) {
			for (Node n : trees) {
				if (n != null)
					n.getAppearencesOf(c, charAppearences.get(c));
			}
		}
	}

	/*
	 * filterWordsWith(int[] letters, char c)
	 * 
	 * Wrapper function for filterWordsWith(int[] letters, char c, int maxLength),
	 * calls it with maxLength on -1.
	 */
	
	public List<String> filterWordsWith(int[] letters, char c) {
		return filterWordsWith(letters, c, -1);
	}
	
	/*
	 * filterWordsWith(int[] letters, char c, int maxLength)
	 * 
	 * Returns a list of all the words from the dictionary that can be formed with
	 * the letters in the array with a specified contained character and with a 
	 * maximum length.
	 */

	public List<String> filterWordsWith(int[] letters, char c, int maxLength) {

		List<String> lstAns = new ArrayList<String>();

		for (Node n : charAppearences.get(c)) {
			if ((maxLength == -1 || n.word.value.length() <= maxLength)
					&& n.validateNode(letters))
				n.getFilterWords(letters, lstAns);
		}

		return lstAns;
	}
	
	/*
	 * filterWords(int[] letters)
	 * 
	 * Returns a list of all the words from the dictionary that can be formed with
	 * the letters in the array.
	 */

	public List<String> filterWords(int[] letters) {

		List<String> lstAns = new ArrayList<String>();

		for (Node n : trees) {
			if (n != null)
				n.getFilterWords(letters, lstAns);
		}

		return lstAns;

	}

	private class Node {

		Character value;
		Node[] sons = new Node[26];
		boolean end = false;
		Word word;

		public Node(Character value, String previousWord) {
			this.value = value;
			this.word = new Word(previousWord + value);
		}
		
		public void printWords() {
			if (end)
				System.out.println(word.value);
			for (Node n : sons)
				if (n != null)
					n.printWords();
		}

		public boolean validateNode(int[] letters) {
			for (Character c : word.getChars()) {
				if (letters[c - 'A'] < word.getAppearencesOf(c))
					return false;
			}
			return true;
		}

		public boolean containsWord(StringIterator it) {
			if (!it.hasNext()) {
				if (end)
					return true;
				return false;
			}
			char aux = it.next();
			if (sons[aux - 'A'] == null)
				return false;
			return sons[aux - 'A'].containsWord(it);
		}

		public void addWord(StringIterator it) {
			if (it.hasNext())
				getSon(it.next()).addWord(it);
			else {
				end = true;
			}
			return;
		}

		public Node getSon(Character value) {
			int offSet = value - 'A';
			if (sons[offSet] == null) {
				sons[offSet] = new Node(value, word.getWord());
			}
			return sons[offSet];
		}

		public void getAppearencesOf(Character value, List<Node> list) {

			if (this.value.equals(value)) {
				list.add(this);
				return;
			}
			for (Node n : sons) {
				if (n != null)
					n.getAppearencesOf(value, list);
			}
		}

		public void getFilterWords(int[] letters, List<String> lst) {
			if (word.getAppearencesOf(value) <= letters[value - 'A']) {
				if (end)
					lst.add(word.getWord());
				for (Node n : sons)
					if (n != null)
						n.getFilterWords(letters, lst);
			}
		}

	}

}
