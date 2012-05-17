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

public class Parser {
	
	public List<String> parseWords(String file){
		
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
				String lineToUpper = line.toUpperCase();
				if (isValidWord(lineToUpper))
					lstReturn.add(lineToUpper);
			}
		} catch (IOException e) {
			System.out.println("Archivo invalido");
		}
		return lstReturn;
		
	}
	
	private boolean isValidWord(String s){
		if (s.length()<2  || s.length()>7)
			return false;
		int diff;
		for (char c : s.toCharArray()){
			diff = ((int)c)-'A';
			if (diff < 0 || diff > 25)
				return false;
		}
		return true;
			
	}
	
	public String parseLetters(String file){
		
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
