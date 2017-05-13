package TP3;

public class IceArtistProblem {
	int requiredLength;
	int[] availableBlocks;
	
	IceArtistProblem (int requiredLength, int[] availableBlocks) {
		this.requiredLength = requiredLength;
		this.availableBlocks = new int[availableBlocks.length];
		for (int i = 0; i < availableBlocks.length; i++)
			this.availableBlocks[i] = availableBlocks[i];
	}
	
	public int solveProblem () {
		int i, j;
		int[][] dinamicArray = new int[availableBlocks.length][requiredLength + 1];
		
		for (i = 1; i <= requiredLength; i++)
			dinamicArray[0][i] = i;
		
		for (i = 1; i < availableBlocks.length; i++) {
			for (j = 1; j <= requiredLength; j++) {
				if (availableBlocks[i] > j)
					dinamicArray[i][j] = dinamicArray[i - 1][j];
				else
					dinamicArray[i][j] = dinamicArray[i - 1][j - ((j / availableBlocks[i]) * availableBlocks[i])] + (j / availableBlocks[i]);
			}
		}
		
		return dinamicArray[availableBlocks.length - 1][requiredLength];
	}
}
