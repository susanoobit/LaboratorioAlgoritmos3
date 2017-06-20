package TP2;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class tp2_lab3 {
	// Gera um arquivo CSV
	public static void main(String[] args) throws Exception {
		final File folder = new File("../archive");
		System.out.print("nome da entrada\tmelhor caminho\tpeso\ttempo gasto (ns)");
		for (final File file : folder.listFiles()) {
			
			FileReader fr = new FileReader(file);
			BufferedReader in = new BufferedReader(fr);
			
			int verticeQnt = Integer.parseInt(in.readLine().trim());
			Graph2 graph = new Graph2(verticeQnt);
			
			String line = in.readLine();
			while (line != null) {
				String[] data = line.split("\t");
				graph.addEdge(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]));
				graph.addEdge(Integer.parseInt(data[1]), Integer.parseInt(data[0]), Integer.parseInt(data[2]));
				line = in.readLine();
			}
			
			long start = System.nanoTime();
			Graph2.ClosedLoop best_loop = graph.PCV(0);
			long elapsedTime = System.nanoTime() - start;
			System.out.print("\n" + file.getName() + "\t");
			graph.printClosedLoopCSV(best_loop);
			System.out.print("\t" + elapsedTime);
			graph = null;
			best_loop = null;
			
			in.close();
		}	
	}	
}
