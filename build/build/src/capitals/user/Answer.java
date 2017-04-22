package capitals.user;

import capitals.Country;

public class Answer {

	public static boolean isCorrect(String capitalName, String userInput)
	{
		if (capitalName.contains("Kyiv")) 
			return (levenshteinDistance(userInput, "Kyiv") < 2);
		return (capitalName.equalsIgnoreCase(userInput) || levenshteinDistance(capitalName, userInput) < 2);
	}
	
	private static int levenshteinDistance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }
	
	//Deprecated
	private static int hammingDistance(String s1, String s2)
	{
		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();
		int distance = 0;
		int i = 0;
		while (i < Math.min(s1.length(), s2.length()))
		{
			if (s1.charAt(i) != s2.charAt(i))
				distance++;
			i++;
		}	
		distance += Math.max(s1.length(), s2.length()) - i;
		return distance;
	}
}
