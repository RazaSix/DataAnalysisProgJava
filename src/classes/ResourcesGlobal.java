package classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResourcesGlobal {

	
	
	private static Map<String, ArrayList<String>> dictionaryLibrary = new HashMap<String, ArrayList<String>>();
	private static Map<String, ArrayList<String>> thesaurusLibrary = new HashMap<String, ArrayList<String>>();
	private static ArrayList<String> largeWordList = new ArrayList<String>();
	
	public ResourcesGlobal(Map<String, ArrayList<String>> inDictionaryLibrary, Map<String, ArrayList<String>> inThesaurusLibrary, ArrayList<String> inLargeWordList) {
		this.dictionaryLibrary = inDictionaryLibrary;
		this.thesaurusLibrary = inThesaurusLibrary;
		this.largeWordList = inLargeWordList;
	}
	
	public ResourcesGlobal() {
		
	}
	
	
	
	
	
	public Map<String, ArrayList<String>> getDictionaryLibrary() {
		return dictionaryLibrary;
	}
	public Map<String, ArrayList<String>> getThesaurusLibrary() {
		return thesaurusLibrary;
	}

	public ArrayList<String> getLargeWordList() {
		return largeWordList;
	}
	
	
	
}
