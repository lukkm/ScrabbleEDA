package backEnd;

import java.util.ArrayList;
import java.util.List;

public class Board {

	private Letter[][] board = new Letter[15][15];
	
	public Board() {
		
	}
	
	public Board(Board board) {
		this.board = board.getMatrix(); 
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
			if (board[posX][posY].getValue() != word.charAt(i) && 
					board[posX][posY] != null)
				return false;
  
		}
		for (int i = 0; i < length; i++){
			int posX = startX + i * rot.getX();
			int posY = startY + i * rot.getY();
			board[posX][posY] = new Letter(word.charAt(i), posX, posY, board[posX][posY] == null ? rot.change() : Rotation.NONE); 
		}
		return true;
	}
	
	public List<Letter> getAvailableLetters() {
		List<Letter> lettersList = new ArrayList<Letter> ();
		for (Letter[] lY : board)
			for (Letter lX : lY)
				if (lX.getRotation() != Rotation.NONE)
					lettersList.add(lX);
		return lettersList;
	}
	
	public void setPosition(int x, int y, Letter c){
		board[x][y] = c;
	}
	
	public Letter getPosition(int x, int y){
		return board[x][y];
	}
	
	public Letter[][] getMatrix(){
		return this.board;
	}
	
	public void print() {
		for (Letter[] lY : board) {
			for (Letter lX : lY) {
				if (lX == null)
					System.out.print("-");
				else
					System.out.print(lX.getValue());
			}
			System.out.println();
		}
	}
	
}
