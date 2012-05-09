package helpers;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
	
	public static List<String> parseWords(String file){
		
		File inputFile = new File(file);
		BufferedReader inputWords;
		List<String> lstReturn = new ArrayList<String>();
		
		try {
			inputWords = new BufferedReader(new FileReader(inputFile));
		} catch (FileNotFoundException e) {
			System.out.println("Archivo no encontrado");
			return null;
		}
		
		String line;
		
		try {
			while ((line = inputWords.readLine()) != null){
				lstReturn.add(line.toUpperCase());
			}
		} catch (IOException e) {
			System.out.println("Archivo invalido");
		}
		
		return lstReturn;
		
	}
	
	public static String parseLetters(String file){
		
		File inputFile = new File(file);
		BufferedReader inputLetters;
		
		try {
			inputLetters = new BufferedReader(new FileReader(inputFile));
		} catch (FileNotFoundException e) {
			System.out.println("Archivo no encontrado");
			return null;
		}
		
		String line;
		
		try {
			line = inputLetters.readLine();
			if (line == null)
				return "";
			return line;
		} catch (IOException e) {
			System.out.println("Archivo invalido");
		}
		
		return "";
	}

}
