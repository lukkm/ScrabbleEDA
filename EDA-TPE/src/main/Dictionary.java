package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Dictionary {

	Node[] trees = new Node[26];
	
	public void printTree(int charPrint){
		trees[charPrint].print();
	}
	
	public void printAll(){
		for(Node n : trees){
			if (n != null)
				n.printWords();
		}
	}
	
	public void addWord(List<String> wordsList) {
		for (String word : wordsList)
			addWord(word);
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
	}
	
}
