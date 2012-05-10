package backEnd;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {

	private Letter[][] board = new Letter[15][15];
	private Set<Letter> lettersList = new HashSet<Letter>();
	private static final int[][] primes = new Primes().getPrimes(); 
	
	public Board() {}
	
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
				lettersList.add(l);
				board[posX][posY] = l;
			}else{
				board[posX][posY].setRotation(Rotation.NONE);
			}
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
