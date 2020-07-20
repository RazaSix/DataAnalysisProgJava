package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import classes.TextSummarising;
import classes.textStats;

class TestTextSummarising {

	@Test
	void testGetSummary() {

		String text = "A chatbot is a program that runs within websites and apps that interacts "
				+ "directly with users to help them with simple tasks. A “conversation” "
				+ "happens between the user and the computer program. These are often currently used "
				+ "for customer support, but the applications for chatbots are growing.";
		
		String[] arrString = text.split(" ");
		ArrayList<String> readText = new ArrayList<String>(Arrays.asList(arrString));
		
		TextSummarising TextSummarising = new TextSummarising(readText);
		textStats textStats = new textStats();
		int ogStringLength = text.length();
		boolean check = false;
		
		if(TextSummarising.getSummary().length() < ogStringLength) {
			check = true;
		}
		
		//System.out.println(TextSummarising.getSummary());
		assertTrue(check);
	}
	
	
	
	
	@Test
	void testCompareKeywords() {
		String text = "A chatbot is a program that runs within websites and apps that interacts "
				+ "directly with users to help them with simple tasks. A “conversation” "
				+ "happens between the user and the computer program. These are often currently used "
				+ "for customer support, but the applications for chatbots are growing.";
		
		String[] arrString = text.split(" ");
		ArrayList<String> readText = new ArrayList<String>(Arrays.asList(arrString));
		
		TextSummarising TextSummarising = new TextSummarising(readText);
		textStats textStats = new textStats();
		
		//System.out.println(textStats.getUniqueKeywords(readText));
		
		//String[] strResult = {"but", "for", "simple", "program", "used", "“conversation”", "interacts", "computer", "are", "chatbots", "growing.", "directly", "tasks.", "between", "apps", "support,", "chatbot", "within", "often", "users", "with", "help", "These", "currently", "websites", "runs", "happens", "user", "program.", "customer", "applications"};
		String[] strResult = {"program", "conversation", "interacts", "computer", "chatbots", "growing", "apps", "support,", "chatbot", "websites","customer", "applications"};
		ArrayList<String> result = new ArrayList<String>(Arrays.asList(strResult));
		
		assertEquals(result, textStats.getUniqueKeywords(readText));
		
	}

}
