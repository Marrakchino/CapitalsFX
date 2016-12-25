package application.test;

import application.data.FactReader;
import application.Country;

public class TestFactReader {

	public static void main(String[] args) {
		Country testCountry = new Country("France", "Paris");
		Country testCountry2 = new Country("Philippines");
		
		try {
			System.out.println(FactReader.getFacts(testCountry));
			System.out.println(FactReader.getFacts(testCountry2));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
