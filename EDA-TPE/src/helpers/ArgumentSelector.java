package helpers;

public class ArgumentSelector {

	private String dictionaryFile;
	private String lettersFile;
	private String outputFile;
	private boolean isVisual = false;
	private double maxTime = 0;
	
	public ArgumentSelector(String[] args){
		parseArguments(args);
	}
	
	private void parseArguments(String[] args) {
		if (args[0] == null || args[1] == null || args[2] == null)
			throw new IllegalArgumentException();
		this.dictionaryFile = args[0];
		this.lettersFile = args[1];
		this.outputFile = args[2];
		if (args.length < 4)
			return;
		if (args[3].equals("-visual")) {
			this.isVisual = true;
			if (args.length > 5) {
				String auxString = args[4].concat(args[5]);
				auxString.matches("-maxtime[0-9]*");
				this.maxTime = Integer.valueOf(args[5]);
			}			
		} else if (args.length > 4 && args[3].concat(args[4]).matches("-maxtime[0-9]*")) {
			this.maxTime = Integer.valueOf(args[4]);
		}
	}
	
	public String getDictionaryFile() {
		return this.dictionaryFile;
	}
	
	public String getLettersFile() {
		return this.lettersFile;
	}
	
	public String getOutputFile() {
		return this.outputFile;
	}
	
	public boolean isVisual() {
		return this.isVisual;
	}
	
	public double getMaxTime(){
		return this.maxTime;
	}
	
	
}
