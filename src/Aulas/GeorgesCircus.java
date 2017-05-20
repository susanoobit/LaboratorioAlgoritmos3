package Aulas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GeorgesCircus {

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("in.txt"));
		
		String line = in.readLine();
		while (line != null) {
			
		}
		
		BufferedWriter out = new BufferedWriter(new FileWriter("out.txt"));
	}
	
	public static int bestProfit (int earns[], int dayCost) {
		int[] sums = new int[earns.length];
		sums[0] = earns[0] - dayCost;
		int max = sums[0];
		//int maxPos = 0;
		
		for (int i = 1; i <= earns.length; i++) {
			int actualSum = earns[i] - dayCost - sums[i - 1];
			if (actualSum > earns[i])
				sums[i] = actualSum;
			else
				sums[i] = earns[i];
			
			if (sums[i] > max) {
				//maxPos = i;
				max = sums[i];
			}
		}
		
		/*
		String days = "";
		for (int i = maxPos; i > -1; i--) {
			days += i;
			if (sums[i] == earns[i])
				break;
		}
		
		days = new StringBuilder(days).reverse().toString();
		*/
		
		return max;
	}

}
