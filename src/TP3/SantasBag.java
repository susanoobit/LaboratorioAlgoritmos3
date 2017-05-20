package TP3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SantasBag {
	public static int capacity = 50;
	
	public static void main(String[] args) throws Exception {
		ArrayList<ArrayList<SantasPackage>> testCases = readInput();
		for (int i = 0; i < testCases.size(); i++) {
			solveProblem(testCases.get(i));
			if (i < testCases.size() - 1)
				System.out.println("");
		}
	}
	
	public static ArrayList<ArrayList<SantasPackage>> readInput () throws IOException {
		Scanner in = new Scanner(System.in);
		
		int testCaseQnt = Integer.valueOf(in.nextLine().trim()); 
		ArrayList<ArrayList<SantasPackage>> testCases = new ArrayList<ArrayList<SantasPackage>>();
		
		for (; testCaseQnt > 0; testCaseQnt--) {
			ArrayList<SantasPackage> santasPackages = new ArrayList<SantasPackage>();
			int packageQnt = Integer.valueOf(in.nextLine().trim());
			for (; packageQnt > 0; packageQnt--) {
				String[] line = in.nextLine().trim().split(" ");
				santasPackages.add(new SantasPackage(Integer.valueOf(line[0]), Integer.valueOf(line[1])));
			}
			testCases.add(santasPackages);
		}
		
		in.close();
		return testCases;
	}
	
	public static void solveProblem (ArrayList<SantasPackage> santasPackages) throws Exception {
		// ordering by weight
		santasPackages.sort((sp1, sp2) -> sp1.weight - sp2.weight);
		
		int i, j, k, l = 0;
		int[][] dinamicArray = new int[santasPackages.size()][capacity + 1];
		
		for (j = 1; j <= capacity; j++)
			dinamicArray[0][j] = j >= santasPackages.get(0).weight ? santasPackages.get(0).toysQnt : 0;
		
		for (i = 1; i < santasPackages.size(); i++) {
			for (j = 1; j <= capacity; j++) {
				if (j < santasPackages.get(i).weight)
					dinamicArray[i][j] = dinamicArray[i - 1][j];
				else
					dinamicArray[i][j] = Math.max(dinamicArray[i - 1][j],
						dinamicArray[i - 1][j - santasPackages.get(i).weight] + santasPackages.get(i).toysQnt);

			}
		}
		
		for (i = santasPackages.size() - 1, j = dinamicArray[i].length - 1; i > -1 && j > -1; i--) {			
			if (i != 0 && dinamicArray[i][j] == dinamicArray[i - 1][j]) continue;
			else if (i == santasPackages.size() - 1 && dinamicArray[i][j] != dinamicArray[i - 1][j]) {
				for (k = dinamicArray[i][j]; k == dinamicArray[i][dinamicArray[i].length - 1]; j--, k = dinamicArray[i][j]);
				j++;
			}
			
			k = santasPackages.get(i).weight;
			for (; j > -1; j--, k--) {
				if (k == 0) {
					santasPackages.get(i).used = true;
					if (i != 0) {
						if (dinamicArray[i][j] == dinamicArray[i - 1][j]) break;
						else throw new Exception("Oh-oh.");
					}
				}
			}
		}
		
		System.out.println(dinamicArray[santasPackages.size() - 1][capacity] + " brinquedos");
		System.out.println("Peso: " + santasPackages.stream().filter(sp -> sp.used).mapToInt(sp -> sp.weight).sum() + " kg");
		System.out.println("sobra(m) " + santasPackages.stream().filter(sp -> !sp.used).count() + " pacote(s)");
	}

	private static void printTable(int[][] dinamicArray) {
		for (int i = 0; i < dinamicArray.length; i++) {
			for (int j = 0; j < dinamicArray[i].length; j++)
				System.out.print(dinamicArray[i][j] + "\t");
			System.out.println("");
		}
		
	}
}
