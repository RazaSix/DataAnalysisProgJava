package classes;

import java.net.*;
import java.io.*;

public class JavaUrlConnectionReader {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 //String output  = getUrlContents("https://www.dictionary.com/browse/james?s=t");
		//String output  = getUrlContents("https://www.merriam-webster.com/dictionary/combine");
		String output  = getUrlContents("https://www.yourdictionary.com/orange");
		    System.out.println(output);
	}

	
	
	 private static String getUrlContents(String theUrl)
	  {
	    StringBuilder content = new StringBuilder();

	    // many of these calls can throw exceptions, so i've just
	    // wrapped them all in one try/catch statement.
	    try
	    {
	      // create a url object
	      URL url = new URL(theUrl);

	      // create a urlconnection object
	      URLConnection urlConnection = url.openConnection();

	      // wrap the urlconnection in a bufferedreader
	      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

	      String line;

	      // read from the urlconnection via the bufferedreader
	      while ((line = bufferedReader.readLine()) != null)
	      {
	        content.append(line + "\n");
	      }
	      bufferedReader.close();
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    return content.toString();
	  }
}
