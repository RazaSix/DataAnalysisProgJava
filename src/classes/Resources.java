package classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Resources {

	
	private Map<String, ArrayList<String>> dictionaryLibrary = new HashMap<String, ArrayList<String>>();
	private Map<String, ArrayList<String>> thesaurusLibrary = new HashMap<String, ArrayList<String>>();
	private ArrayList<String> largeWordList = new ArrayList<String>();
	
	public Resources() {
		createDictionary();
		createThesaurus();
		createLargeWordList();
		ResourcesGlobal ResourcesGlobal = new ResourcesGlobal(dictionaryLibrary, thesaurusLibrary, largeWordList);
	}
	
	
	
	
	
	public void createDictionary() {
		//Initial reading of dictionary file into this arraylist
		ArrayList<String> fileDictionaryArrList = new ArrayList<String>();
		
		
		//Will hold the final dictionary
		Map<String, ArrayList<String>> dictionaryMap = new HashMap<String, ArrayList<String>>();
		
		try {
			File file = new File(getClass().getResource("dictionaryB.csv").getFile());
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			
			
			ArrayList<String> definitons = new ArrayList<String>();  
		  	String st; 
		  	int n = 0;
		  	while ((st = br.readLine()) != null) {
		  		fileDictionaryArrList.add(st);
		  		String[] split = fileDictionaryArrList.get(n).split(",",3);
		  		
		  		definitons.add(split[1]);
		  		definitons.add(split[2]);
		  		
		  		
		  		//dictionaryMap.put(split[0], definitons);
		  		int wLength =  split[0].length();
		  		try {
		  		String wordStripped = split[0].toLowerCase().substring(1, (wLength-1));
		  		dictionaryMap.putIfAbsent(wordStripped, new ArrayList<String>());
		  		dictionaryMap.get(wordStripped).addAll(definitons);
		  		}catch(IndexOutOfBoundsException ibe) {
		  			//Weird definition
		  		}
		  		
		  		n++;
		  		
		  		definitons.clear();
		  		}
		  	
		  	
		  	this.dictionaryLibrary = new HashMap<String, ArrayList<String>>(dictionaryMap);
		  	
		  	//System.out.println(st); 
			    
			} 
		catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public void createThesaurus() {
		//Split by "," and put each "word" into an arraylist.
		//First word into dictionary, and arraylist of synonyms into dictionary
		
		ArrayList<String> fileThesaurusList = new ArrayList<String>();
		
		
		//Map<String, ArrayList<String>> dictionaryMap = new HashMap<>();
		Map<String, ArrayList<String>> thesaurusMap = new HashMap<String, ArrayList<String>>();
		
		
		try {
			File file = new File(getClass().getResource("mthesaur.txt").getFile());
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			ArrayList<String> synonyms = new ArrayList<String>();  
		  	String st; 
		  	int n = 0;
		  	while ((st = br.readLine()) != null ) {
		  		fileThesaurusList.add(st);
		  		//System.out.println("B4 split"+st);
		  		
		  		//Split the synonyms into separate words
		  		String[] split = fileThesaurusList.get(n).split(",");
		  		//System.out.println("First word "+split[0]+" :Synonym->  "+split[1]);
		  		//Add synonyms to arraylist
		  		for(int i = 1; i < split.length; i++) {
		  			synonyms.add(split[i]);
		  		}
		  		//System.out.println("In-txtfileprocessing_createThesaurs");
	  			//System.out.println("Synonym Word: "+split[0]+" Synonyms: "+synonyms.toString());
		  		
		  		//Add the initial word to the dictionary and the synonyms arraylist
		  		thesaurusMap.putIfAbsent(split[0].toLowerCase(), new ArrayList<String>(synonyms));
		  		
		  		n++;
		  		
		  		synonyms.clear();
		  		}
		  	
		  	//System.out.println("Thesaurus done-txtfileprocessing_createThesaurs");
		  	this.thesaurusLibrary = new HashMap<String, ArrayList<String>>(thesaurusMap);
		  	
			   br.close();
			} 
		catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void createLargeWordList() {
		ArrayList<String> fileWordList = new ArrayList<String>();
		
		try {
			File file = new File(getClass().getResource("largeWordList.txt").getFile());
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			
			
			String st; 
		  	//int n = 0;
		  	while ((st = br.readLine()) != null ) {
		  		fileWordList.add(st);
			
		  		
		  		//n++;
		  		}
			
		  	
		  	this.largeWordList = new ArrayList<String>(fileWordList);
		  	
		  	br.close();
		} 
		catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	
	//GETS
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
	
	public ArrayList<String> getLargeWordList(){
		return this.largeWordList;
	}
}
