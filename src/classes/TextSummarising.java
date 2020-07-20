package classes;

import java.util.ArrayList;

public class TextSummarising {

	private textStats textStats = new textStats();
	private ArrayList<String> textKeywords = new ArrayList<String>();
	
	private String summary;
	
	public TextSummarising(ArrayList<String> readTrimmedOGCase) {
		//textStats.getSpeechParts(readTrimmedLowCase);
		
		//textStats.findSubSpecificTerms(readTrimmedLowCase);
		
		createSummary(readTrimmedOGCase);
	}
	
	
	public String getSummary() {
		return summary;
	}
	
	
	
	
	public void createSummary(ArrayList<String> readTrimmedOGCase) {
		String summary = "";
		
		ArrayList<String> uniqueKeywords = textStats.getUniqueKeywords(readTrimmedOGCase);
		
		//words after keyword added to summary
		int keySurroundWords = 5;
		
		//Add first five words
		for(int initial = 0; initial < keySurroundWords; initial++) {
			summary+= readTrimmedOGCase.get(initial)+" ";
		}
		
		int lineBreak = 0;
		for(int s = keySurroundWords; s < readTrimmedOGCase.size(); s++) {
			
			String newLine = "\n";
			if(uniqueKeywords.contains(readTrimmedOGCase.get(s))) {
				summary+= readTrimmedOGCase.get(s)+" ";
				int checkNxt = s+1;
				boolean checkNxtBool = false;
				lineBreak++;
				if(lineBreak == 10) {
					summary+= newLine;
					lineBreak = 0;
				}
				//Check if the next word is a key word and add it
				while(checkNxt < readTrimmedOGCase.size() && checkNxt < (s+keySurroundWords) && checkNxtBool == false) {
					//checkNxt = (s+1);
					if(uniqueKeywords.contains(readTrimmedOGCase.get(checkNxt))) {
						summary+= readTrimmedOGCase.get(checkNxt)+" ";
						checkNxt++;
						lineBreak++;
						s = checkNxt;
						
						if(lineBreak == 10) {
							 summary+= newLine;
							lineBreak = 0;
						}
					}
					else {
						summary+= readTrimmedOGCase.get(checkNxt)+" ";
						
						checkNxtBool = true;
					}
				}
				
				
				
				
				
			}
		}
		
		
		this.summary = summary;
	}
	
	
}
