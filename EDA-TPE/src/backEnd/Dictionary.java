package backEnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Dictionary {

	Node[] trees = new Node[26];
	Map<Character, List<Node>> charAppearences = new HashMap<Character, List<Node>> ();
	
	public void printAll(){
		for(Node n : trees){
			if (n != null)
				n.printWords();
		}
	}
	
	public void printAppearences() {
		System.out.println(charAppearences);
	}
	
	public void addWord(List<String> wordsList) {
		for (String word : wordsList)
			addWord(word);
		updateAppearences();
	}
	
	private void addWord(String word) {
		char[] wordArray = word.toCharArray();
		List<Character> wordList = new ArrayList<Character> ();
		for (char e : wordArray)
			wordList.add(e);
		Iterator<Character> it = wordList.iterator();
		Character firstChar = it.next();
		
		int offSet = firstChar - 'A';
		
		if (trees[offSet] == null)
			trees[offSet] = new Node(firstChar);
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
		int actualSons = 0;
		
		public Node(Character value) {
			this.value = value;
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
				sons[offSet] = new Node(value);
				actualSons++;
			}
			return sons[offSet];
		}
		
		public void print() {
			System.out.println(value);
			if (actualSons == 0)
				return;
			for (Node e : sons){
				if (e != null)
					e.print();
			}
			
		}
		
		public void printWords(){
			printWords("");
		}
		
		private void printWords(String str){
			String strNew = str + this.value.toString();
			if (end)
				System.out.println(strNew);
			for (Node n : sons)
				if (n != null)
					n.printWords(strNew);
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
		
		public String toString(){
			return "Nodo con: " + value + "//";
		}
	
	}
	
}
