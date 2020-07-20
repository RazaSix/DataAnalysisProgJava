package classes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

public class statsCalc {
	
	public statsCalc() {
		
	}
	
	
	//Get Mean
	public double getMean(ArrayList<Double> inList) {
		double mean = 0, sum = 0;;
		
		for(int c = 0; c < inList.size(); c++) {
			sum+=inList.get(c);
		}
		
		mean = sum/inList.size();
		return mean;
		
	}
	
	
	//Gets Sample Mean
	public double getSampleMean(ArrayList<Double> inList) {
		double mean = 0, sum = 0;;
		
		for(int c = 0; c < inList.size(); c++) {
			sum+=inList.get(c);
		}
		
		mean = sum/(inList.size()-1);
		return mean;
		
	}
	
	
	//Gets all most common elements even when more than one share the same number of occurrences
	public ArrayList<Double> getMode(ArrayList<Double> inList) {
		//Will contain list of most common elements
		ArrayList<Double> mode = new ArrayList<Double>();
			
		//Will contain list of previously tested elements to prevent duplication in the results
		ArrayList<Double> testedElements = new ArrayList<Double>();
			
		//Currently tested element
		double testedElement = 0;
		//Previous Most Common Element Count && Current Element Occurrence Count 
		int prevMostCommonElem = 0, currElemCount = 0;
		for(int n = 0; n< inList.size(); n++) 
		{
			//Get values in array to cycle through
			testedElement = inList.get(n);
			for(int i = 0; i< inList.size(); i++) 
			{
				//Count each occurrence of selected element in outer loop
				if(testedElement== inList.get(i)) 
				{
					currElemCount++;
				}
			}
		//System.out.println(testedElement+" occurs "+currElemCount+" times");

			
		//This if runs the 1st time always because current count is 0 and any new number will have a higher count
		//Will also run and clear previous candidates if a new higher number is encountered
		if(currElemCount>prevMostCommonElem  && !testedElements.contains(testedElement)) {
				prevMostCommonElem = currElemCount;
				mode.clear();
				mode.add(testedElement);
				//System.out.println("A");
				//System.out.println(testedElement+" occurs "+currElemCount+" times");
			}
			//This if runs if another element that has the same number of occurrences if found
			//This element is added to an arraylist that contains all found elements
			else if(currElemCount==prevMostCommonElem && !testedElements.contains(testedElement)) {
				mode.add(testedElement);
				//System.out.println("B");
				//System.out.println(testedElement+" occurs "+currElemCount+" times");
			}
			
			//resets element count so previous elements aren't counted together
			currElemCount = 0;
			//Adds tested elements to array so they are skipped in the subsequent passes
			testedElements.add(testedElement);
		}
		
		return mode;
	}
	
	
	public double getMedian(ArrayList<Double> inList) {
		double median = 0;
		
		int arraySize = inList.size();
		double rem = (arraySize+1) % 2;
		int pos = (arraySize+1)/2;
		
		//System.out.println("Array Size: "+arraySize);
		//System.out.println("Calculated Position: "+pos);
		
		//If obvious median (odd number of elements)
		if(rem == 0.0) {
			median = inList.get(pos-1);
		}
		else {//If not obvious median (even number of elements)
			
			//get both n & n+1
			//median with even elements is midpoint between the 2 values
			int roundedPosition = Math.round(pos);
			double valueA = inList.get(roundedPosition-1);
			double valueB = inList.get(roundedPosition);
			//System.out.println("Even Number of Elements- Median between "+valueA+" and "+valueB);
			double midpoint = (valueA+valueB)/2;
			
			median = midpoint;
			
		}
		
		
		return median;
	}
	
	
	public double getQuartile1(ArrayList<Double> inList) {
		double q1 = 0;
		int size = inList.size();
		double rem = size%2;
		int arrayEnd = 0;
		if(rem==0) {
			//It's even so split in half is fine
			arrayEnd = (size/2);
		}
		else {
			//it's odd so round down
			//double roundSize = size;
			//roundSize = (size/2);
			//arrayEnd = (int) (roundSize-(roundSize%1));
			
			arrayEnd = (size/2)+1;
		}

		//Creates arraylist of first quarter
		ArrayList<Double> inListCopy = new ArrayList<Double>(inList);
		Collections.sort(inListCopy);
		ArrayList<Double> firstQuarter = new ArrayList<Double>(inListCopy.subList(0, arrayEnd));
		
		//System.out.println("First Quarter:"+firstQuarter.toString());
		q1 = getMedian(firstQuarter);
		
		return q1;
	}
	
	
	public double getQuartile2(ArrayList<Double> inList) {
		double q2 = 0;
		int size = inList.size();
		double rem = size%2;
		int arrayStart = 0;
		if(rem==0) {
			//It's even so split in half is fine
			arrayStart = (size/2);
			
		}
		else {
			//it's odd so round down
			//double roundSize = size;
			//roundSize = (size/2);
			//arrayStart = (int) (roundSize-(roundSize%1));
			
			arrayStart = (size/2)+1;
			
		}
		
		//Creates arraylist of second quarter
		ArrayList<Double> inListCopy = new ArrayList<Double>(inList);
		Collections.sort(inListCopy);
		ArrayList<Double> secondQuarter = new ArrayList<Double>(inListCopy.subList(arrayStart, size));
		//System.out.println("Second Quarter:"+secondQuarter.toString());
		q2 = getMedian(secondQuarter);
		
		return q2;
	}
	
	
	public double getInterQuartileRange(ArrayList<Double> inList) {
		double iqr = getQuartile2(inList) - getQuartile1(inList);
		
		return iqr;
	}
	
	
	//Gets Variance
	// The average of the squared differences from the Mean
	public double getVariance(ArrayList<Double> inList) {
		double variance = 0;
		
		double inMean = getMean(inList);
		
		ArrayList<Double> squaredDiff = new ArrayList<Double>();
		
		//for each number (inList): subtract the Mean and square the result into (squaredDiff)
		for(int i = 0; i < inList.size(); i++) {
			double subtractRes = inList.get(i) - inMean;
			double sqrRes = Math.pow(subtractRes, 2);
			
			squaredDiff.add(sqrRes);
		}
				
		variance = getMean(squaredDiff);

		return variance;
	}
		
		
	public double getPopStandardDeviation(ArrayList<Double> inList) {
		double stDev = 0;
		double variance = getVariance(inList);
		
		stDev = Math.sqrt(variance);
		
		return stDev;
	}
	
	
	public double getSampleStandardDeviation(ArrayList<Double> inList) {
		double stDev = 0;
		double inMean = getMean(inList);
		
		ArrayList<Double> squaredDiff = new ArrayList<Double>();
		
		//for each number (inList): subtract the Mean and square the result into (squaredDiff)
		for(int i = 0; i < inList.size(); i++) {
			double subtractRes = inList.get(i) - inMean;
			double sqrRes = Math.pow(subtractRes, 2);
			
			squaredDiff.add(sqrRes);
		}
		
		
		double sqrDiffMean = getSampleMean(squaredDiff);
		
		stDev = Math.sqrt(sqrDiffMean);
		
		return stDev;
	}
	
	
	public double roundNum(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	 
	    BigDecimal bd = new BigDecimal(Double.toString(value));
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public int getNumberOfDecimalPlaces(BigDecimal bigDecimal) {
	    String string = bigDecimal.stripTrailingZeros().toPlainString();
	    int index = string.indexOf(".");
	    return index < 0 ? 0 : string.length() - index - 1;
	}
	
	
	//Get Range (largest number - smallest)
	public double getRange(ArrayList<Double> inList) {
		double range = 0,  min = 0, max = 0;;
		//int arrayListSize = inList.size();
		
		//min = inList.get(0);
		//max = inList.get(arrayListSize-1);
		
		min = Collections.min(inList);
		max = Collections.max(inList);
		range = max - min;
		
		return range;
	}

}
