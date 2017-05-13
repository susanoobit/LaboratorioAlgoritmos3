package TP2;
import java.util.ArrayList;

public class Graph2 extends Graph {
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
	
	ClosedLoop closed_loops[];
	private ArrayList<ClosedLoop> closed_loopsal = new ArrayList<ClosedLoop>();

	public Graph2() {
		super();
	}

	public Graph2(int num_vertices) {
		super(num_vertices);
	}
	
	ClosedLoop PCV(int start_vertice) throws Exception {
		int visited_vertices[] = new int[num_vertices];
		int full_path[] = new int[num_vertices + 1];
		full_path[0] = start_vertice;
		ClosedLoop best_path = checkVertice(start_vertice, visited_vertices, start_vertice, full_path, 1);
		visited_vertices = null;
		full_path = null;
		return best_path;
	}
	
	ClosedLoop checkVertice (int vertice, int visited_vertice[], int start_vertice, int full_path[], int qnt_elements) {
		visited_vertice[vertice] = 1;
		ClosedLoop best_path = new ClosedLoop(), tmp;

		for (short i = 0; i < num_vertices; i++) {
			if (matrix[vertice * num_vertices + i].edge == 1 && visited_vertice[i] == 0) {
				full_path[qnt_elements] = i;
				tmp = checkVertice(i, visited_vertice, start_vertice, full_path, qnt_elements + 1);
				if (best_path.weight == 0 || tmp.weight < best_path.weight)
					best_path = tmp;
				full_path[qnt_elements] = -1;
			}
			else if (i == start_vertice &&
					qnt_elements == num_vertices &&
					matrix[i * num_vertices + vertice].edge == 1) {
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
		}
		
		visited_vertice[vertice] = 0;
		tmp = null;
		return best_path;
	}
		
	ClosedLoop[] PCV2(int start_vertice) throws Exception {
		short visited_vertices[] = new short[num_vertices];
		ArrayList<Integer> full_path = new ArrayList<Integer>(this.num_vertices + 1);
		full_path.add(new Integer(start_vertice));
		checkVertice2(start_vertice, visited_vertices, start_vertice, full_path);
		this.closed_loops = new ClosedLoop[this.closed_loopsal.size()];
		this.closed_loops = this.closed_loopsal.toArray(this.closed_loops);
		this.closed_loopsal = new ArrayList<ClosedLoop>();
		visited_vertices = null;
		return getBestLoops();
	}

	private void checkVertice2 (int vertice, short visited_vertice[], int start_vertice, ArrayList<Integer> full_path) {
		visited_vertice[vertice] = 1;

		for (int i = 0; i < num_vertices; i++) {
			if (matrix[vertice * num_vertices + i].edge == 1 && visited_vertice[i] == 0) {
				full_path.add(new Integer(i));
				checkVertice2(i, visited_vertice, start_vertice, full_path);
				full_path.remove(getLastPopulatedIndex(full_path));
			}
			else if (i == start_vertice &&
					countNonNullElements(full_path) == num_vertices &&
					matrix[i * num_vertices + vertice].edge == 1) {
				full_path.add(new Integer(start_vertice));
				int[] path = new int[full_path.size()];
				for (int j = 0; j < path.length; j++) {
					path[j] = full_path.get(j).intValue();
				}
				this.closed_loopsal.add(new ClosedLoop(this.getPathWeight(path), path));
				full_path.remove(num_vertices);
				visited_vertice[vertice] = 0;
				return;
			}
		}
		visited_vertice[vertice] = 0;
	}
	
	int getPathWeight(int path[]) {
		int weight = 0;
		for (int i = 1; i < path.length; i++) {
			weight += this.matrix[path[i - 1] * this.num_vertices + path[1]].value;
		}
		return weight;
	}
	
	ClosedLoop[] getBestLoops() throws Exception {
		if (this.closed_loops.length == 0)
			throw new Exception("There is no closed loop. Try to invoke the DFS2 method first and try again.");
		
		ArrayList<ClosedLoop> best_closed_loops = new ArrayList<ClosedLoop>();
		ClosedLoop best_path = this.closed_loops[0];
		int weight = this.getPathWeight(best_path.path);
		for (int i = 1; i < this.closed_loops.length; i++) {
			int aux_weight = this.getPathWeight(this.closed_loops[i].path);
			if (aux_weight < weight)
				best_path = this.closed_loops[i];
		}
		for (int i = 0; i < this.closed_loops.length; i++) {
			if (this.closed_loops[i].weight == best_path.weight)
				best_closed_loops.add(this.closed_loops[i]);
		}
		return best_closed_loops.toArray(new ClosedLoop[0]);
	}
	
	void printClosedLoop(ClosedLoop cl) {
		System.out.print("\nPath: ");
		for (int i = 0; i < cl.path.length; i++) {
			System.out.print(cl.path[i] + " ");
		}
		System.out.print("\nWeight: " + cl.weight);
	}
	
	void printClosedLoopCSV(ClosedLoop cl) {
		for (int i = 0; i < cl.path.length; i++) {
			System.out.print(cl.path[i] + " ");
		}
		System.out.print("\t" + cl.weight);
	}
	
	private int countNonNullElements(ArrayList<Integer> path) {
		int length = 0;
		for (int i = 0; i < path.size(); i++)
			if (path.get(i) != null) length++;
		return length;
	}
	
	private int getLastPopulatedIndex(ArrayList<Integer> path) {
		for (int i = 0; i < path.size(); i++)
			if (path.get(i) == null) return i - 1;
		return path.size() - 1;
	}
}
