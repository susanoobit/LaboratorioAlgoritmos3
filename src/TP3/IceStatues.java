package TP3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class IceStatues {

	public static void main(String[] args) throws IOException {
		ArrayList<IceArtistProblem> problems = readInput();
		problems.stream().mapToInt(p -> p.solveProblem()).forEach(v -> System.out.println(String.valueOf(v)));
	}
	
	public static ArrayList<IceArtistProblem> readInput () throws IOException {
		Scanner in = new Scanner(System.in);
		
		int instanceQnt = Integer.valueOf(in.nextLine().trim()); 
		ArrayList<IceArtistProblem> problems = new ArrayList<IceArtistProblem>();
		
		for (; instanceQnt > 0; instanceQnt--) {
			String[] line = in.nextLine().trim().split(" ");
			int[] availableBlocks = new int[Integer.valueOf(line[0])];
			int requiredLength = Integer.valueOf(line[1]);
			line = in.nextLine().trim().split(" ");
			for (int i = 0; i < line.length; i++)
				availableBlocks[i] = Integer.valueOf(line[i]);
			problems.add(new IceArtistProblem(requiredLength, availableBlocks));
		}
		
		in.close();
		return problems;
	}

}
