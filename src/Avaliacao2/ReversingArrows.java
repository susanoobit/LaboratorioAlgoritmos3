package Avaliacao2;

import java.util.ArrayList;
import java.util.Scanner;

public class ReversingArrows {
	
	private Graph2 graph;
	private int start, end;
	private ArrayList<Edge> edges;

	public ReversingArrows (Graph2 graph, int start, int end, ArrayList<Edge> edges) {
		this.graph = graph;
		this.start = start;
		this.end = end;
		this.edges = edges;
	}
	
	public static class Edge {
		int start;
		int end;
		boolean reversed = false;
		
		Edge (int start, int end) {
			this.start = start;
			this.end = end;
		}

		public boolean equals(Edge edge) {
			return this.start == edge.start && this.end == edge.end;
		}
	}

	public static void main(String[] args) throws Exception {
		ArrayList<ReversingArrows> problems = readInput();
		problems.stream().forEach(ReversingArrows::solveProblem);
	}
	
	private static ArrayList<ReversingArrows> readInput () throws Exception {
		ArrayList<ReversingArrows> problems = new ArrayList<ReversingArrows>();
		Scanner sc = new Scanner(System.in);
		Exception invalidInput = new Exception("Invalid input.");
		
		int oi = 1;
		//while (sc.hasNextLine()) {
		while (oi --> 0) {
			String[] initialArgs = sc.nextLine().split(" ");
			if (Integer.parseInt(initialArgs[0]) < 1 || Integer.parseInt(initialArgs[0]) > 10000 ||
					Integer.parseInt(initialArgs[1]) < 0 || Integer.parseInt(initialArgs[1]) > 500000 ||
					Integer.parseInt(initialArgs[2]) < 1 || Integer.parseInt(initialArgs[2]) > Integer.parseInt(initialArgs[0]) ||
					Integer.parseInt(initialArgs[3]) < 1 || Integer.parseInt(initialArgs[3]) > Integer.parseInt(initialArgs[0]))
				throw invalidInput;
			
			Graph2 graph = new Graph2(Integer.parseInt(initialArgs[0]));
			ArrayList<Edge> edges = new ArrayList<Edge>();
			
			int arrows = Integer.parseInt(initialArgs[1]);
			
			while (arrows --> 0) {
				String[] arrowVertices = sc.nextLine().split(" ");
				if (Integer.parseInt(arrowVertices[0]) < 1 || Integer.parseInt(arrowVertices[0]) > Integer.parseInt(initialArgs[0]) ||
					Integer.parseInt(arrowVertices[1]) < 1 || Integer.parseInt(arrowVertices[1]) > Integer.parseInt(initialArgs[0]))
					throw invalidInput;
				
				graph.addEdge(Integer.parseInt(arrowVertices[0]) - 1, Integer.parseInt(arrowVertices[1]) - 1);
				edges.add(new Edge(Integer.parseInt(arrowVertices[0]) - 1, Integer.parseInt(arrowVertices[1]) - 1));
			}
			
			problems.add(new ReversingArrows(graph, Integer.parseInt(initialArgs[2]), Integer.parseInt(initialArgs[3]), edges));
		}
		
		sc.close();
		return problems;
	}
	
	// IREI ME LAMENTAR POR TODA A VIDA POR TER ESCOLHIDO ITERACAO SEM RECURSAO. FORCA BRUTA ME DOI NA ALMA.
	private static void solveProblem (ReversingArrows problem) {
		int bibi, bibika = bibi = 0;
		boolean hasBibiPath, hasBibikaPath = hasBibiPath = false;
		
		// if there's no edge between start and end, bibi has to wurk
		if (problem.graph.hasEdge(problem.start, problem.end) == 1) hasBibiPath = true;
		else {
			int chunk = 1;
			while (problem.graph.PCV(problem.start, problem.end).path == null
				&& problem.edges.stream().filter(e -> !e.reversed).count() > 0) {
				
				for (int i = 0; i < problem.edges.size() && problem.edges.size() - i >= chunk; i += chunk) {
					
					for (int j = 0; j < chunk; j++) {
						Edge actualEdge = problem.edges.get(i + j);
						actualEdge.reversed = true;
						problem.graph.delEdge(actualEdge.start, actualEdge.end);
						problem.graph.addEdge(actualEdge.end, actualEdge.start);
						bibi++;
					}
					
					if (problem.graph.PCV(problem.start, problem.end).path != null) {
						hasBibiPath = true;
						break;
					}
					
					problem.reset();
					bibi = 0;
				}
				
				chunk++;
			}
			problem.reset();
		}
		
		// if there's no edge between end and start, bibika has to wurk
		if (problem.graph.hasEdge(problem.end, problem.start) == 1) hasBibikaPath = true;
		else  {
			int chunk = 1;
			while (problem.graph.PCV(problem.end, problem.start).path == null
				&& problem.edges.stream().filter(e -> !e.reversed).count() > 0) {
				
				for (int i = 0; i < problem.edges.size() && problem.edges.size() - i >= chunk; i += chunk) {
					
					for (int j = 0; j < chunk; j++) {
						Edge actualEdge = problem.edges.get(i + j);
						actualEdge.reversed = true;
						problem.graph.delEdge(actualEdge.start, actualEdge.end);
						problem.graph.addEdge(actualEdge.end, actualEdge.start);
						bibika++;
					}
					
					if (problem.graph.PCV(problem.end, problem.start).path != null) {
						hasBibikaPath = true;
						break;
					}
					
					problem.reset();
					bibika = 0;
				}
				
				chunk++;
			}
			problem.reset();
		}
		
		if (hasBibiPath && hasBibikaPath) {
			if (bibi > bibika) System.out.println("Bibi: " + bibi);
			else if (bibika > bibi) System.out.println("Bibika: " + bibika);
			else System.out.println("Draw");
		}
		else if (hasBibiPath) System.out.println("Bibi: " + bibi);
		else if (hasBibikaPath) System.out.println("Bibi: " + bibika);
		else System.out.println("Draw");
	}
	
