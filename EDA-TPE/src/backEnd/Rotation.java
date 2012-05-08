package backEnd;

public enum Rotation {
	
	HORIZONTAL(1,0),
	VERTICAL(0,1);
	
	private int left, top;
	
	private Rotation(int left, int top){
		this.left = left;
		this.top = top;
	}
	
	public int getLeft(){
		return left;
	}
	
	public int getTop(){
		return top;
	}
	
}
