package Aulas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Bus {
	
	private static int numberOfDigitsToShow = 6;
	
	private static class Problem {
		
		public final static long maxValue = (long) Math.pow(10, 15);
		public final static int busLength = 10, mBusLength = 5;
		long totalLength, busColorQuantity, mBusColorQuantity;
		
		Problem (long totalLength, long mBusColorQuantity, long busColorQuantity) {
			this.totalLength = totalLength;
			this.busColorQuantity = busColorQuantity;
			this.mBusColorQuantity = mBusColorQuantity;
		}
	}
	
	public static void main(String[] args) throws Exception {
		readInput2().stream().forEach(Bus::solveProblem);
	}
	
	private static void solveProblem (Problem problem) {
		long arrangementQuantity = 0;
		long maxNumberOfBus = problem.totalLength / Problem.busLength;
		long maxNumberOfMBus = problem.totalLength / Problem.mBusLength;
		long maxNumberOfMBusAfterBus = problem.totalLength % Problem.busLength / Problem.mBusLength;
		int relationBusMBus = Problem.busLength / Problem.mBusLength;
		
		for (long i = maxNumberOfBus, j = maxNumberOfMBusAfterBus; i > -1; i--, j += relationBusMBus) {
			long mBusColorPossibilities = fat(problem.mBusColorQuantity, j);
			long busColorPossibilities = fat(problem.busColorQuantity, i);
			long permutation = fat(i + j) / (fat(i) * fat(j)); // permutation with repetition
			arrangementQuantity += busColorPossibilities * mBusColorPossibilities * permutation;
		}
		
		// trying to consider not using all the space
		for (long i = maxNumberOfBus - (maxNumberOfMBusAfterBus == 0 ? 1 : 0); i > 0; i--) {
			long busColorPossibilities = fat(problem.busColorQuantity, i);
			long freeSpace = maxNumberOfMBus - (i * relationBusMBus);
			long permutation = fat(i + freeSpace) / (fat(i) * fat(freeSpace)); // permutation with repetition
			arrangementQuantity += busColorPossibilities * permutation;
		}
		
		for (long i = maxNumberOfMBus - 1; i > 0; i--) {
			long mBusColorPossibilities = fat(problem.mBusColorQuantity, i);
			long permutation = fat(maxNumberOfMBus) / (fat(i) * fat(maxNumberOfMBus - i)); // permutation with repetition
			arrangementQuantity += mBusColorPossibilities * permutation;
		}
		
		String print = String.valueOf(arrangementQuantity);
		while (print.length() < numberOfDigitsToShow) print = "0" + print;
		print = print.substring(print.length() - numberOfDigitsToShow);
		System.out.println(print);
	}
	
	private static long fat (long n, long maxMultiplications) {
		if (maxMultiplications == 0 || n < 2) return 1;
		return n * fat(n - 1, maxMultiplications - 1);
	}
	
	private static long fat (long n) {
		if (n < 2) return 1;
		return n * fat(n - 1);
	}
		
	private static ArrayList<Problem> readInput () throws Exception {
		ArrayList<Problem> problems = new ArrayList<Problem>();
		Scanner sc = new Scanner(System.in);
		
		while (sc.hasNextLine()) {
			long[] input = Arrays.asList(sc.nextLine().split(" ")).stream().mapToLong(Long::valueOf).toArray();
			if (input[0] < 5 || input[1] < 1 || input[2] < 1 || input[0] > Problem.maxValue ||
					input[1] > Problem.maxValue || input[2] > Problem.maxValue || input[0] % 5 != 0)
				throw new Exception("Invalid input");
			
			problems.add(new Problem(input[0], input[1], input[2]));
		}
		
		sc.close();
		return problems;
	}
	
	private static ArrayList<Problem> readInput2 () throws Exception {
		ArrayList<Problem> problems = new ArrayList<Problem>();
		FileReader fr = new FileReader("archive/buss-in");
		BufferedReader in = new BufferedReader(fr);
		
		String line = in.readLine();
		while (line != null) {
			long[] input = Arrays.asList(line.split(" ")).stream().mapToLong(Long::valueOf).toArray();
			if (input[0] < 5 || input[1] < 1 || input[2] < 1 || input[0] > Problem.maxValue ||
					input[1] > Problem.maxValue || input[2] > Problem.maxValue || input[0] % 5 != 0)
				throw new Exception("Invalid input");
			
			problems.add(new Problem(input[0], input[1], input[2]));
			line = in.readLine();
		}
		
		in.close();
		return problems;
	}
}
