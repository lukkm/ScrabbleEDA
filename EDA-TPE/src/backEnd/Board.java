package backEnd;

public class Board {

	private char[][] board = new char[15][15];
	
	public boolean setWord(int startRow, int startCol, String word, Rotation rot){
		if (startRow < 0 || startRow > board.length - 1 || startCol < 0 || startCol > board[0].length - 1)
			return false;
		int length = word.length();
		if (startRow + length * rot.getTop() > board.length - 1 || startCol + length * rot.getLeft() > board.length - 1)
			return false;
		for (int i = 0; i < length; i++){
			board[startRow + i * rot.getTop()][startCol + i * rot.getLeft()] = word.charAt(i); 
		}
		return true;
	}
	
	public void setPosition(int row, int col, char c){
		board[row][col] = c;
	}
	
	public char getPosition(int row, int col){
		return board[row][col];
	}
	
	
	
}
