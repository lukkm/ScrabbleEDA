package backEnd;

import helpers.CharValues;
import helpers.StringIterator;

import java.util.ArrayList;
import java.util.HashMap;
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
	
		StringIterator it = new StringIterator(word);		
		Character firstChar = it.next();
		
		int offSet = firstChar - 'A';
		
		if (trees[offSet] == null)
			trees[offSet] = new Node(firstChar, "", 0);
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
	
	
	public List<String> filterWordsWith(int[] letters, char c){
		
		List<String> lstAns = new ArrayList<String>();
		
		for(Node n : charAppearences.get(c)){
			if (n.validateNode(letters))
				n.getFilterWords(letters, lstAns);
		}
		
		return lstAns;
	}
	
	
	public List<String> filterWords(int[] letters){
		
		List<String> lstAns = new ArrayList<String>();
		
		for(Node n : trees){
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
		int score;
		
		public Node(Character value, String previousWord, int previousScore) {
			this.value = value;
			this.word = new Word(previousWord + value);
			this.score = previousScore + scores.getScores()[value - 'A'];
		}
		
		public boolean validateNode(int[] letters){
			for (Character c : word.getChars()){
				if (letters[c - 'A'] < word.getAppearencesOf(c));
					return false;
			}
			return true;
		}
		
		public void addWord(StringIterator it){
			if (it.hasNext())
				getSon(it.next()).addWord(it);
			else {
				end = true;
			}
			return;
		}
		
		public Node getSon(Character value) {
			int offSet = value - 'A';
			if (sons[offSet] == null){
				sons[offSet] = new Node(value, word.getWord(), score);
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
		
		public void getFilterWords(int[] letters, List<String> lst){
			if (word.getAppearencesOf(value) <= letters[value - 'A']){
				if (end)
					lst.add(word.getWord());
				for(Node n : sons)
					if (n != null)
						n.getFilterWords(letters, lst);
			}
		}
		
		public void getWords(List<String> list) {
			if (end)
				list.add(word.getWord());
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
