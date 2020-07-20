package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import classes.Resources;
import classes.textStats;

class TestTextStats {

	@Test
	void testGetFoundNouns() {
		
		ArrayList<String> results = new ArrayList<String>();
		
		
		String sentence = "science, yes, scientist, no, machinery, it, maybe, level, warden, so, railroad";
		String[] arrSent = sentence.split(", ");
		ArrayList<String> inWords = new ArrayList<String>();
		for(int n = 0; n < arrSent.length; n++) {
			inWords.add(arrSent[n].trim().toLowerCase());
			
		}
		results.add("science");
		results.add("scientist");
		results.add("machinery");
		results.add("level");
		results.add("warden");
		results.add("railroad");
		
		
		
    	Resources Resources =  new Resources();
		
		textStats textStats = new textStats(Resources);
		textStats.getSpeechParts(inWords);

		
		assertEquals(results, textStats.getFoundNouns());
		
		
	}

	@Test
	void testGetFoundAdverbs() {
		ArrayList<String> results = new ArrayList<String>();
		
		
		String sentence = "do, it, humanly, no, actively, it, maybe, fashionably, warden, naively, railroad";
		String[] arrSent = sentence.split(", ");
		ArrayList<String> inWords = new ArrayList<String>();
		for(int n = 0; n < arrSent.length; n++) {
			inWords.add(arrSent[n].trim().toLowerCase());
			
		}
		results.add("humanly");
		results.add("actively");
		results.add("fashionably");
		results.add("naively");
		
		
		
    	Resources Resources =  new Resources();
		
		textStats textStats = new textStats(Resources);
		textStats.getSpeechParts(inWords);

		
		assertEquals(results, textStats.getFoundAdverbs());
	}

	@Test
	void testGetFoundVerbs() {
		ArrayList<String> results = new ArrayList<String>();
		
		
		String sentence = "do, it, nap, no, actively, it, navigate, fashionably, herald, naively, jangle";
		String[] arrSent = sentence.split(", ");
		ArrayList<String> inWords = new ArrayList<String>();
		for(int n = 0; n < arrSent.length; n++) {
			inWords.add(arrSent[n].trim().toLowerCase());
			
		}
		results.add("nap");
		results.add("navigate");
		results.add("jangle");
		
		
		
    	Resources Resources =  new Resources();
		
		textStats textStats = new textStats(Resources);
		textStats.getSpeechParts(inWords);

		
		assertEquals(results, textStats.getFoundVerbs());
	}

	@Test
	void testGetFoundAdjectives() {
		ArrayList<String> results = new ArrayList<String>();
		
		
		String sentence = "do, it, racing, row, it, hit, scalping, red-handed, herald, naively, scholastical";
		String[] arrSent = sentence.split(", ");
		ArrayList<String> inWords = new ArrayList<String>();
		for(int n = 0; n < arrSent.length; n++) {
			inWords.add(arrSent[n].trim().toLowerCase());
			
		}
		results.add("row");
		results.add("red-handed");
		results.add("scholastical");
		
		
		
    	Resources Resources =  new Resources();
		
		textStats textStats = new textStats(Resources);
		textStats.getSpeechParts(inWords);

		
		assertEquals(results, textStats.getFoundAdjectives());
	}

	@Test
	void testGetKeywordsA() {
		ArrayList<String> results = new ArrayList<String>();
		
		
		String sentence = "science, yes, scientist, no, machinery, it, maybe, level, warden, so, railroad";
		String[] arrSent = sentence.split(", ");
		ArrayList<String> inWords = new ArrayList<String>();
		for(int n = 0; n < arrSent.length; n++) {
			inWords.add(arrSent[n].trim().toLowerCase());
			
		}
		results.add("science");
		results.add("scientist");
		results.add("machinery");
		results.add("level");
		results.add("warden");
		results.add("railroad");
		
		
		
    	Resources Resources =  new Resources();
		
		textStats textStats = new textStats(Resources);
		textStats.findKeywordsA(inWords);

		
		assertEquals(results, textStats.getKeywordsA());
	}

	@Test
	void testGetKeywordsB() {
		ArrayList<String> results = new ArrayList<String>();
		
		
		String sentence = "yes, AMA, no, GNUSI, it, level, GCPD, so, railroad";
		String[] arrSent = sentence.split(", ");
		ArrayList<String> inWords = new ArrayList<String>();
		for(int n = 0; n < arrSent.length; n++) {
			inWords.add(arrSent[n].trim().toLowerCase());
			
		}
		results.add("ama");
		results.add("gnusi");
		results.add("gcpd");
		
		
		
    	Resources Resources =  new Resources();
		
		textStats textStats = new textStats(Resources);
		textStats.findKeywordsB(inWords);

		
		assertEquals(results, textStats.getKeywordsB());
	}

