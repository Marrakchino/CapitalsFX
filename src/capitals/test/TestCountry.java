package capitals.test;

import java.io.IOException;

import capitals.Country;

public class TestCountry {

	public static void main(String[] args) {

		try
		{
			for (int i = 0; i < 10; i++)
			{
				Country randomCountry = Country.getCountry();
				System.out.println("[DEBUG] " + randomCountry.getCountryName() + " - "  + randomCountry.getCapitalName());
			}
		} catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

}
