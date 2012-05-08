package main;


import java.util.Collection;
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
		if (!sons.containsKey(value))
			sons.put(value, new Node(value));
		return sons.get(value);
	}
	
	public void print() {
		
		System.out.println(value);
		Collection <Node> col = sons.values();
		if (col == null)
			return;
		for (Node e : col)
			e.print();
		
	}
}
