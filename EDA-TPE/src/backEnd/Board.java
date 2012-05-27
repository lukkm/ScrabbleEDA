package backEnd;

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
	
	/*
	 * Board cloneBoard(List<Letter> lettersList)
	 * 
	 * Returns a new board with the same list of located letters and the same dictionary.
	 */
	
	public Board cloneBoard(List<Letter> lettersList){
		return new Board(this, dictionary, lettersList);
	}
	
	/*
	 * addLetter(Letter addLetter)
	 * 
	 * Sets a letter into a position on the board and adds it to the list of located
	 * letters.
	 */
	
	public void addLetter(Letter addLetter){
		this.board[addLetter.getX()][addLetter.getY()] = addLetter;
		lettersList.add(addLetter);
	}
	
	/*
	 * getOffSetX(int startPosition, int cantMoves, Rotation rot)
	 * getOffSetY(int startPosition, int cantMoves, Rotation rot)
	 * 
	 * Given a starting position, an amount of moves and a rotation, returns the
	 * new x/y that is calculated with the equation:
	 * 
	 * newPosition = startPosition + cantMoves * rotation.getVersor()
	 */
	
	public int getOffSetX(int startPosition, int cantMoves, Rotation rot){
		return startPosition + cantMoves * rot.getX();
	}
	
	public int getOffSetY(int startPosition, int cantMoves, Rotation rot){
		return startPosition + cantMoves * rot.getY();
	}
	
	/*
	 * addWord(String word, Letter locatedLetter, int charPosition)
	 * 
	 * Wrapper method of setWord(), receives one word, one Letter and its relative 
	 * position into the word, and calls the function setWord(), with the starting
	 * x and y of the word, its rotation, and its length.
	 */
	
	public boolean addWord(String word, Letter locatedLetter, int charPosition){
		int xPosition = getOffSetX(locatedLetter.getX(), -charPosition, locatedLetter.getRotation());
		int yPosition = getOffSetY(locatedLetter.getY(), -charPosition, locatedLetter.getRotation());
		return setWord(xPosition, yPosition, word, locatedLetter.getRotation());
	}
	
	/*
	 * setWord(int startX, int startY, String word, Rotation rot)
	 * 
	 * Given a word, its starting position, length and rotation, validates if it
	 * is possible to locate it on the board (Not exceeding the limits of the board,
	 * not overlapping another word already located and the respective crossChecks)
	 * 
	 * Once the word is validated, locates it on the board, adding each letter to the
	 * located letters list and its score to the board general score.
	 * 
	 * After this, marks any anchor letter used with its rotation with the value
	 * Rotation.NONE indicating that no words anchored from it anymore.
	 */
	
	private boolean setWord(int startX, int startY, String word, Rotation rot){
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
	
	/*
	 * validateWordMargins(int startX, int startY, int length, Rotation rot)
	 * 
	 * Given a position, the length of the word and its rotation, validates it does
	 * not exceed any margin on the board.
	 */
	
	private boolean validateWordMargins(int startX, int startY, int length, Rotation rot){
		if (startX < 0 || startX > ROWS -1 || startY < 0 || startY > COLS -1)
			return false;
		if (getOffSetX(startX, length, rot) > ROWS || getOffSetY(startY, length, rot) > COLS)
			return false;
		return true;
	}
	
	/*
	 * crossCheck(Character c, int x, int y, Rotation rot)
	 * verticalCrossCheck(int startX, int startY, int length, Rotation rot, String word)
	 * 
	 * Given a character, its position a rotation, gathers the words that are made
	 * in the board in any direction, and validates that all that words belong to 
	 * the dictionary.
	 */
	
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
	
	/*
	 * getUpperString(int x, int y, Rotation rot, String currentString)
	 * getLowerString(int x, int y, Rotation rot, String currentString)
	 * 
	 * Recursive functions that given a position and a rotation, gather the largest 
	 * string that is formed in the board in that positions.
	 */
	
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
	
	/*
	 * getAvailableLetters()
	 * 
	 * Returns a list of all the letters that can be used to anchor a word on them 
	 * into the board (those letters which rotation is different than Rotation.NONE).
	 * 
	 */
	
	public List<Letter> getAvailableLetters() {
		List<Letter> returnList = new ArrayList<Letter> ();
		for (Letter l : lettersList)
			if(l.getRotation() != Rotation.NONE)
				returnList.add(l);
		return returnList;
	}
	
	/*
	 * getBoardScore()
	 * 
	 * Returns the general score of the board.
	 */
	
	public int getBoardScore() {
		return this.boardScore;
	}
	
	/*
	 * print()
	 * 
	 * Prints the board in console.
	 */
	
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
	
	/*
	 * getLettersList()
	 * 
	 * Returns the list of letters set on the board.
	 */
	
	public Set<Letter> getLettersList(){
		return lettersList;
	}
}
