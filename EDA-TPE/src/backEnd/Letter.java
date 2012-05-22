package backEnd;
import helpers.Rotation;

public class Letter implements Cloneable{

	private Character value;
	private int x = -1;
	private int y = -1;
	private Rotation rot;
	
	public Letter(Character value, int x, int y, Rotation rotation) {
		this.value = value;
		this.x = x;
		this.y = y;
		this.rot = rotation;
	}
	
	public void setRotation(Rotation rot){
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
		final int[] primes = {2, 3, 5};
		int result = 1;
		result = primes[0] * result * ((value == null) ? 1 : value.hashCode());
		result = primes[1] * result * (x);
		result = primes[2] * result * (y);
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
	
	public Letter clone(){
		return new Letter(getValue(), getX(), getY(), getRotation());
	}

}
