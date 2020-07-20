package classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
/*
import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.input.BOMInputStream;
*/
public class NumFileProcessing {

	//For file processing
	//# Use the process from csvRead
	
	//List to hold intial data read from file in string format
	private List<List<String>> records = new ArrayList<>();
	
	//Arraylist to hold read csv data as strings
	private ArrayList<ArrayList<Double>> csvDoubleData = new ArrayList<ArrayList<Double>>();
	
	//Arraylist to hold read csv data as strings
	private ArrayList<String> titleHeaders = new ArrayList<String>();
	
	//Arraylist to hold columns as arraylist of arraylisyts
	private ArrayList<ArrayList<Double>> csvDoubleColumnData = new ArrayList<ArrayList<Double>>();
	
	//Copy of data (all columns) for sorting
	//So sorting doesn't mess up main arraylists
	private ArrayList<ArrayList<Double>> csvDoubleColumnDataSorted = null;
	
	private int numOfCols;
	
	private boolean errorFileProcessing = false;
	private String errorReason;
	private boolean fileHasHeaders = false;
	
	
	//Constructor
	public NumFileProcessing(String fileName) {
		int headerCheckResult = 0;
		if(readFile(fileName) == true){
			//System.out.println(this.records.toString());
			
			//Checks if the file has text lables at the top
			if(checkTxtHeaders(this.records) == true) {
				//Headers if any
				//System.out.println(this.titleHeaders);
				headerCheckResult = 1;
			}
			else {
				
			}
			
			
			//Regardless of the result
			try {
				convToDouble(this.records,headerCheckResult);
				convToCols(this.csvDoubleData);
				
				csvDoubleColumnDataSorted = new ArrayList<ArrayList<Double>>(this.csvDoubleColumnData);
				for(int arrSize = 0; arrSize < csvDoubleColumnDataSorted.size(); arrSize++) {
					Collections.sort(csvDoubleColumnDataSorted.get(arrSize));
				}
				/* Test Prints */
				/*
				System.out.println("Printing Test Data from File (in constructor)");
				System.out.println("First 5 values in the first column");
				System.out.print(csvDoubleColumnData.get(0).get(0)+",");
				System.out.print(csvDoubleColumnData.get(0).get(1)+",");
				System.out.print(csvDoubleColumnData.get(0).get(2)+",");
				System.out.print(csvDoubleColumnData.get(0).get(3)+",");
				System.out.print(csvDoubleColumnData.get(0).get(4)+"\n");
				*/
				
			}catch(Exception e) {
				//System.out.println("Not the expected data file");
				this.errorReason = "Not the expected data file";
				this.errorFileProcessing = true;
			}

			
			
			
			
			
		}
		else {
			//System.out.println("Could not read file- printed from constructor");
			this.errorReason = "Could not read file- printed from constructor";
			this.errorFileProcessing = true;
		}
	}
	
	
	/* Getting Headers */
	public ArrayList<String> getHeaders() {
		return this.titleHeaders;
	}
	
	// Returns boolean value. if there is an error => true
	public boolean getErrorCheck() {
		return this.errorFileProcessing;
	}
	
	// Text reason for error if it occurs
	public String getErrorReason() {
		return this.errorReason;
	}
	
	// Returns true if file has headers
	public boolean getCheckIfFileHasHeaders() {
		return this.fileHasHeaders;
	}
	
	//Data is returned row by row
	public ArrayList<ArrayList<Double>> getRowData() {
		return this.csvDoubleData;
	}
	
	//Data is returned column by column
	public ArrayList<ArrayList<Double>> getColumnData() {
		return this.csvDoubleColumnData;
	}
	
	public int getNumofCols() {
		this.numOfCols = this.csvDoubleColumnData.size();
		
		return this.numOfCols;
	}
	
	
	public ArrayList<ArrayList<Double>> getColumnsSorted(){
		return this.csvDoubleColumnDataSorted;
	}
	
	//Read filename and put into List "records"
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
    			
    		}
    		
    		finStream.close();
    		
			

		}catch(Exception ioe) {
			System.out.println("Found UTF-8 BOM but could not skip");
		}
		

		return result;
	}
	
	
	
	//Convert data to "double" values
	public void convToDouble(List<List<String>> inRrecords, int headerCheck) {
		// Converts from file csv String to double and adds to arraylist "datalist"
    	
    	int i = 0;
    	i+=headerCheck;
    	
    	boolean textCheck = false;
    	
    	while(i<inRrecords.size() && textCheck == false) {
    		/* Here is where you check the first row for text headers */
    		
    		//System.out.println("Got Here in convDouble in while loop");
    		
    		//Arraylist to hold all values in a row
    		ArrayList<Double> rowList = new ArrayList<Double>();
    		
    		//Try catch if there is any text within the body of the data nothing will happen
    		
	    		for(int x = 0; x<inRrecords.get(i).size(); x++) {
	    			//Prints each "array row"
	    			//System.out.print(records.get(i).get(x)+",");
	    			
	    			
	    			try {
	    				rowList.add(Double.parseDouble(inRrecords.get(i).get(x)));
	    			}catch(Exception e)
		    		{
	    				this.errorReason = "Not the expected data file";
	    				this.errorFileProcessing = true;
	    				
	    				
		    			//not a double
		    			//do nothing
		    			//System.out.println("Caught text in file");
		    			textCheck = true;
					}
	    			
	    			
	    			
	    		}
	    		//System.out.println();
	    		this.csvDoubleData.add(rowList);
	    		
    		
    		
    		
    		i++;
    	}
	}
	
	//Check row for text
	public boolean checkTxtHeaders(List<List<String>> inRrecords) {
		boolean foundHeaders = false;
		
		try{
			Double test = Double.parseDouble(inRrecords.get(0).get(0));
		}catch(NumberFormatException e)
		{
			this.titleHeaders.addAll(inRrecords.get(0));

			foundHeaders = true;
			this.fileHasHeaders = true;
		}
		
		return foundHeaders;
	}
	
	
	//Split data into columns
	//Zero is used to specify a row number (first row) but which row doesn't matter as all rows will have the same columns
	//Sorts through data and converts them into columns
	//Data is currently read in horizontally (rows) and converted to vertical columns
	public void convToCols(ArrayList<ArrayList<Double>> inCsvDoubleData) {
    	for(int w = 0; w<inCsvDoubleData.get(0).size(); w++) {

    		//Prints Each Column Consecutively- Puts them one by one into arraylists
    		//System.out.println("Column No."+(w+1));
    		//Loops through each "row" and prints the first value i.e. "column"
    		
    		//column arraylist
    		ArrayList<Double> colList = new ArrayList<Double>();
    		
	    	for(int v = 0; v<inCsvDoubleData.size(); v++) {
	    		//Print the whole arrayList of arrayList within arrayList
	    		//System.out.println(dataList.get(v));
	    		
	    		
	    		//Print just a column at a time		
	    		//System.out.println(csvStringData.get(v).get(w));
	    		

	    		//Add the value to its relevant column
	    		colList.add(inCsvDoubleData.get(v).get(w));
	    		
	    	}
	    	//Column data is added to arraylist: Arraylist [(Col1),(Col2),(Col3)]
	    	this.csvDoubleColumnData.add(colList);
    	}
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
