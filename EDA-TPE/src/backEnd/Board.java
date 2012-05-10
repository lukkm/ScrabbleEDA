package backEnd;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {

	private Letter[][] board = new Letter[15][15];
	private Set<Letter> lettersList = new HashSet<Letter>(); 
	
	public Board() {
		
	}
	
	public Board(Board board) {
		for (Letter l : board.getLettersList()){
			Letter addLetter =  new Letter(l.getValue(), l.getX(), l.getY(), l.getRotation());
			this.board[l.getX()][l.getY()] = addLetter;
			lettersList.add(addLetter);
		}
	}
	
	public Set<Letter> getLettersList(){
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
  
		}
		for (int i = 0; i < length; i++){
			int posX = startX + i * rot.getX();
			int posY = startY + i * rot.getY();
			Letter l;
			if (board[posX][posY] == null){
				l = new Letter(word.charAt(i), posX, posY, rot.change());
			}else{
				l = new Letter(word.charAt(i), posX, posY, Rotation.NONE);
				// esto esta como el orto, deberia sacar la letra de la lista asi despues la agrega con la rotacion en none, pero esta re feo hecho..
				lettersList.remove(new Letter(word.charAt(i), posX, posY, board[posX][posY].getRotation()));
			}
			lettersList.add(l);
			board[posX][posY] = l; 
		}
		return true;
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
	
}
