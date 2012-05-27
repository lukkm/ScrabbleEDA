package helpers;

public class FileException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	public FileException(String message) {
		this.message = message;
	}
	
	public String toString() {
		return this.message;
	}

}
