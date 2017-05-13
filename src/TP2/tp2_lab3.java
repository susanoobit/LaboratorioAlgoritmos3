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
		
		
		
		/*
		Graph2.ClosedLoop[] best_loops = graph.PCV2(0);
		long elapsedTime = System.currentTimeMillis() - start;
		System.out.println("Os caminhos existentes no grafo são:");
		for (int i = 0; i < graph.closed_loops.length; i++) {
			System.out.print("\nLoop " + i);
			graph.printClosedLoop(graph.closed_loops[i]);
			System.out.print("\n");
			
		}
		System.out.println("\n\n\n===========================================\n\n");
		if (best_loops.length == 0) {
			System.out.println("\nO menor caminho é:");
			graph.printClosedLoop(best_loops[0]);
		}
		else {
			System.out.println("\nOs " + best_loops.length + " menores caminhos são:");
			for (int i = 0; i < best_loops.length; i++) {
				graph.printClosedLoop(best_loops[i]);
				System.out.print("\n");
			}
		}
		*/	
	}	
}
