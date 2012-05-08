package main;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Node {

	Character value;
	Map<Character, Node> sons = new HashMap<Character, Node> ();
	
	public Node(Character value) {
		this.value = value;
	}
	
	public void addWord(Iterator<Character> it){
		if (it.hasNext())
			getSon(it.next()).addWord(it);
		return;
	}
	
	public Node getSon(Character value) {
		Node auxNode = sons.get(value);
		if (auxNode == null)
			auxNode = sons.put(value, new Node(value));
		return auxNode;
	}
	
}
