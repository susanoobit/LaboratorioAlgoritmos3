package Avaliacao2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BatataQuente {
	public static void main (String[] args) throws Exception {
		readInput();
		intervals.forEach(BatataQuente::solve);
	}
	
	private static int[] labels;
	private static ArrayList<ArrayList<Integer>> intervals = new ArrayList<ArrayList<Integer>>();
	
	private static void solve (ArrayList<Integer> interval) {
		for (int i = interval.get(0); i <= interval.get(1); i++) {
			System.out.println(computeProbability(i));
		}
	}
	
	private static float computeProbability (int start) {
		float probability = 0;
		for (int i = 1; i <= labels.length; i++)
			probability += hasSolution(start, i) ? 1 : 0;
		
		probability /= labels.length;
		
		return probability;
	}
	
	private static boolean hasSolution (int start, int position) {
		int maxCount = labels.length, nextStudent = start;
		boolean off = false;
		ArrayList<Integer> visited = new ArrayList<Integer>();
		while (maxCount --> 0) {
			for (int i = 0; i < visited.size(); i++) {
				if (visited.get(i).intValue() == nextStudent) {
					off = true;
					break;
				}
			}
			if (off) break;
			nextStudent = labels[nextStudent - 1];
			visited.add(nextStudent);
		}
		
		if (maxCount != 0) return true;
		return false;
	}
	
	private static void readInput () throws Exception {
		Exception invalidInput = new Exception("Invalid input!");
		Scanner sc = new Scanner(System.in);
		
		String[] line = sc.nextLine().split(" ");
		int n = Integer.parseInt(line[0]);
		if (n < 2 || n > 50000) throw invalidInput;
		long q = Long.parseLong(line[1]);
		if (q < 1 || q > Math.pow(10, 15)) throw invalidInput;
		
		labels =
			Arrays.asList(sc.nextLine().split(" "))
				.stream()
				.mapToInt(Integer::parseInt)
				.toArray();
		
		for (int value : labels)
			if (value < 1 || value > n) throw invalidInput;

		for (long i = 0; i < q; i++) {
			intervals.add(Arrays.asList(sc.nextLine().split(" "))
				.stream()
				.mapToInt(Integer::parseInt)
				.mapToObj(Integer::valueOf)
				.collect(Collectors.toCollection(ArrayList::new)));
			
			// santa m�e de deus, ArrayList s� aceita int de tamanho D:
			if (intervals.get((int) i).get(0) < 1 ||
					intervals.get((int) i).get(0) > intervals.get((int) i).get(1) ||
					intervals.get((int) i).get(1) > n)
				throw invalidInput;
		}
		
		sc.close();
	}
}
