package application.test;

import application.data.FactParser;
import application.Country;

public class TestFactParser {

	public static void main(String[] args) {
		Country france = new Country("France", "Paris");
		FactParser.prepareFacts(france);
	}

	public static void delimiter()
	{
		System.out.println("\n\n\n\n\n\n\n\n");
	}
}
