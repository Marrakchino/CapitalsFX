package capitals.data;

import capitals.Country;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FactParser {
	
	protected Country country;
	protected static Document document;
	protected static final String FACTS_URL = "http://www.factmonster.com/country/";
	public static final String FACTS_FOLDER_PATH = "facts\\";
		
	protected FactParser(Country country)
	{
		this.country = country;
		setDocumentFromHtml(country.getCountryName());
	}
	
	private void setDocumentFromHtml(String countryName)
	{
		try {
			document = getDocumentFromHtml(countryName);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void prepareFacts(Country country)
	{
		FactParser factParser = new FactParser(country);
		File factFile = new File(FACTS_FOLDER_PATH + country.getCountryName().toLowerCase() + ".txt");
		if (factFile.exists() && factFile.getTotalSpace() > 0)
			return;
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(factFile));
			writer.write(getFacts(country));
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String getFacts(Country country) throws Exception
	{
		String facts = new String();
		for (String fact : getUsefulInfo())
		{
			facts += fact;
			facts += "#";
		}
		return facts;
	}

	private static List<String> getUsefulInfo() throws Exception
	{
		List<String> usefulInfoList = new LinkedList<String>();
			
		String[] classes = {"president", "other-leader", "prime-mininster",
					"area", "population", "money-unit"}; // removed "largest-cities"
		String fetchedInfo;
		String addedInfo = "";
		for (String className : classes)
		{
			fetchedInfo = getInfoByClass(className);
			if (className.equals("population"))
				addedInfo = fetchedInfo.substring(0, fetchedInfo.indexOf("growth") - 2);
			else if (className.equals("area"))
				addedInfo = fetchedInfo.substring(0, fetchedInfo.indexOf(";"));
			else
				addedInfo = fetchedInfo;
			if (!addedInfo.isEmpty())
				usefulInfoList.add(addedInfo);
		}
		
		return usefulInfoList;
	}
	
	private static String getInfoByClass(String className)
	{
		try {
			return document.getElementsByClass(className).text();
		} catch (Exception e){
			System.out.println(e);
			return "";
		}
	}
	
	private static Document getDocumentFromHtml(String countryName)
	{
		try {
			return Jsoup.parse(getRawHtmlPage(countryName));
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	private static String getRawHtmlPage(String countryName) throws Exception {	
		URL url = null;
		BufferedReader reader = null;
		StringBuilder stringBuilder;
		
		String sourceUrl;
		if (!countryName.contains(" "))
			sourceUrl = new String(FACTS_URL + countryName + ".html").toLowerCase();
		else
			sourceUrl = new String(FACTS_URL + countryName.replaceAll(" ", "-") + ".html").toLowerCase();
		
		try
		{
			// create the HttpURLConnection
			url = new URL(sourceUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	      
			// just want to do an HTTP GET here
			connection.setRequestMethod("GET");
	      
			// give it 15 seconds to respond
			connection.setReadTimeout(15*1000);
			connection.connect();
	
			// read the output from the server
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			stringBuilder = new StringBuilder();
	
			String line = null;
			while ((line = reader.readLine()) != null)
			{
				stringBuilder.append(line + "\n");
			}
			return stringBuilder.toString();
	    }
		catch (FileNotFoundException fileNotFoundException)
		{
		}
	    catch (Exception e)
	    {
	    	e.printStackTrace();
	    	throw e;
	    }
	    finally
	    {
	    	// close the reader; this can throw an exception too, so
	    	// wrap it in another try/catch block.
	    	if (reader != null)
	    	{
	    		try
	    		{
	    			reader.close();
	    		}
	    		catch (IOException ioe)
	    		{
	    			ioe.printStackTrace();
	    		}
	    	}
	    }
	}

}
