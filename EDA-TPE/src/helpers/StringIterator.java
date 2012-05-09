package helpers;

public class StringIterator {
	
	private String word;
	private int index = 0;
	
	public StringIterator(String word){
		this.word = word;
	}
	
	public boolean hasNext(){
		return (index < word.length());	
	}
	
	public Character next(){
		if (!hasNext())
			return null;
		return word.charAt(index++);
	}

}
