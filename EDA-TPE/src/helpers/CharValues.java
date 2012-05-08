package helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CharValues {

	Map<Character, Integer> valuesMap = new HashMap<Character, Integer> ();
	
	public CharValues(String source) {
		initializeValues(source);
	}
	
	private void initializeValues(String source) {
		File inputFile = new File(source);
		BufferedReader inputWords;
		
		try {
			inputWords = new BufferedReader(new FileReader(inputFile));
		} catch (FileNotFoundException e) {
			System.out.println("Archivo de puntajes no encontrado");
			return;
		}
		
		String line;
		
		try {
			while ((line = inputWords.readLine()) != null){
				Integer value = Integer.valueOf(line.charAt(1)) - '0';
				valuesMap.put(line.charAt(0), value==0?10:value);
			}
		} catch (IOException e) {
			System.out.println("Archivo invalido");
		}
		
	}
	
	public Map<Character, Integer> getValues(){
		return valuesMap;
	}
	
}
