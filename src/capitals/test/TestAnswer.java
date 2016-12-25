package capitals.test;

import capitals.user.Answer;

public class TestAnswer {

	public static void main(String[] args) {
		String[] expected = {"Rabat", "Paris", "Manilla", "Bangkok", "Pyongyang", "Tokyo"};
		String[] got = {"Raba", "Paris", "Manille", "Bankok", "Pyong yang", "Kyoto"};
		
		for (int i = 0; i < expected.length; i++)
			System.out.println("expected: " + expected[i] + " - got: " + got[i] + " - Distance: " + Answer.isCorrect(expected[i], got[i]));

	}

}
