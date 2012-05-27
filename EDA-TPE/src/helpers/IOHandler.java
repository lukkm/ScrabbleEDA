package helpers;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import backEnd.Letter;

public class IOHandler {
	
	public List<String> getWords(String file) throws FileException{
		
		File inputFile = new File(file);
		BufferedReader inputWords;
		List<String> lstReturn = new ArrayList<String>();
		
		try {
			inputWords = new BufferedReader(new FileReader(inputFile));
		} catch (FileNotFoundException e) {
			throw new FileException("'" + file + "'" + "not found!");
		}
		
		String line;
		
		try {
			while ((line = inputWords.readLine()) != null){
				if (isValidWord(line)) {
					lstReturn.add(line.toUpperCase());
				}
			}
		} catch (IOException e) {
			throw new FileException("'" + file + "'" + " is not a valid dictionary file!");
		}
		return lstReturn;
		
	}
	
	public String getHandLetters(String file) throws FileException{
		
		File inputFile = new File(file);
		BufferedReader inputLetters;
		
		try {
			inputLetters = new BufferedReader(new FileReader(inputFile));
		} catch (FileNotFoundException e) {
			throw new FileException("'" + file + "'" + " not found!");
		}
		
		String line;
		
		try {
			line = inputLetters.readLine();
			if (line == null)
				return "";
			if (validateLetters(line))
				return line.toUpperCase();
		} catch (IOException e) {
			throw new FileException("'" + file + "'" + " is not a valid letters file!");
		}
		
		return "";
	}
	
	private boolean isValidWord(String s){
		return s.matches("[A-Za-z]{2,7}");			
	}
	
	private boolean validateLetters(String s) {
		return s.matches("[A-Za-z]{1,80}");
	}

	public void printSolution(Set<Letter> out, String file) {
		Writer writer;
		try {
			writer = new FileWriter(file);
			BufferedWriter buffered = new BufferedWriter(writer);
			char[][] line = new char[15][30]; 
			for (int i = 0 ; i < 15 ; i++) {
				for (int j = 0; j < 30 ; j+=2) {
					line[i][j] = '-';
					line[i][j+1] = ' ';
				}
			}
			for (Letter l : out) {
				line[l.getX()][l.getY()*2] = l.getValue();
			}
			for (char[] c : line) {
				buffered.write(c);
				buffered.newLine();
			}
			buffered.flush();
		} catch (IOException e) {
			System.out.println("Carpeta no encontrada");
			e.printStackTrace();
		}
	}
	
}
