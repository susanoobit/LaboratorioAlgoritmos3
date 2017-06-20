package Avaliacao2;

import java.util.Scanner;
import java.util.stream.Collectors;

public class PalindromesGame {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		String line;
		
		while (sc.hasNextLine()) {
			line = sc.nextLine().trim();
			if (line.length() > 1000) throw new Exception("Invalid input.");
			int lettersToAdd = (int) line
				.chars()
				.boxed()
				.collect(Collectors.toMap(
					k -> Character.valueOf((char) k.intValue()),
					v -> 1,
					Integer::sum))
				.values()
				.stream()
				.filter(i -> i.intValue() % 2 > 0)
				.count() - 1;
			if (lettersToAdd < 0) lettersToAdd = 0;
			System.out.println(lettersToAdd);
		}
		
		sc.close();
	}

}
