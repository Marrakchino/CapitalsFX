package capitals.data;

import capitals.Country;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class FactReader {

	private Country country;
	private String filePath;
	
	private FactReader(Country country)
	{
		this.country = country;
		this.filePath = FactParser.FACTS_FOLDER_PATH + country.getCountryName().toLowerCase() + ".txt";
	}
	
	//TODO change the prototype of FactParser.getFacts to directly return a List, and keep up-dealing with it
	// instead of using "#" dumb delimiters!!!!
	public static List<String> getFacts(Country country) throws FileNotFoundException
	{
		FactReader factReader = new FactReader(country);
		FactParser.prepareFacts(country);
		
		List<String> factList = new LinkedList<String>(); 

		File factFile = new File(factReader.filePath);
		Scanner reader = new Scanner(factFile);
		reader.useDelimiter("#");
		String nextFact;		
		while(reader.hasNext())
		{
			nextFact = reader.next();
			if (!nextFact.isEmpty()) // no need to add empty lines
			{
				truncateAndAddFact(factList, nextFact);
			}
		}
		reader.close();
		return factList;
	}
	
	private static void truncateAndAddFact(List<String> factList, String fact)
	{
		if (fact.contains(";")) fact = fact.substring(0, fact.indexOf(";"));
		if (fact.contains("growth")) fact = fact.substring(0, fact.indexOf("growth") -2);
		if (fact.contains("area")) 
			fact = new String(fact.substring(0, fact.indexOf(":") + 2) + fact.substring(fact.indexOf("(") + 1, fact.indexOf(")")));
		factList.add(fact);
	}
}
