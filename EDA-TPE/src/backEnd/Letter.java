package backEnd;

public class Letter {

	private Character value;
	private int x = -1;
	private int y = -1;
	
	public Letter(Character value) {
		this.value = value;
	}
	
	public void addToBoard(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getRow() {
		return this.x;
	}
	
	public int getCol() {
		return this.y;
	}

}