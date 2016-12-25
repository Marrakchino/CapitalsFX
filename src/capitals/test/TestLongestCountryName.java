package capitals.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// RESULT: Saint Vincent and the Grenadines
public class TestLongestCountryName {

	public static void main(String[] args)
	{
		try {
			String longestCountryName = "";
			int longestCountryNameLength = 0;
	    	FileReader fileReader = new FileReader(System.getProperty("user.dir") + "\\data\\capitals.txt");
	    	BufferedReader bufferedReader = new BufferedReader(fileReader);
	    	String line = "";
	        while((line = bufferedReader.readLine()) != null) {
	        	// System.out.println("longestCountryName" + longestCountryName);
	            if (line.substring(0, line.indexOf(":")).length() > longestCountryNameLength)
	            {
		        	longestCountryName = line.substring(0, line.indexOf(":"));
	            	longestCountryNameLength = longestCountryName.length();
	            }
	        }
	        bufferedReader.close();
	        System.out.println(new String(longestCountryName + ":" + longestCountryNameLength));
	    } 
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
    }
}
