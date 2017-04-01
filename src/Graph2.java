import java.util.ArrayList;

public class Graph2 extends Graph {
	public class ClosedLoop {
		int weight;
		int path[];
		
		ClosedLoop (int weight, int[] path) {
			this.weight = weight;
			this.path = path;
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
		
	void DFS2(int start_vertice) {
		short visited_vertices[] = new short[num_vertices];
		ArrayList<Integer> full_path = new ArrayList<Integer>(this.num_vertices + 1);
		full_path.add(new Integer(start_vertice));
		visit2(start_vertice, visited_vertices, start_vertice, full_path);
		this.closed_loops = new ClosedLoop[this.closed_loopsal.size()];
		this.closed_loops = this.closed_loopsal.toArray(this.closed_loops);
		this.closed_loopsal = new ArrayList<ClosedLoop>();
		visited_vertices = null;
	}

	void visit2 (int vertice, short visited_vertice[], int start_vertice, ArrayList<Integer> full_path) {
		visited_vertice[vertice] = 1;

		for (int i = 0; i < num_vertices; i++) {
			if (matrix[vertice * num_vertices + i].edge == 1 && visited_vertice[i] == 0) {
				full_path.add(new Integer(i));
				visit2(i, visited_vertice, start_vertice, full_path);
				full_path.remove(getLastPopulatedIndex(full_path));
			}
			else if (i == start_vertice &&
					countNonZeroElements(full_path) == num_vertices - 1 &&
					matrix[i * num_vertices + vertice].edge == 1) {
				full_path.add(new Integer(start_vertice));
				int[] path = new int[full_path.size()];
				for (int j = 0; j < path.length; j++) {
					path[j] = full_path.get(j).intValue();
				}
				this.closed_loopsal.add(new ClosedLoop(this.getPathWeight(path), path));
				int lastIndex = getLastPopulatedIndex(full_path);
				full_path.remove(lastIndex);
				full_path.remove(lastIndex - 1);
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
	
	ClosedLoop getBestLoop() throws Exception {
		if (this.closed_loops.length == 0)
			throw new Exception("There is no closed loop. Try to invoke the DFS2 method first and try again.");
		
		ClosedLoop best_path = this.closed_loops[0];
		int weight = this.getPathWeight(best_path.path);
		for (int i = 1; i < this.closed_loops.length; i++) {
			int aux_weight = this.getPathWeight(this.closed_loops[i].path);
			if (aux_weight < weight)
				best_path = this.closed_loops[i];
		}
		return best_path;
	}
	
	void printClosedLoop(ClosedLoop cl) {
		System.out.println("Path: ");
		for (int i = 0; i < cl.path.length; i++) {
			System.out.print(cl.path[i] + " ");
		}
		System.out.println("Weight: " + cl.weight);
	}
	
	int countNonZeroElements(ArrayList<Integer> path) {
		int length = 0;
		for (int i = 0; i < path.size(); i++)
			if (path.get(i).intValue() != 0) length++;
		return length;
	}
	
	int getLastPopulatedIndex(ArrayList<Integer> path) {
		for (int i = 0; i < path.size(); i++)
			if (path.get(i) == null) return i - 1;
		return path.size() - 1;
	}
}
