package backEnd;

public class Board {

	private char[][] board = new char[15][15];
	
	public boolean setWord(int startX, int startY, String word, Rotation rot){
		if (startX < 0 || startX > board.length - 1 || startY < 0 || startY > board[0].length - 1)
			return false;
		int length = word.length();
		if (startX + length * rot.getLeft() > board.length - 1 || startY + length * rot.getTop() > board.length - 1)
			return false;
		for (int i = 0; i < length; i++){
			board[startX + i * rot.getLeft()][startY + i * rot.getTop()] = word.charAt(i); 
		}
		return true;
	}
	
	public void setPosition(int x, int y, char c){
		board[x][y] = c;
	}
	
	public char getPosition(int x, int y){
		return board[x][y];
	}
	
	
	
}
