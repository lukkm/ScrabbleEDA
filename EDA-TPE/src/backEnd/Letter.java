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
	
	public int hashCode() {
		final int prime = 277;
		int result = 1;
		result = prime * result + ((rot == null) ? 0 : rot.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Letter other = (Letter) obj;
		if (rot != other.rot)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
