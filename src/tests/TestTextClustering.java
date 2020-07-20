package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import classes.Resources;
import classes.TextClustering;

class TestTextClustering {

	@Test
	void testGetCommonElements() {
		ArrayList<String> wordListA = new ArrayList<String>();
		wordListA.add("technology");
		wordListA.add("artificial");
		wordListA.add("science");
		wordListA.add("memory");
		
		
		ArrayList<String> wordListB = new ArrayList<String>();
		wordListB.add("computers");
		wordListB.add("memory");
		wordListB.add("technology");
		wordListB.add("price");
		
		ArrayList<String> result = new ArrayList<String>();
		result.add("technology");
		result.add("memory");
		
		TextClustering textClustering = new TextClustering();
		
		assertEquals(result, textClustering.getCommonElements(wordListA, wordListB));
	}

	
	@Test
	void testFindCommonWords() {
		Resources Resources = new Resources();
		TextClustering textClustering = new TextClustering(Resources);
		
		ArrayList<String> wordList = new ArrayList<String>();
		ArrayList<String> result = new ArrayList<String>();
		
		//identified nouns from string
		String[] comNons = {"algorithm", "formula", "relationship", "recipe", "machine", "learning", "ai", "hope"};
		ArrayList<String> fnouns = new ArrayList<String>(Arrays.asList(comNons));
		
		
		//common synonyms identified from above nouns
		String[] comArray = {"MO", "form", "means", "method", "modus operandi", "practice", "procedure", "technique", 
				"way", "approach", "system", "process", "combination", "formulary", "prescription", 
				"receipt", "alliance", "assemblage", "association", "union", "plan", "drive"};
		result = new ArrayList<String>(Arrays.asList(comArray));
		
		assertEquals(result, textClustering.findCommonWords(fnouns));
	}
	
	@Test
	void testFindClusters() {
		Resources Resources = new Resources();
		TextClustering textClustering = new TextClustering(Resources);
		
		ArrayList<String> wordList = new ArrayList<String>();
		ArrayList<String> commonSynonyms = new ArrayList<String>();
		ArrayList<ArrayList<String>> resultclusters = new ArrayList<ArrayList<String>>();
		
		String textSample = "An algorithm is a formula representing the relationship between certain variables \r\n" + 
				"Think of algorithms as a simple set of instructions with a finite end designed to \r\n" + 
				"produce an output An every day example of an algorithm is a recipe You have \r\n" + 
				"a set amount of inputs ingredients designed to produce a repeatable output \r\n" + 
				"apple pie for example Machine learning programs in AI use algorithms to make predictions \r\n" + 
				"and in the case of marketing suggestions are based on algorithms that hope to target the \r\n" + 
				"users specific preferences if website visitor A visits pages for kids short sleeve \r\n" + 
				"shirts an algorithm will email them coupons for kids short sleeve shirts";
		
		
		//initial read straing split then put in array
		String[] textArray = textSample.split(" ");
		wordList = new ArrayList<String>(Arrays.asList(textArray));
		
		//identified nouns from string
		String[] comNons = {"algorithm", "formula", "relationship", "recipe", "machine", "learning", "ai", "hope"};
		ArrayList<String> fnouns = new ArrayList<String>(Arrays.asList(comNons));
		
		//common synonyms identified from above nouns
		String[] comArray = {"MO", "practice", "approach", "system", "process", "combination", "formulary", "alliance", "plan", "drive"};
		commonSynonyms = new ArrayList<String>(Arrays.asList(comArray));
		
		
		//expected clusters
		String[][] comClus = {{"algorithm", "formula", "recipe"}, {"algorithm", "formula"}, {"algorithm", "recipe", "hope"}, {"algorithm", "recipe", "machine"}, {"algorithm", "machine"}, {"formula", "relationship", "machine"}, {"formula", "recipe"}, {"relationship", "machine"}, {"recipe", "hope"}, {"machine", "hope"}, {"learning", "ai"}};
		for(int y = 0; y < comClus.length; y++) {
			ArrayList<String> tempArrList = new ArrayList<String>(Arrays.asList(comClus[y]));
			resultclusters.add(tempArrList);
		}
		
		assertEquals(resultclusters, textClustering.findClusters(commonSynonyms, fnouns));
		
		
		
		
	}


}
