package classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class TextFileProcessing {

	//List to hold intial data read from file in string format
	private List<List<String>> records = new ArrayList<>();
	private Map<String, ArrayList<String>> dictionaryLibrary = new HashMap<String, ArrayList<String>>();
	private Map<String, ArrayList<String>> thesaurusLibrary = new HashMap<String, ArrayList<String>>();
	
	
	private boolean errorFileProcessing = false;
	private String errorReason;
	
	public TextFileProcessing() {
		ResourcesGlobal ResourcesGlobal = new ResourcesGlobal();

		this.dictionaryLibrary = ResourcesGlobal.getDictionaryLibrary();
		this.thesaurusLibrary = ResourcesGlobal.getThesaurusLibrary();
	}
	
	
	public TextFileProcessing(String fileName) {
		ResourcesGlobal ResourcesGlobal = new ResourcesGlobal();

		this.dictionaryLibrary = ResourcesGlobal.getDictionaryLibrary();
		this.thesaurusLibrary = ResourcesGlobal.getThesaurusLibrary();
		
		if(readFile(fileName) == true){
			
		}
		else {
			//System.out.println("Could not read file- printed from constructor");
			this.errorReason = "Could not read file- printed from constructor";
			this.errorFileProcessing = true;
		}
	}
	
	public boolean getErrorCheck() {
		return this.errorFileProcessing;
	}
	
	public String getErrorReason() {
		return this.errorReason;
	}
	
	public List<List<String>> getRecords(){
		return this.records;
	}
	
	public String getExtension(String filepath) {
		
		int dotPosition = filepath.indexOf('.');
    	int fileSize = filepath.length();
    	String extension = filepath.substring(dotPosition, fileSize);
    	
    	return extension;
	}
	
	
 
	
	/*
	 * GETS
	 * 
	 */
	public String getDefinition(String inWord) {
		String definition = "";
		
		try{
			definition = this.dictionaryLibrary.get(inWord.toLowerCase()).get(1);
		}catch(NullPointerException npe) {
			definition = "[NO DEFINITION FOUND]";
		}
		
		return definition;
	}
	
	public ArrayList<String> getSynonyms(String inWord) {
		ArrayList<String> synonyms = new ArrayList<String>();
		
		try{
			synonyms = new ArrayList<String>(this.thesaurusLibrary.get(inWord.toLowerCase()));
		}catch(NullPointerException npe) {
			synonyms.add("[NO SYNONYMS FOUND]");
		}
		
		return synonyms;
	}
	
	public Map<String, ArrayList<String>> getDictionaryLibrary() {
		return this.dictionaryLibrary;
	}
	
	public Map<String, ArrayList<String>> getThesaurusLibrary() {
		return this.thesaurusLibrary;
	}


	/*
	 * READING FILE INFO
	 */
	public boolean readFile(String fileName) {
		//System.out.println("Got to readFile");
		boolean result = true;
		
		
		try {
			FileInputStream finStream = new FileInputStream(fileName);
    		BufferedReader reader = new BufferedReader(new InputStreamReader(finStream));
			
    		/*
			BOMInputStream bomIn = new BOMInputStream(finStream, ByteOrderMark.UTF_8, ByteOrderMark.UTF_16BE,
			        ByteOrderMark.UTF_16LE, ByteOrderMark.UTF_32BE, ByteOrderMark.UTF_32LE);
			
			bomIn.read(); // Skips BOM but was skipping the first actual value as well, and repeated so removed the entire first column        
			bomIn.close();
			*/
    		/*
    		String extension = getExtension(filepath);
    		if(extension.equals(".doc")) {
    			HWPFDocument document = new HWPFDocument(fis);
                extractor = new WordExtractor(document);
                String[] fileData = extractor.getParagraphText();
    		}
    		else {
    			
    		}*/
    		
    		

			//Reads the first line
    		String line = reader.readLine();
    		//System.out.println(line);ï»¿
    		
    		//System.out.println("firstNonBOMByte: "+firstNonBOMByte);
    		//If byte is found, line is changed to skip the characters
    		//Should only run if the byte order mark exists
    		String sym = "ï";
    		char c = sym.charAt(0);
    		if(line.charAt(0) == c) {
    			//System.out.println("Has BOM");
    			//System.out.println("Line b4 strip: "+line);
				line = line.substring(3);
				//System.out.println("Line after: "+line);
			}
    		else {
    			//System.out.println("no BOM");
    			
    		}
    		
    		//Add the line modified or not the system
    		this.records.add(getRecordFromLine(line));
    		
    		//Continue to read the rest of the file like normal
    		while((line = reader.readLine()) != null){
    			this.records.add(getRecordFromLine(line));
    			//System.out.println(line);
    		}
    		
    		finStream.close();
    		
			

		}catch(Exception ioe) {
			System.out.println("Found UTF-8 BOM but could not skip");
		}
		

		return result;
	}
	
	
	
	//parse the lines from the csv and puts into array
	//Used in the readFile method
	public List<String> getRecordFromLine(String inString) {
	    List<String> values = new ArrayList<String>();
	    try (Scanner rowScanner = new Scanner(inString)) {
	        rowScanner.useDelimiter(",");
	        while (rowScanner.hasNext()) {
	            values.add(rowScanner.next());
	        }
	    }
	    return values;
	}
	
	
}
