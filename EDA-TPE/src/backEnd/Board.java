package backEnd;

public class Board {

	private char[][] board = new char[15][15];
	
	public Board(Board board) {
		this.board = board.getChars(); 
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
			if (board[startX + i * rot.getX()][startY + i * rot.getY()] != word.charAt(i) && 
					board[startX + i * rot.getX()][startY + i * rot.getY()] != 0)
				return false;
  
		}
		for (int i = 0; i < length; i++){
			board[startX + i * rot.getX()][startY + i * rot.getY()] = word.charAt(i); 
		}
		return true;
	}
	
	public void setPosition(int x, int y, char c){
		board[x][y] = c;
	}
	
	public char getPosition(int x, int y){
		return board[x][y];
	}
	
	public char[][] getChars(){
		return this.board;
	}
	
}
