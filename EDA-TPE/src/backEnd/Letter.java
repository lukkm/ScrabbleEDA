package backEnd;

public class Letter {

	private Character value;
	private int x = -1;
	private int y = -1;
	private Rotation rot;
	
	public Letter(Character value, int x, int y, Rotation rot) {
		this.value = value;
		this.x = x;
		this.y = y;
		this.rot = rot;
	}
	
	public int getX() {
		return this.x;
	}
	
	public Rotation getRotation(){
		return rot;
	}
	
	public int getY() {
		return this.y;
	}
	
	public char getValue(){
		return value;
	}

}