	@Test
	void testGetSubjectSpecificTerms() {
		ArrayList<String> results = new ArrayList<String>();
		
		
		String sentence = "yes, datasets, no, multivaried, it, level, GCPD, so, railroad";
		String[] arrSent = sentence.split(", ");
		ArrayList<String> inWords = new ArrayList<String>();
		for(int n = 0; n < arrSent.length; n++) {
			inWords.add(arrSent[n].trim().toLowerCase());
			
		}
		results.add("datasets");
		results.add("multivaried");
		results.add("gcpd");
		
		
		
    	Resources Resources =  new Resources();
		
		textStats textStats = new textStats(Resources);
		textStats.findSubSpecificTerms(inWords);

		
		assertEquals(results, textStats.getSubjectSpecificTerms());
	}

	@Test
	void testGetLongestWords() {
		ArrayList<String> results = new ArrayList<String>();
		
		
		String sentence = "yes, datasets, no, multivaried, it, level, GCPD, so, railroad";
		String[] arrSent = sentence.split(", ");
		ArrayList<String> inWords = new ArrayList<String>();
		for(int n = 0; n < arrSent.length; n++) {
			inWords.add(arrSent[n].trim().toLowerCase());
			
		}
		results.add("multivaried");
		results.add("railroad");
		results.add("datasets");
		results.add("level");
		results.add("gcpd");
		
		
		
    	Resources Resources =  new Resources();
		
		textStats textStats = new textStats(Resources);
		

		
		assertEquals(results, textStats.getLongestWords(inWords));
	}

	@Test
	void testMostFrequentWords() {
		ArrayList<String> results = new ArrayList<String>();
		
		
		String sentence = "yes, datasets, level, datasets, it, level, datasets, so, railroad, multivaried, it, scientist, scientist,"
				+ " railroad, multivaried";
		String[] arrSent = sentence.split(", ");
		ArrayList<String> inWords = new ArrayList<String>();
		for(int n = 0; n < arrSent.length; n++) {
			inWords.add(arrSent[n].trim().toLowerCase());
			
		}
		results.add("datasets");
		results.add("railroad");
		results.add("multivaried");
		results.add("level");
		results.add("scientist");
		
		
		
		
    	Resources Resources =  new Resources();
		
		textStats textStats = new textStats(Resources);
		

		
		assertEquals(results, textStats.mostFrequentWords(inWords));
	}

	@Test
	void testLeastFrequentWords() {
		ArrayList<String> results = new ArrayList<String>();
		
		
		String sentence = "orange, datasets, level, datasets, purple, level, datasets, artificial, railroad, multivaried,"
				+ " technology, scientist, scientist, railroad, multivaried";
		String[] arrSent = sentence.split(", ");
		ArrayList<String> inWords = new ArrayList<String>();
		for(int n = 0; n < arrSent.length; n++) {
			inWords.add(arrSent[n].trim().toLowerCase());
			
		}
		results.add("technology");
		results.add("purple");
		results.add("artificial");
		results.add("orange");
		results.add("scientist");
		
		
		
		
    	Resources Resources =  new Resources();
		
		textStats textStats = new textStats(Resources);
		

		
		assertEquals(results, textStats.leastFrequentWords(inWords));
	}

	@Test
	void testTotalWordCount() {
		int result = 15;
		
		
		String sentence = "orange, datasets, level, datasets, purple, level, datasets, artificial, railroad, multivaried,"
				+ " technology, scientist, scientist, railroad, multivaried";
		String[] arrSent = sentence.split(", ");
		ArrayList<String> inWords = new ArrayList<String>();
		for(int n = 0; n < arrSent.length; n++) {
			inWords.add(arrSent[n].trim().toLowerCase());
			
		}
		
		textStats textStats = new textStats();
		assertEquals(result, textStats.totalWordCount(inWords));
		
	}

	@Test
	void testTotalSignificantWordCount() {
		int result = 11;
		
		
		String sentence = "yes, datasets, level, datasets, it, level, datasets, so, railroad, multivaried, it, scientist, scientist,"
				+ " railroad, multivaried";
		String[] arrSent = sentence.split(", ");
		ArrayList<String> inWords = new ArrayList<String>();
		for(int n = 0; n < arrSent.length; n++) {
			inWords.add(arrSent[n].trim().toLowerCase());
			
		}
		
		textStats textStats = new textStats();
		

		
		assertEquals(result, textStats.totalSignificantWordCount(inWords));
	
	}


}
