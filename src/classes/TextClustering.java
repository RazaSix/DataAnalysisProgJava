package classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TextClustering {

	private Map<String, ArrayList<String>> dictionaryLibrary = new HashMap<String, ArrayList<String>>();
	private Map<String, ArrayList<String>> thesaurusLibrary = new HashMap<String, ArrayList<String>>();
	
	public TextClustering() {
		ResourcesGlobal ResourcesGlobal = new ResourcesGlobal();

		this.dictionaryLibrary = ResourcesGlobal.getDictionaryLibrary();
		this.thesaurusLibrary = ResourcesGlobal.getThesaurusLibrary();
	}
	
	public TextClustering(Resources Resources) {
		this.dictionaryLibrary = Resources.getDictionaryLibrary();
		this.thesaurusLibrary = Resources.getThesaurusLibrary();
	}
	
	
	//Returns common elements in TWO (2) given arraylists
	public ArrayList<String> getCommonElements(ArrayList<String> inArrListA, ArrayList<String> inArrListB){
		//ArrayList<String> foundCommonElements = new ArrayList<String>();
		
		ArrayList<String> copyInArrListA = new ArrayList<String>(inArrListA);
		ArrayList<String> copyInArrListB = new ArrayList<String>(inArrListB);
	
		copyInArrListA.retainAll(copyInArrListB);
		
		//list1.retainAll(list2); 
		return copyInArrListA;
	}
	
	public ArrayList<ArrayList<String>> getNounSynonyms(ArrayList<String> inFoundNouns){
		ArrayList<ArrayList<String>> allNounSynonyms = new ArrayList<ArrayList<String>>();
		
		for(int i = 0; i < inFoundNouns.size(); i++) {
			allNounSynonyms.add(getSynonyms(inFoundNouns.get(i)));
		}
		
		
		
		return allNounSynonyms;
	}
	
	//Compares synonyms lists to find common synonyms
	public ArrayList<String> findCommonWords(ArrayList<String> inFoundNouns){
		/*
		List<Integer> newList = list.stream() 
                                      .distinct() 
                                      .collect(Collectors.toList()); 
  
		*/
		
		ArrayList<String> distinctFoundNouns = (ArrayList<String>) inFoundNouns.stream().distinct().collect(Collectors.toList());	
		//System.out.println("Nouns no Duplicate: "+distinctFoundNouns);
		
		//Takes in nouns and finds their synonyms
		ArrayList<ArrayList<String>> inAllNounSynonyms = getNounSynonyms(distinctFoundNouns);
		
		//To hold the found synonyms
		ArrayList<String> foundCommonSynonyms = new ArrayList<String>();
		
		//int n = 1;
		//Cycle through the found noun synonyms
		for(int i = 0; i < inAllNounSynonyms.size(); i++) {
			
			//holds the found common elements for every two arraylists
			ArrayList<String> tempCommonListing = new ArrayList<String>();
			for(int n = (i+1); n < inAllNounSynonyms.size(); n++) {
				
				//tempListing.addAll(c)
				//System.out.println("i= "+i+"inAllNounSynonyms.get(i): "+inAllNounSynonyms.get(i));
				//System.out.println("n= "+n+"inAllNounSynonyms.get(n): "+inAllNounSynonyms.get(n));
				
				tempCommonListing.addAll(getCommonElements(inAllNounSynonyms.get(i), inAllNounSynonyms.get(n)));
				//System.out.println("No. "+i+" Common Words: "+tempCommonListing);
				//System.out.println("-----------------------------");
				//System.out.println();
			}
			
			//Add the found synonyms for the recent checked 2 arraylists
			foundCommonSynonyms.addAll(tempCommonListing);
			tempCommonListing.clear();
			
			//n++;
		}
		//[age, saturation, hill, fish, warder]
		
		ArrayList<String> distinctSynonyms = (ArrayList<String>) foundCommonSynonyms.stream().distinct().collect(Collectors.toList());
		return distinctSynonyms;
	}
	
	

	//Create clusters- All cluster+dictionary/thesaurus stuff to be separated into "textClustering Class"
	public ArrayList<ArrayList<String>> findClusters(ArrayList<String> inCommonWords, ArrayList<String> inFoundNouns){
		ArrayList<ArrayList<String>> foundClusters = new ArrayList<ArrayList<String>>();
		
		
		//Create "blank" arraylists to later hold the cluster's words
		//Plus an extra one to hold non-connected words
		for(int i = -1; i < inCommonWords.size(); i++) {
			ArrayList<String> holdClusterWords = new ArrayList<String>();
			foundClusters.add(holdClusterWords);
		}
		
		
		//Cycle through the found nouns from the text
		for(int n = 0; n < inFoundNouns.size(); n++) {
			boolean check = false;
			//Cycle through the common words
			for(int x = 0; x < inCommonWords.size(); x++) {

				//Check if the noun is related to the common word
				if(getSynonyms(inFoundNouns.get(n)).contains(inCommonWords.get(x))){
					//if the noun is related, add it to the corresponding arraylist in the "holdsClusterWords" arraylist of "foundClusters" arraylists
					foundClusters.get(x).add(inFoundNouns.get(n));
					check = true;
				}
				
				foundClusters.set(x,(ArrayList<String>) foundClusters.get(x).stream().distinct().collect(Collectors.toList()));

			}
				
			//If a word did not match any of the clusters, then it is added to the unclustered
			//Also checks to make sure it has not already been added
			if(foundClusters.get((foundClusters.size()-1)).contains(inFoundNouns.get(n)) || check == true) {
				//do nothing because it has already been added
			}
			else {//Added to the unclustered arraylist
				foundClusters.get((foundClusters.size()-1)).add(inFoundNouns.get(n));
				check = false;
			}
		}
		
		
		return foundClusters;
	}

	
	//Remove common synonyms that result in duplicate clusters
	public ArrayList<String> remRepeatSynonymClus(ArrayList<String> inCommonWords, ArrayList<ArrayList<String>> inFoundClusters){
		//ArrayList<ArrayList<String>> noDuplicateClusters = (ArrayList<ArrayList<String>>) inFoundClusters.stream().distinct().collect(Collectors.toList());
		// use this method because, need to leave the empty arraylists in order to identify the redundant words
		ArrayList<String> removedWordClusters = new ArrayList<String>();
		ArrayList<ArrayList<String>> noDuplicateClusters = new ArrayList<ArrayList<String>>();
		
		
		Set<ArrayList<String>> set=new HashSet<ArrayList<String>>();
		
		//noDuplicateClusters.add(inFoundClusters.get(0));
		boolean flag=true;
		//int duplicate=0;
        for(int i=0;i<inFoundClusters.size()-1;i++){
            flag=set.add(inFoundClusters.get(i));
            if(!(flag)){
            	//System.out.println("Duplicate value in list is = " + inFoundClusters.get(i));
                flag=true;
				//counter++;
            }
            else {
            	removedWordClusters.add(inCommonWords.get(i));
            }
        }
		
		//System.out.println("in textfileprocessing");
		//System.out.println(removedWordClusters);
		return removedWordClusters;
	}
	
	
	//Remove duplicate clusters that have already been created due to too close synonyms
	public ArrayList<ArrayList<String>> removeDuplicateClusters(ArrayList<ArrayList<String>> inFoundClusters){
		//ArrayList<ArrayList<String>> noDuplicateClusters = (ArrayList<ArrayList<String>>) inFoundClusters.stream().distinct().collect(Collectors.toList());
		// use this method because, need to leave the empty arraylists in order to identify the redundant words
		ArrayList<ArrayList<String>> noDuplicateClusters = new ArrayList<ArrayList<String>>();
		Set<ArrayList<String>> set=new HashSet<ArrayList<String>>();
		
		//noDuplicateClusters.add(inFoundClusters.get(0));
		boolean flag=true;
		//int duplicate=0;
        for(int i=0;i<inFoundClusters.size();i++){
            flag=set.add(inFoundClusters.get(i));
            if(!(flag)){
            	//System.out.println("Duplicate value in list is = " + inFoundClusters.get(i));
                flag=true;
				//counter++;
            }
            else {
            	noDuplicateClusters.add(inFoundClusters.get(i));
            }
        }
		

		
		return noDuplicateClusters;
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
	
	//levenshtein distance 
	public double calcDistance(String origWord, String targetWord) {
		origWord = origWord.toLowerCase();
		targetWord = targetWord.toLowerCase();
		
		
		double distanceValue = 0;
		
		
		double substitutionCost = calcSubstitutionCost(origWord, targetWord);
		double insertionCost = calcInsertionCost(origWord, targetWord);
		double deletionCost = calcDeletionCost(origWord, targetWord);
		
		
		distanceValue = substitutionCost + insertionCost + deletionCost;
		
		return distanceValue;
	}
	
	
	public double calcSubstitutionCost(String origWord, String targetWord) {
		double subCost = 0;
		int n = 0;
		
		while((n < origWord.length()) && (n < targetWord.length())) {
			
			if(origWord.charAt(n) != targetWord.charAt(n)) {
				subCost++;
			}
			
			
			n++;
		}
		
		
		
		return subCost;
	}
	
	public double calcInsertionCost(String origWord, String targetWord) {
		double insertCost = 0;
		int difference = 0;
		
		if(origWord.length() < targetWord.length()) {
			//No. of inserts to ogWord to reach target word
			difference = targetWord.length() - origWord.length();
		}
		else {
			//do nothing- does not match criteria to insert
		}
		
		
		insertCost = difference;
		return insertCost;
	}
	
	public double calcDeletionCost(String origWord, String targetWord) {
		double deleteCost = 0;
		int difference = 0;
		
		if(origWord.length() > targetWord.length()) {
			difference = origWord.length() - targetWord.length();
		}
		
		
		deleteCost = difference;
		
		return deleteCost;
	}
	
	

}
