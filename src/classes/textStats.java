package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public class textStats {
	
	//private ArrayList<String> wordLengths = new ArrayList<String>();
	
	//Changed Nouns and ProperNouns to KeywordsA and KeywordsB respectively
	//private Set<String> uniqueKeywords = new HashSet<String>();
	private ArrayList<String> foundKeywordsA = new ArrayList<String>();
	private ArrayList<String> foundKeywordsB = new ArrayList<String>();
	
	private ArrayList<String> foundNouns = new ArrayList<String>();
	private ArrayList<String> foundAdjectives = new ArrayList<String>();
	private ArrayList<String> foundVerbs = new ArrayList<String>();
	private ArrayList<String> foundAdverbs = new ArrayList<String>();
	
	private ArrayList<String> nonSignificantWords = new ArrayList<String>();
	
	private ArrayList<String> subjectSpecificTerms = new ArrayList<String>();
	
	//Accessing Resources
	private Map<String, ArrayList<String>> dictionaryLibrary = new HashMap<String, ArrayList<String>>();
	private Map<String, ArrayList<String>> thesaurusLibrary = new HashMap<String, ArrayList<String>>();
	private ArrayList<String> largeWordList = new ArrayList<String>();
	
	public textStats() {
		populatingNonSigWords();
		
		ResourcesGlobal ResourcesGlobal =  new ResourcesGlobal();
		
		this.dictionaryLibrary = ResourcesGlobal.getDictionaryLibrary();
		this.thesaurusLibrary = ResourcesGlobal.getThesaurusLibrary();
		this.largeWordList = ResourcesGlobal.getLargeWordList();
	}
	
	public textStats(Resources Resources) {
		populatingNonSigWords();
		
		
		
		this.dictionaryLibrary = Resources.getDictionaryLibrary();
		this.thesaurusLibrary = Resources.getThesaurusLibrary();
		this.largeWordList = Resources.getLargeWordList();
	}


	
	
	public void populatingNonSigWords() {
		//Any extra ADVERBs, VERBs, PREPOSITIONs, ADJECTIVEs, PRONOUNs, PREPOSITIONs, CONJUNCTIONs, INTERJECTIONs
		this.nonSignificantWords.add("what");
		this.nonSignificantWords.add("why");
		this.nonSignificantWords.add("where");
		this.nonSignificantWords.add("when");
		this.nonSignificantWords.add("if");
		this.nonSignificantWords.add("how");
		this.nonSignificantWords.add("also");
		this.nonSignificantWords.add("is");
		this.nonSignificantWords.add("it");
		this.nonSignificantWords.add("another");
		this.nonSignificantWords.add("on");
		this.nonSignificantWords.add("like");
		this.nonSignificantWords.add("he");
		this.nonSignificantWords.add("she");
		this.nonSignificantWords.add("it");
		this.nonSignificantWords.add("they");
		this.nonSignificantWords.add("them");
		this.nonSignificantWords.add("more");
		this.nonSignificantWords.add("the");
		this.nonSignificantWords.add("that");
		this.nonSignificantWords.add("whereas");
		this.nonSignificantWords.add("however");
		this.nonSignificantWords.add("and");
		this.nonSignificantWords.add("it");
		this.nonSignificantWords.add("there");
		this.nonSignificantWords.add("were");
		this.nonSignificantWords.add("was");
		this.nonSignificantWords.add("furthermore");
		this.nonSignificantWords.add("have");
		this.nonSignificantWords.add("has");
		this.nonSignificantWords.add("him");
		this.nonSignificantWords.add("one");
		this.nonSignificantWords.add("two");
		this.nonSignificantWords.add("three");
		this.nonSignificantWords.add("four");
		this.nonSignificantWords.add("five");
		this.nonSignificantWords.add("six");
		this.nonSignificantWords.add("seven");
		this.nonSignificantWords.add("eight");
		this.nonSignificantWords.add("nine");
		this.nonSignificantWords.add("ten");
		this.nonSignificantWords.add("could");
		this.nonSignificantWords.add("at");
		this.nonSignificantWords.add("of");
		this.nonSignificantWords.add("some");
		this.nonSignificantWords.add("in");
		this.nonSignificantWords.add("theyre");
		this.nonSignificantWords.add("its");
		this.nonSignificantWords.add("yes");
		this.nonSignificantWords.add("no");
		this.nonSignificantWords.add("so");
		this.nonSignificantWords.add("get");
	}
	
	public ArrayList<String> getFoundNouns() {
		return foundNouns;
	}
	
	public ArrayList<String> getFoundAdverbs() {
		return foundAdverbs;
	}

	public ArrayList<String> getFoundVerbs() {
		return foundVerbs;
	}

	public ArrayList<String> getFoundAdjectives() {
		return foundAdjectives;
	}

	public ArrayList<String> getKeywordsA() {
		return foundKeywordsA;
	}
	
	public ArrayList<String> getKeywordsB() {
		return foundKeywordsB;
	}
	
	public ArrayList<String> getSubjectSpecificTerms() {
		return subjectSpecificTerms;
	}

	//Top 5 longest words
	public ArrayList<String> getLongestWords(ArrayList<String> inWords) {
		ArrayList<String> longestWords = new ArrayList<String>(inWords);
		Collections.sort(longestWords, Comparator.comparing(String::length));
		
		
		ArrayList<String> fiveLongestWords = new ArrayList<String>();
		fiveLongestWords = new ArrayList<String>(longestWords.subList((longestWords.size()-5), (longestWords.size())));
		Collections.reverse(fiveLongestWords);
		
		/*
		
		private static void sortStringListByLength(List<String> list) {
	        System.out.println("-- sorting list of string --");
	        Collections.sort(list, Comparator.comparing(String::length));
	        list.forEach(System.out::println);
    	}
		*/
		
		
		return fiveLongestWords;
	}
	
	
	//Top 5 most frequent words
	public ArrayList<String> mostFrequentWords(ArrayList<String> inWords) {
		ArrayList<String> removedNonWords = new ArrayList<String>(inWords);
		for(int v = 0; v < removedNonWords.size(); v++) {
			if(nonSignificantWords.contains(removedNonWords.get(v))) {
				removedNonWords.remove(v);
			}
		}
		
		ArrayList<String> mostfrequentWords = new ArrayList<String>();
		
		
		Map<String, Integer> count =   new HashMap<String, Integer>(); 
		
		 for(int i = 0; i < removedNonWords.size(); i++) 
	        { 
			 String key = removedNonWords.get(i); 
	            if(count.containsKey(key)) 
	            { 
	                int freq = count.get(key); 
	                freq++; 
	                count.put(key, freq); 
	            } 
	            else
	            { 
	            	count.put(key, 1); 
	            } 
	        }
		 
		 
		 // find max frequency. 
		 int a = 0;
		 while(a < 5) {
			 int max_count = 0;
			String result = ""; 
	          
	        for(Entry<String, Integer> val : count.entrySet()) 
	        { 
	            if (max_count < val.getValue()) 
	            { 
	            	result = val.getKey(); 
	                max_count = val.getValue(); 
	            } 
	        } 
	        
	        mostfrequentWords.add(result);
	        count.remove(result);
	        a++;
		 }
	        
		
		return mostfrequentWords;
	}
	
	//Top 5 least frequent words
	public ArrayList<String> leastFrequentWords(ArrayList<String> inWords) {
		ArrayList<String> leastfrequentWords = new ArrayList<String>();
		
		
		Map<String, Integer> count =   new HashMap<String, Integer>(); 
		
		 for(int i = 0; i < inWords.size(); i++) 
	        { 
			 String key = inWords.get(i); 
	            if(count.containsKey(key)) 
	            { 
	                int freq = count.get(key); 
	                freq++; 
	                count.put(key, freq); 
	            } 
	            else
	            { 
	            	count.put(key, 1); 
	            } 
	        }
		 
		 
		 
		 //find least frequency
		 int a = 0;
		 while(a < 5) {
			
			int min_count = inWords.size();
			String result = ""; 
	          
	        for(Entry<String, Integer> val : count.entrySet()) 
	        { 
	            if (min_count >=  val.getValue()) 
	            { 
	            	result = val.getKey(); 
	            	min_count = val.getValue(); 
	            } 
	        } 
	        
	        leastfrequentWords.add(result);
	        count.remove(result);
	        a++;
		 }
		
		return leastfrequentWords;
	}
	
	//Total word count (includes it's, this, that, etc.
	public int totalWordCount(ArrayList<String> inWords) {
		int totalWordCount = inWords.size();
		
		return totalWordCount;
	}
	
	
	//Discounting words like "this, is, it" etc. 
	public int totalSignificantWordCount(ArrayList<String> inWords) {
		int totalSignificantWordCount = 0;
		
		boolean found = false;
		
		//System.out.println(inWords.toString());
		for(int i = 0; i < inWords.size(); i++) {
			
			
			int x = 0; 
			while(found == false && x < nonSignificantWords.size()) {
				//System.out.println("Check if "+inWords.get(i).toLowerCase().trim()+" = "+nonSignificantWords.get(x).toLowerCase());
				if(inWords.get(i).toLowerCase().trim().equals(nonSignificantWords.get(x).toLowerCase())) {
					//System.out.println("Matched non sig words: "+nonSignificantWords.get(x).toLowerCase());
					
					found = true;
				}
				
				x++;
			}
			
			
			if(found == false) {
				totalSignificantWordCount++;
			}
			
			found = false;
		}
		
		return totalSignificantWordCount;
	}
	


	
	//Returns the word type(s) from the dictionary definition i.i. nouns= n, adverb = adv, adjective = adj etc.
	public ArrayList<String> wordTypeList(ArrayList<String> inDefinition) {
		ArrayList<String> wordTypesArrList = new ArrayList<String>();
		String wordType = null;
		try {
			wordType = inDefinition.get(0).substring(1, (inDefinition.get(0).length()-1));
			String[] arrOfStr = wordType.split("\\.");
			
			for(int n = 0; n < arrOfStr.length; n++) {
				arrOfStr[n] = arrOfStr[n].trim().replaceAll("\\p{Punct}", "");;
				wordTypesArrList.add(arrOfStr[n]);
			}
		}catch(NullPointerException npe) {
			//System.out.println("Probably a blank word i.e. no definition to find a the word type");
		}
		
		//System.out.println(wordType.substring(1, splitEnd));
		
		//System.out.println("Word Type: "+wordType);
		
		
		//System.out.println("Array: "+Arrays.toString(arrOfStr));
		
		return wordTypesArrList;
	}
	
	
	//Returns the word if it is a noun as identified in the dictionary
	public boolean isNoun(ArrayList<String> wordTypesArrList) {
		boolean isNoun = false;
		int n = 0;
		while(n < wordTypesArrList.size() && isNoun == false)
		{
			//System.out.println("n value: "+n);
			if(wordTypesArrList.get(n).equals("n")) {
				isNoun = true;
			}
			n++;
		}
		
		return isNoun;
	}
	
	//Returns the word if it is a adjective as identified in the dictionary
	public boolean isAdjective(ArrayList<String> wordTypesArrList) {
		boolean isAdj = false;
		int n = 0;
		while(n < wordTypesArrList.size() && isAdj == false)
		{
			//System.out.println("n value: "+n);
			if(wordTypesArrList.get(n).equals("a") || wordTypesArrList.get(n).equals("adj")) {
				isAdj = true;
			}
			n++;
		}
		
		return isAdj;
	}
	
	//Returns the word if it is a verb as identified in the dictionary
	public boolean isVerb(ArrayList<String> wordTypesArrList) {
		boolean isVerb = false;
		int n = 0;
		while(n < wordTypesArrList.size() && isVerb == false)
		{
			//System.out.println("n value: "+n);
			if(wordTypesArrList.get(n).equals("v")) {
				isVerb = true;
			}
			n++;
		}
		
		return isVerb;
	}
	
	
	//Returns the word if it is a adverb as identified in the dictionary
	public boolean isAdVerb(ArrayList<String> wordTypesArrList) {
		boolean isAdVerb = false;
		int n = 0;
		while(n < wordTypesArrList.size() && isAdVerb == false)
		{
			//System.out.println("n value: "+n);
			if(wordTypesArrList.get(n).equals("adv")) {
				isAdVerb = true;
			}
			n++;
		}
		
		return isAdVerb;
	}
	
	public void getSpeechParts(ArrayList<String> readTrimmedLowCase) {
		
		ArrayList<String> inFoundNouns = new ArrayList<String>();
		ArrayList<String> inFoundAdjectives = new ArrayList<String>();
		ArrayList<String> inFoundVerbs = new ArrayList<String>();
		ArrayList<String> inFoundAdverbs = new ArrayList<String>();
		
		for(int i = 0; i < readTrimmedLowCase.size(); i++) {
			ArrayList<String> wordTypes = wordTypeList(this.dictionaryLibrary.get(readTrimmedLowCase.get(i).toLowerCase()));
			//System.out.println("Tested word: "+readTrimmedLowCase.get(i));
			//System.out.println("Word types:"+wordTypes.toString());
			
			
			if(isNoun(wordTypes)) {
				inFoundNouns.add(readTrimmedLowCase.get(i));
			}
			else if(isAdjective(wordTypes)) {
				inFoundAdjectives.add(readTrimmedLowCase.get(i));
			}
			else if(isVerb(wordTypes)) {
				inFoundVerbs.add(readTrimmedLowCase.get(i));
			}
			else if(isAdVerb(wordTypes)) {
				inFoundAdverbs.add(readTrimmedLowCase.get(i));
			}
			else {
				
			}
			
		}
		
		//removes duplicates
		//ArrayList<ArrayList<String>> noDuplicateClusters = (ArrayList<ArrayList<String>>) inFoundClusters.stream().distinct().collect(Collectors.toList());
		inFoundNouns = (ArrayList<String>) inFoundNouns.stream().distinct().collect(Collectors.toList());
		inFoundAdjectives = (ArrayList<String>) inFoundAdjectives.stream().distinct().collect(Collectors.toList());
		inFoundVerbs = (ArrayList<String>) inFoundVerbs.stream().distinct().collect(Collectors.toList());
		inFoundAdverbs = (ArrayList<String>) inFoundAdverbs.stream().distinct().collect(Collectors.toList());
		
		this.foundNouns = new ArrayList<String>(inFoundNouns);
		
		//this.foundKeywordsA = new ArrayList<String>(inFoundNouns); //Moved to its own method
		this.foundAdjectives = new ArrayList<String>(inFoundAdjectives);
		this.foundVerbs = new ArrayList<String>(inFoundVerbs);
		this.foundAdverbs = new ArrayList<String>(inFoundAdverbs);
		
		
	}
	
	public ArrayList<String> getUniqueKeywords(ArrayList<String> readTrimmedLowCase) {
		Set<String> uniqueKeywords = new HashSet<String>();
		ArrayList<String> foundKeywords = new ArrayList<String>();
		
		
		findKeywordsA(readTrimmedLowCase);
		findKeywordsB(readTrimmedLowCase);
		
		for(int a = 0; a < this.foundKeywordsA.size(); a++) {
			if(this.foundKeywordsA.get(a).length() > 2) {
				uniqueKeywords.add(this.foundKeywordsA.get(a));
			}
			
		}
		
		
		for(int b = 0; b < this.foundKeywordsB.size(); b++) {
			if(this.foundKeywordsB.get(b).length() > 2) {
				uniqueKeywords.add(this.foundKeywordsB.get(b));
			}
			
		}
		
		for(int c = 0; c < this.subjectSpecificTerms.size(); c++) {
			if(this.subjectSpecificTerms.get(c).length() > 2) {
				uniqueKeywords.add(this.subjectSpecificTerms.get(c));
			}
			
		}
		
		
		for(String temp : uniqueKeywords) {
			foundKeywords.add(temp);
		}
		
		
		return foundKeywords;
		
	}
	
	
	public ArrayList<Integer> keywordFreqCount(ArrayList<String> inKeywordsList, ArrayList<String> inReadText){
		ArrayList<Integer> frequencyCount = new ArrayList<Integer>();
		
		int count = 0;
		for(int countArr = 0; countArr < inKeywordsList.size(); countArr++) {
			count = Collections.frequency(inReadText, inKeywordsList.get(countArr));
			frequencyCount.add(count);
		}
		
		return frequencyCount;
	}
	
	//Nouns
	public void findKeywordsA(ArrayList<String> readTrimmedLowCase) {
		ArrayList<String> inFoundNouns = new ArrayList<String>();
		
		for(int i = 0; i < readTrimmedLowCase.size(); i++) {
			ArrayList<String> wordTypes = wordTypeList(this.dictionaryLibrary.get(readTrimmedLowCase.get(i).toLowerCase()));
			
			if(isNoun(wordTypes)) {
				inFoundNouns.add(readTrimmedLowCase.get(i));
			}
			
			
			this.foundKeywordsA = new ArrayList<String>(inFoundNouns);
			
}
		
		//ArrayList<ArrayList<String>> noDuplicateClusters = (ArrayList<ArrayList<String>>) inFoundClusters.stream().distinct().collect(Collectors.toList());
		inFoundNouns = (ArrayList<String>) inFoundNouns.stream().distinct().collect(Collectors.toList());
	}
	
	
	//Words not found in the dictionary after removing non significant words
	public void findKeywordsB(ArrayList<String> readTrimmedLowCase) {
		boolean found = false;
		for(int n = 0; n < readTrimmedLowCase.size(); n++) {
			//this.largeWordList.contains(readTrimmedLowCase.get(n).toLowerCase())
			//if not in dictionary and thesaurus
			if((this.dictionaryLibrary.get(readTrimmedLowCase.get(n).toLowerCase()) == null) && 
					(this.thesaurusLibrary.get(readTrimmedLowCase.get(n).toLowerCase()) == null) ) {
				
				int x = 0;
				while (x < this.nonSignificantWords.size() && found == false) {
					
					//if not an insignificant word
					if(this.nonSignificantWords.get(x).equals(readTrimmedLowCase.get(n).toLowerCase())) {
						found = true;
					}
					
					x++;
				//end of while loop	
				}
				
				if(found == false) {
					this.foundKeywordsB.add(readTrimmedLowCase.get(n));
				}
				
				found = false;
				
			//end of if statement	
			}
		//end of for loop	
		}
	//end of method	
	}
	
	

	//terms not in dictionary or large word list
	public void findSubSpecificTerms(ArrayList<String> readTrimmedLowCase) {
		for(int n = 0; n < readTrimmedLowCase.size(); n++) {
			if(this.largeWordList.contains(readTrimmedLowCase.get(n).toLowerCase())) {
				
			}
			else {
				if(readTrimmedLowCase.get(n).toLowerCase().length() < 2) {
					
				}
				else {
					this.subjectSpecificTerms.add(readTrimmedLowCase.get(n).toLowerCase());
				}
				
			}
		}
	}
	
	/*
	public String textSummary(ArrayList<String> readTrimmedOGCase) {
		String summary = "";
		
		ArrayList<String> uniqueKeywords = getUniqueKeywords(readTrimmedOGCase);
		
		//words after keyword added to summary
		int keySurroundWords = 3;
		
		int lineBreak = 0;
		for(int s = 0; s < readTrimmedOGCase.size(); s++) {
			
			String newLine = "\n";
			if(uniqueKeywords.contains(readTrimmedOGCase.get(s))) {
				summary+= readTrimmedOGCase.get(s)+" ";
				int checkNxt = s;
				boolean checkNxtBool = false;
				lineBreak++;
				if(lineBreak == 10) {
					//summary+= newLine;
					lineBreak = 0;
				}
				//Check if the next word is a key word and add it
				while((s+1) < readTrimmedOGCase.size() && checkNxtBool == false) {
					checkNxt = (s+1);
					if(uniqueKeywords.contains(readTrimmedOGCase.get(checkNxt))) {
						summary+= readTrimmedOGCase.get(checkNxt)+" ";
						checkNxt++;
						lineBreak++;
						s = checkNxt;
						
						if(lineBreak == 10) {
							//summary+= newLine;
							lineBreak = 0;
						}
					}
					else {
						//add the next three words
						//for(int r = 1; r <= keySurroundWords; r++) {
						int r = 1;
							while((r+s) < readTrimmedOGCase.size() && r <= keySurroundWords) {
								
								s += r;
								summary+= readTrimmedOGCase.get((s))+" ";
								lineBreak++;
								r++;
								if(lineBreak == 10) {
									//summary+= newLine;
									lineBreak = 0;
								}
							}
							
							
						//}
						
						checkNxtBool = true;
					}
				}
				
				
				
				
				
			}
		}
		
		
		return summary;
	}
	*/
	
}
