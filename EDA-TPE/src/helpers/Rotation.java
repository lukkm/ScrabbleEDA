package helpers;

public enum Rotation {
	
	HORIZONTAL(0,1),
	VERTICAL(1,0),
	NONE(0,0);
	
	private int x, y;
	
	private Rotation(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public Rotation change() {
		if (this.equals(Rotation.HORIZONTAL))
			return Rotation.VERTICAL;
		return Rotation.HORIZONTAL;
	}
	
}
