package backEnd;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Board {

	private Letter[][] board = new Letter[15][15];
	private List<Letter> lettersList = new LinkedList<Letter>();
	private static final int[][] primes = new Primes().getPrimes(); 
	private Dictionary dictionary;
	
	public Board(Dictionary dict) {
		this.dictionary = dict;
	}
	
	public Board(Board board, Dictionary dict) {
		this(dict);
		for (Letter l : board.getLettersList()){
			Letter addLetter =  new Letter(l.getValue(), l.getX(), l.getY(), l.getRotation());
			this.board[l.getX()][l.getY()] = addLetter;
			lettersList.add(addLetter);
		}
	}
	
	public List<Letter> getLettersList(){
		return lettersList;
	}
	
	public boolean addWord(String word, Letter locatedLetter, int charPosition){
		int offSetX = charPosition * locatedLetter.getRotation().getX();
		int offSetY = charPosition * locatedLetter.getRotation().getY();
		return setWord(locatedLetter.getX() - offSetX, locatedLetter.getY() - offSetY, word, locatedLetter.getRotation());
	}
	
	public boolean setWord(int startX, int startY, String word, Rotation rot){
		if (startX < 0 || startX > board.length - 1 || startY < 0 || startY > board[0].length - 1)
			return false;
		int length = word.length();
		if (startX + length * rot.getX() > board.length - 1 || startY + length * rot.getY() > board.length - 1)
			return false;
		for (int i = 0; i < length; i++){
			int posX = startX + i * rot.getX();
			int posY = startY + i * rot.getY();
			if (board[posX][posY] != null && board[posX][posY].getValue() != word.charAt(i))
				return false;
			if(!crossCheck(word.charAt(i), posX, posY, rot.change()))
					return false;
		}
		for (int i = 0; i < length; i++){
			int posX = startX + i * rot.getX();
			int posY = startY + i * rot.getY();
			Letter l;
			if (board[posX][posY] == null){
				l = new Letter(word.charAt(i), posX, posY, rot.change());
				lettersList.add(l);
				board[posX][posY] = l;
			}else{
				board[posX][posY].setRotation(Rotation.NONE);
			}
		}
		return true;
	}
	
	private boolean crossCheck(Character c, int x, int y, Rotation rot){
		if (rot == Rotation.NONE)
			return true;
		int upperX = x + rot.getX();
		int lowerX = x - rot.getX();
		int upperY = y + rot.getY();
		int lowerY = y - rot.getY();
		if ((upperX > 14 || upperY > 14 || board[upperX][upperY] == null )&&
				( lowerX < 0 || lowerY < 0 || board[lowerX][lowerY] == null))
			return true;
		String word = c.toString();
		word = getUpperString(x, y, rot, word);
		word = getLowerString(x, y, rot, word);
		return dictionary.containsWord(word);
	}
	
	private String getUpperString(int x, int y, Rotation rot, String currentString){
		if (x > 14 || y > 14)
			return currentString;
		if (board[x][y] == null)
			return currentString;
		return getUpperString(x + rot.getX(), y + rot.getY(), rot, currentString + board[x][y].getValue());
	}
	
	private String getLowerString(int x, int y, Rotation rot, String currentString){
		if (x < 0 || y < 0)
			return currentString;
		if (board[x][y] == null)
			return currentString;
		return getLowerString(x - rot.getX(), y - rot.getY(), rot, board[x][y].getValue() + currentString);
	}
	
	public List<Letter> getAvailableLetters() {
		List<Letter> returnList = new ArrayList<Letter> ();
		for (Letter l : lettersList)
			if(l.getRotation() != Rotation.NONE)
				returnList.add(l);
		return returnList;
	}
	
	public void setPosition(int x, int y, Letter c){
		board[x][y] = c;
	}
	
	public Letter getPosition(int x, int y){
		return board[x][y];
	}
	
	public void print() {
		System.out.println();
		for (Letter[] lY : board) {
			for (Letter lX : lY) {
				if (lX == null)
					System.out.print(" - ");
				else
					System.out.print(" " + lX.getValue() + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public int hashCode() {
		
		int result = 0;
		for (Letter l : lettersList) {
			result += l.hashCode() * primes[l.getX()][l.getY()];
		}
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board other = (Board) obj;
		if (lettersList == null) {
			if (other.lettersList != null)
				return false;
		} else if (!lettersList.equals(other.lettersList))
			return false;
		return true;
	}
}
