package backEnd;

import helpers.CharValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Dictionary {

	private Node[] trees = new Node[26];
	private Map<Character, List<Node>> charAppearences = new HashMap<Character, List<Node>> ();
	private CharValues scores;
	
	public Dictionary(CharValues scores){
		this.scores = scores;
	}
	
	public void printAll(){
		for(Node n : trees){
			if (n != null)
				n.printWords();
		}
	}
	
	public void printAppearences() {
		System.out.println(charAppearences);
	}
	
	public List<String> getAppearencesWith(Character value) {
		List<String> newList = new ArrayList<String> ();
		for (Node n: charAppearences.get(value)) {
			n.getWords(newList);
		}
		return newList;
	}
	
	public void addWord(List<String> wordsList) {
		for (String word : wordsList)
			addWord(word);
		updateAppearences();
	}
	
	private void addWord(String word) {
		// cambiar
		char[] wordArray = word.toCharArray();
		List<Character> wordList = new ArrayList<Character> ();
		for (char e : wordArray)
			wordList.add(e);
		Iterator<Character> it = wordList.iterator();
		// hasta aca
		
		Character firstChar = it.next();
		
		int offSet = firstChar - 'A';
		
		if (trees[offSet] == null)
			trees[offSet] = new Node(firstChar, firstChar.toString(), scores.getScores()[firstChar-'A']);
		trees[offSet].addWord(it);
	}

	private void updateAppearences(){
		for (char i = 'A'; i <= 'Z'; i++){
			charAppearences.put(i, new ArrayList<Node> ());
		}
		
		for (Character c : charAppearences.keySet()){
			for (Node n: trees) {
				if (n != null)
					n.getAppearencesOf(c, charAppearences.get(c));
			}
		}
			
	}
	
	private class Node {

		Character value;
		Node[] sons = new Node[26];
		boolean end = false;
		String word;
		int score;
		
		public Node(Character value, String word, int score) {
			this.value = value;
			this.word = word;
			this.score = score;
		}
		
		public void addWord(Iterator<Character> it){
			if (it.hasNext())
				getSon(it.next()).addWord(it);
			else
				end = true;
			return;
		}
		
		public Node getSon(Character value) {
			int offSet = value - 'A';
			if (sons[offSet] == null){
				sons[offSet] = new Node(value, word + value, score + scores.getScores()[value - 'A']);
			}
			return sons[offSet];
		}
		
		public void printWords(){
			if (end)
				System.out.println(word + " " + score);
			for (Node n : sons)
				if (n != null)
					n.printWords();
		}
		
		public void getAppearencesOf(Character value, List<Node> list) {
			
			if (this.value.equals(value)) {
				list.add(this);
				return;
			}
			for (Node n: sons) {
				if (n != null)
					n.getAppearencesOf(value, list);
			}
			
		}
		
		public void getWords(List<String> list) {
			if (end)
				list.add(word);
			for (Node n: sons) {
				if (n != null)
					n.getWords(list);
			}
		}
		
		public String toString(){
			return "Nodo con: " + value + "//";
		}
	
	}
	
}
