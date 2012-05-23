package backEnd;

import helpers.Primes;
import helpers.Rotation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board implements Cloneable{

	private final int ROWS = 15, COLS = 15;
	private Letter[][] board = new Letter[ROWS][COLS];
	private Set<Letter> lettersList = new HashSet<Letter>();
	private Dictionary dictionary;
	private int boardScore;
	private List<Letter> locatedLetters;
	
	public Board(Dictionary dict) {
		this.dictionary = dict;
	}
	
	private Board(Board board, Dictionary dict, List<Letter> locatedLetters) {
		this(dict);
		for (Letter l : board.getLettersList())
			addLetter(l.clone());
		this.boardScore = board.boardScore;
		this.locatedLetters = locatedLetters;
	}
	
	public Board cloneBoard(List<Letter> lettersList){
		return new Board(this, dictionary, lettersList);
	}
	
	public void addLetter(Letter addLetter){
		this.board[addLetter.getX()][addLetter.getY()] = addLetter;
		lettersList.add(addLetter);
	}
	
	public Set<Letter> getLettersList(){
		return lettersList;
	}
	
	public int getOffSetX(int startPosition, int cantMoves, Rotation rot){
		return startPosition + cantMoves * rot.getX();
	}
	
	public int getOffSetY(int startPosition, int cantMoves, Rotation rot){
		return startPosition + cantMoves * rot.getY();
	}
	
	
	public boolean addWord(String word, Letter locatedLetter, int charPosition){
		int xPosition = getOffSetX(locatedLetter.getX(), -charPosition, locatedLetter.getRotation());
		int yPosition = getOffSetY(locatedLetter.getY(), -charPosition, locatedLetter.getRotation());
		return setWord(xPosition, yPosition, word, locatedLetter.getRotation());
	}
	
	public boolean setWord(int startX, int startY, String word, Rotation rot){
		int length = word.length();
		if (!validateWordMargins(startX, startY, length, rot))
			return false;
		for (int i = 0; i < length; i++){
			int posX = getOffSetX(startX, i, rot);
			int posY = getOffSetY(startY, i, rot);
			if (board[posX][posY] != null && board[posX][posY].getValue() != word.charAt(i))
				return false;
			if(!verticalCrossCheck(startX, startY, length, rot, word) || !crossCheck(word.charAt(i), posX, posY, rot.change()))
					return false;
		}
		for (int i = 0; i < length; i++){
			int posX = getOffSetX(startX, i, rot);
			int posY = getOffSetY(startY, i, rot);
			Letter l;
			if (board[posX][posY] == null){
				l = new Letter(word.charAt(i), posX, posY, rot.change());
				lettersList.add(l);
				board[posX][posY] = l;
				boardScore += dictionary.getScore(l);
			}else{
				board[posX][posY].setRotation(Rotation.NONE);
				this.locatedLetters.add(board[posX][posY]);
			}
		}
		return true;
	}
	
	private boolean validateWordMargins(int startX, int startY, int length, Rotation rot){
		if (startX < 0 || startX > ROWS -1 || startY < 0 || startY > COLS -1)
			return false;
		if (getOffSetX(startX, length, rot) > ROWS || getOffSetY(startY, length, rot) > COLS)
			return false;
		return true;
	}
	
	private boolean crossCheck(Character c, int x, int y, Rotation rot){
		if (rot == Rotation.NONE)
			return true;
		int upperX = x + rot.getX();
		int lowerX = x - rot.getX();
		int upperY = y + rot.getY();
		int lowerY = y - rot.getY();
		if ((upperX > ROWS-1 || upperY > COLS-1 || board[upperX][upperY] == null )&&
				( lowerX < 0 || lowerY < 0 || board[lowerX][lowerY] == null))
			return true;
		String word = c.toString();
		word = getUpperString(x + rot.getX(), y + rot.getY(), rot, word);
		word = getLowerString(x - rot.getX(), y - rot.getY(), rot, word);
		return dictionary.containsWord(word);
	}
	
	private boolean verticalCrossCheck(int startX, int startY, int length, Rotation rot, String word){
		int upperX = startX + length * rot.getX();
		int lowerX = startX - rot.getX();
		int upperY = startY + length * rot.getY();
		int lowerY = startY - rot.getY();
		if ((lowerX < 0 || lowerY < 0 || board[lowerX][lowerY] == null) && 
				(upperX > 14 || upperY > 14 || board[upperX][upperY] == null))
			return true;
		String aux = word;
		aux = getUpperString(upperX, upperY, rot, aux);
		aux = getLowerString(lowerX, lowerY, rot, aux);
		return dictionary.containsWord(aux);
	}
	
	private String getUpperString(int x, int y, Rotation rot, String currentString){
		if (x > ROWS-1 || y > COLS-1)
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
	
	public int getBoardScore() {
		return this.boardScore;
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
		System.out.println("PUNTOS: " + this.boardScore);
	}
}