	private void reset () {
		// reseting graph
		this.edges
			.stream()
			.filter(e -> e.reversed)
			.forEach(e -> {
				this.graph.delEdge(e.end, e.start);
				this.graph.addEdge(e.start, e.end);
			});
		
		// reseting edges
		for(int i = 0; i < this.edges.size(); i++)
			this.edges.get(i).reversed = false;
	}
	
	public static class Graph {
		
		class Vertice {
			public int edge;
			public int value;

			Vertice() {
				this.edge = EMPTY;
				this.value = EDGE;
			}
		};

		int num_vertices;
		Vertice matrix[];

		public final int EDGE = 1;
		public final int EMPTY = 0;

		Graph(int num_vertices) {
			this.num_vertices = num_vertices;
			this.matrix = new Vertice[this.num_vertices * this.num_vertices];
			for (int i = 0; i < this.num_vertices * this.num_vertices; i++)
				this.matrix[i] = new Vertice();
		}

		int hasEdge(int v1, int v2) {
			return this.matrix[v1 * this.num_vertices + v2].edge;
		}

		void addEdge(int x, int y, int val) {
			this.matrix[x * this.num_vertices + y].edge = EDGE;
			this.matrix[x * this.num_vertices + y].value = val;
		}

		void delEdge(int x, int y) {
			this.matrix[x * this.num_vertices + y].edge = EMPTY;
			this.matrix[x * this.num_vertices + y].value = 0;
		}

	}
	
	public static class Graph2 extends Graph {
		public class ClosedLoop {
			int weight;
			int path[];
			
			ClosedLoop (int weight, int[] path) {
				this.weight = weight;
				this.path = new int[path.length];
				for (int i = 0; i < path.length; this.path[i] = path[i], i++);
			}
			
			ClosedLoop () {
				this.weight = 0;
			}
		}

		public Graph2(int num_vertices) {
			super(num_vertices);
		}
		
		void addEdge(int x, int y) {
			super.addEdge(x, y, 1);
		}
		
		ClosedLoop PCV(int start_vertice, int final_vertice) {
			int visited_vertices[] = new int[num_vertices];
			int full_path[] = new int[num_vertices + 1];
			full_path[0] = start_vertice;
			ClosedLoop best_path = checkVertice(start_vertice, visited_vertices, start_vertice, full_path, 1, final_vertice);
			visited_vertices = null;
			full_path = null;
			return best_path;
		}
		
		ClosedLoop checkVertice (int vertice, int visited_vertice[], int start_vertice, int full_path[], int qnt_elements, int final_vertice) {
			visited_vertice[vertice] = 1;
			ClosedLoop best_path = new ClosedLoop(), tmp;

			for (short i = 0; i < num_vertices; i++) {
				if (i == final_vertice && matrix[i * num_vertices + vertice].edge == 1) {
					full_path[qnt_elements] = start_vertice;
					ClosedLoop found_path = new ClosedLoop(this.getPathWeight(full_path), full_path);
					full_path[qnt_elements] = -1;
					visited_vertice[vertice] = 0;
					if (best_path.weight == 0 || found_path.weight < best_path.weight) {
						best_path = null;
						tmp = null;
						return found_path;
					}
					else {
						found_path = null;
						tmp = null;
						return best_path;
					}
				}
				else if (qnt_elements == num_vertices) {
					break;
				}
				else if (matrix[vertice * num_vertices + i].edge == 1 && visited_vertice[i] == 0) {
					full_path[qnt_elements] = i;
					tmp = checkVertice(i, visited_vertice, start_vertice, full_path, qnt_elements + 1, final_vertice);
					if (best_path.weight == 0 || tmp.weight < best_path.weight)
						best_path = tmp;
					full_path[qnt_elements] = -1;
				}
			}
			
			visited_vertice[vertice] = 0;
			tmp = null;
			return best_path;
		}
				
		int getPathWeight(int path[]) {
			int weight = 0;
			for (int i = 1; i < path.length; i++) {
				weight += this.matrix[path[i - 1] * this.num_vertices + path[1]].value;
			}
			return weight;
		}
	}

}
