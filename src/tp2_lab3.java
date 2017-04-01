import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class tp2_lab3 {
	public static void main(String[] args) throws Exception {
		FileReader fr = new FileReader("archive/k10.txt");
		BufferedReader in = new BufferedReader(fr);
		
		int verticeQnt = Integer.parseInt(in.readLine().trim());
		Graph2 graph = new Graph2(verticeQnt);
		
		String line = in.readLine();
		while (line != null) {
			String[] data = line.split("\t");
			graph.addEdge(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]));
			line = in.readLine();
		}
		
		graph.print();
		graph.DFS();
		graph.DFS2(0);
		System.out.println("Os caminhos existentes no grafo são:\n");
		for (int i = 0; i < graph.closed_loops.length; i++) {
			System.out.println("Loop " + i);
			graph.printClosedLoop(graph.closed_loops[i]);
			System.out.println("");
			
		}
		
		Graph2.ClosedLoop best_loop = graph.getBestLoop();
		System.out.println("\nO menor caminho é:");
		graph.printClosedLoop(best_loop);
		
		in.close();
	}	
}
