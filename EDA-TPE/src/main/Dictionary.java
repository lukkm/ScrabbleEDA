package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Dictionary {

	Map<Character, Node> trees = new HashMap<Character, Node> ();
	
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
		Node tree = trees.get(firstChar);
		
		if (tree == null)
			tree = trees.put(firstChar, new Node(firstChar));
		tree.addWord(it);
	}
	
}
