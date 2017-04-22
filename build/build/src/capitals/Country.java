package capitals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public final class Country {

	private final String countryName;
	private final String capitalName;
	private static final String CAPITALS_FILE_PATH = "\\data\\capitals.txt";
	
	/**
	 * Random Country constructor from the capitals file 
	 * @throws IOException 
	 */
	public Country() throws IOException
	{
		Random r = new Random();
		BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir") + CAPITALS_FILE_PATH));
		String line = reader.readLine();
		List<String> lines = new ArrayList<String>();
		while (line != null) {
		     lines.add(line);
		     line = reader.readLine();
		}
		String randomLine = lines.get(r.nextInt(lines.size()));
		String[] countryAndCapital = randomLine.split(":");
		this.countryName = countryAndCapital[0];
		this.capitalName = countryAndCapital[1];
		reader.close();
	}
	
	public static Country getCountry() throws IOException
	{
		return new Country();
	}
	
	public Country(String countryName, String capitalName) {
		this.countryName = countryName;
		this.capitalName = capitalName;
	}
	
	public Country(String countryName){
		this.countryName = countryName;
		this.capitalName = this.getCorrespondingCapital(countryName);
	}
	
	private String getCorrespondingCapital(String countryName) {
		String correspondingLine = this.getCorrespondingLine(countryName);
		return correspondingLine.substring(correspondingLine.indexOf(":") + 1);
	}
	
	private String getCorrespondingLine(String countryName) {
	    try {
	    	// System.getProperty("user.dir") saved my soul (read: night sleep)
	    	FileReader fileReader = new FileReader(System.getProperty("user.dir") + CAPITALS_FILE_PATH);
	    	BufferedReader bufferedReader = new BufferedReader(fileReader);
	    	String line = "";
	        while((line = bufferedReader.readLine()) != null) {
	            if (line.contains(countryName)) {   
	            	bufferedReader.close();
	            	return line;
	            }
	        }
	        bufferedReader.close();
	    } 
	    catch (FileNotFoundException fnfe) {
			throw new IllegalArgumentException("File not found: " + CAPITALS_FILE_PATH);
		}
	    catch (IOException ioe) {
	    	ioe.printStackTrace();
	    }
	    return "";
	}

	public String getCountryName() {
		return this.countryName;
	}

	public String getCapitalName() {
		return this.capitalName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countryName == null) ? 0 : countryName.hashCode()); 
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Country)
			&& (((Country)obj).getCountryName().equals(this.getCountryName()));
	}
	
}
