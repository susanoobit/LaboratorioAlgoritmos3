package TP2;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {
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

	public final int INFINITY = 32767;
	public final int EDGE = 1;
	public final int EMPTY = 0;

	Graph() {
		this.num_vertices = 5;
		this.matrix = new Vertice[25];
		for (int i = 0; i < this.num_vertices * this.num_vertices; i++)
			this.matrix[i] = new Vertice();
	}

	Graph(int num_vertices) {
		this.num_vertices = num_vertices;
		this.matrix = new Vertice[this.num_vertices * this.num_vertices];
		for (int i = 0; i < this.num_vertices * this.num_vertices; i++)
			this.matrix[i] = new Vertice();
	}

	int hasEdge(int v1, int v2) {
		return this.matrix[v1 * this.num_vertices + v2].edge;
	}

	boolean isComplete() {
		if (isDirectioned())
			return false;

		int sum = 0;

		for (int i = 0; i < this.num_vertices; i++) {
			for (int j = i + 1; j < this.num_vertices; j++) {
				sum += this.matrix[i * num_vertices + j].edge;
			}
		}
		return sum == (num_vertices * num_vertices - 1) / 2;
	}

	boolean isRegular() {
		int degrees[] = new int[this.num_vertices];

		boolean resp = true;

		for (int i = 0; i < this.num_vertices; i++) {
			degrees[i] = this.getDegree(i);
		}

		for (int i = 1; (i < this.num_vertices) && resp; i++) {
			resp = degrees[i] == degrees[i - 1];
		}
		return resp;
	}

	boolean isNullGraph() {
		boolean resp = true;

		int degrees[] = new int[this.num_vertices];

		for (int i = 0; i < this.num_vertices; i++) {
			degrees[i] = this.getDegree(i);
		}

		for (int i = 0; (i < this.num_vertices) && resp; i++) {
			resp = degrees[i] == 0;
		}

		return resp;
	}

	void addEdge(int x, int y, int val) {
		this.matrix[x * this.num_vertices + y].edge = EDGE;
		this.matrix[x * this.num_vertices + y].value = val;
	}

	void delEdge(int x, int y) {
		this.matrix[x * this.num_vertices + y].edge = EMPTY;
		this.matrix[x * this.num_vertices + y].value = 0;
	}

	int getEdge(int x, int y) {
		return this.matrix[x * this.num_vertices + y].edge;
	}

	void addVertice() {
		++num_vertices;
		Vertice aux[] = new Vertice[this.num_vertices * this.num_vertices];
		for (int i = 0; i < this.num_vertices * this.num_vertices; i++)
			aux[i] = new Vertice();

		for (int i = 0; i < num_vertices - 1; i++) {
			for (int j = 0; j < num_vertices - 1; j++) {
				aux[i * num_vertices + j] = this.matrix[i * (num_vertices - 1) + j];
			}
		}
		this.matrix = aux;

		aux = null;
	}

	void delVertice() {
		--num_vertices;
		Vertice aux[] = new Vertice[this.num_vertices * this.num_vertices];
		for (int i = 0; i < this.num_vertices * this.num_vertices; i++)
			aux[i] = new Vertice();

		for (int i = 0; i < num_vertices; i++) {
			for (int j = 0; j < num_vertices; j++) {
				aux[i * num_vertices + j] = this.matrix[i * (num_vertices + 1) + j];
			}
		}

		this.matrix = aux;
		aux = null;
	}

	int getDegree(int vertice) {
		int resp = -1;
		if (!isDirectioned())
			resp = getEnterDegree(vertice) + getOutDegree(vertice);
		return resp;
	}

	int getOutDegree(int vertice) {
		int resp = 0;
		for (int i = 0; i < num_vertices; i++) {
			resp += matrix[vertice * num_vertices + i].edge;
		}
		return resp;
	}

	int getEnterDegree(int vertice) {
		int resp = 0;
		for (int i = 0; i < num_vertices; i++) {
			resp += matrix[i * num_vertices + vertice].edge;
		}
		return resp;
	}

	boolean hasLoops() {
		boolean resp = false;

		for (int i = 0; i < num_vertices; i++) {
			resp = resp || (matrix[i * num_vertices + i].edge == 1);
		}
		return resp;
	}

	boolean isBipartite() {
		int colors[] = new int[this.num_vertices];
		Arrays.fill(colors, 0);

		colors[0] = 1;
		Queue<Integer> vertices_to_visit = new LinkedList<Integer>();
		vertices_to_visit.add(0);

		while (!vertices_to_visit.isEmpty()) {
			int current_vertice = vertices_to_visit.remove();

			for (int j = 0; j < num_vertices; j++) {
				if (matrix[current_vertice * num_vertices + j].edge == 1) {
					if (colors[j] == 0) {
						colors[j] = colors[current_vertice] == 1 ? 2 : 1;
						vertices_to_visit.add(j);
					} else if (colors[j] == colors[current_vertice]) {
						return false;
					}
				}
			}
		}
		return true;
	}

	boolean isDirectioned() {
		boolean resp = true;

		for (int i = 0; i < num_vertices; i++) {
			for (int j = i + 1; j < num_vertices; j++) {
				resp = resp && (matrix[i * num_vertices + j].edge == matrix[j * num_vertices + i].edge);
			}
		}
		return !resp;
	}

	boolean isConnect() {
		boolean resp = true;

		short visited_vertices[] = new short[num_vertices];

		int val = 0;
		for (int i = 0; i < num_vertices; i++) {
			if (visited_vertices[i] == 0) {
				val++;
				visit_conex(i, val, visited_vertices);
			}
		}

		for (int i = 0; (i < num_vertices) && resp; i++)
			resp = (1 == visited_vertices[i]);

		visited_vertices = null;

		return resp;
	}

	Graph getComplement() {
		Graph resp = new Graph(this.num_vertices);

		for (int i = 0; i < this.num_vertices; i++) {
			for (int j = 0; j < this.num_vertices; j++) {
				int aux = this.getEdge(i, j) == 1 ? 0 : 1;
				if (aux == 1)
					resp.addEdge(i, j, 1);
			}
		}

		for (int i = 0; i < this.num_vertices; i++)
			resp.delEdge(i, i);

		return resp;
	}

	int getNComponents() {
		short visited_vertices[] = new short[num_vertices];

		int val = 0;
		for (int i = 0; i < num_vertices; i++) {
			if (visited_vertices[i] == 0) {
				val++;
				visit_conex(i, val, visited_vertices);
			}
		}

		visited_vertices = null;

		return val;
	}

	void visit_conex(int vertice, int val, short visited_vertice[]) {
		for (int i = 0; i < num_vertices; i++) {
			if (visited_vertice[i] == 0 && matrix[vertice * num_vertices + i].edge == 1) {
				visited_vertice[i] = (short) val;
				visit_conex(i, val, visited_vertice);
			}
		}
	}

	boolean isEulerGraph() {
		boolean resp = true;

		int degrees[] = new int[this.num_vertices];

		for (int i = 0; i < this.num_vertices; i++) {
			degrees[i] = this.getDegree(i) % 2;
		}

		for (int i = 0; i < this.num_vertices && resp; i++) {
			resp = degrees[i] == 0;
		}

		return resp;
	}

	boolean isUnicursal() {
		int val = 0;

		int degrees[] = new int[this.num_vertices];

		for (int i = 0; i < this.num_vertices; i++) {
			degrees[i] = this.getDegree(i) % 2;
		}

		for (int i = 0; i < this.num_vertices; i++) {
			val += degrees[i];
		}

		return val == 2;
	}

	boolean isCircuit(int list[], int n) {
		boolean resp = this.isClosePath(list, n);

		int frequency[] = new int[this.num_vertices];

		for (int i = 0; i < n && resp; i++) {
			frequency[list[i]]++;
		}

		for (int i = 0; i < this.num_vertices - 1 && resp; i++) {
			resp = frequency[i] == 1;
		}

		return resp;
	}

	boolean isLonelyVertice(int vertice) {
		return getDegree(vertice) == 0;
	}

	boolean isPendingVertice(int vertice) {
		return (!isDirectioned() && (getDegree(vertice) == 1));
	}

	boolean isPath(int list[], int n) {
		boolean resp = true;

		for (int i = 1; (i < n) && resp; i++) {
			resp = (getEdge(i - 1, i) == 1);
		}
		return resp;
	}

	boolean isOpenPath(int list[], int n) {
		return (this.isPath(list, n)) && (list[0] != list[n - 1]);
	}

	boolean isClosePath(int list[], int n) {
		return (this.isPath(list, n)) && (list[0] == list[n - 1]);
	}

	void print() {
		int i, j;

		System.out.printf("\n   ");
		for (i = 0; i < this.num_vertices; i++) {
			if (i < 10)
				System.out.printf(" 0%d", i);
			else
				System.out.printf(" %d", i);
		}
		System.out.printf("\n");

		System.out.printf("   ");
		for (i = 0; i < this.num_vertices * 3; i++)
			System.out.printf("-");
		System.out.printf("-\n");

		for (i = 0; i < this.num_vertices; i++) {
			if (i < 10)
				System.out.printf("0%d|", i);
			else
				System.out.printf("%d|", i);
			for (j = 0; j < this.num_vertices; j++) {

				System.out.printf("  %d", this.matrix[i * this.num_vertices + j].edge);
			}
			System.out.printf("\n");
		}
	}

	void transpose() {
		if (isDirectioned()) {
			for (int i = 0; i < num_vertices; i++) {
				for (int j = 1; j < num_vertices; j++) {
					short tmp = (short) matrix[i * num_vertices + j].edge;
					matrix[i * num_vertices + j].edge = matrix[j * num_vertices + i].edge;
					matrix[j * num_vertices + i].edge = tmp;
				}
			}
		}
	}

	void BFS() {
		short visited_vertices[] = new short[num_vertices];

		for (int i = 0; i < num_vertices; i++)
			visited_vertices[i] = 0;

		for (int i = 0; i < num_vertices; i++) {
			if (visited_vertices[i] == 0) {
				visited_vertices[i] = 1;
				Queue<Integer> vertices_to_visit = new LinkedList<Integer>();
				vertices_to_visit.add(i);

				while (!vertices_to_visit.isEmpty()) {
					int current_vertice = vertices_to_visit.remove();

					for (int j = 0; j < num_vertices; j++) {
						if (matrix[current_vertice * num_vertices + j].edge == 1 && visited_vertices[j] == 0) {
							visited_vertices[j] = 1;
							vertices_to_visit.add(j);
						}
					}
					visited_vertices[current_vertice] = 2;
					System.out.printf("%d\n", current_vertice);
				}
			}
		}
		for (int i = 0; i < num_vertices; i++)
			System.out.printf("%d", visited_vertices[i]);
	}

	void DFS() {
		short visited_vertices[] = new short[num_vertices];

		for (int i = 0; i < num_vertices; i++) {
			if (visited_vertices[i] == 0) {
				visit(i, visited_vertices);
			}
		}

		visited_vertices = null;
	}

	void visit(int vertice, short visited_vertice[]) {
		visited_vertice[vertice] = 1;

		for (int i = 0; i < num_vertices; i++) {
			if (visited_vertice[i] == 0 && matrix[vertice * num_vertices + i].edge == 1) {
				visit(i, visited_vertice);
			}
		}
		visited_vertice[vertice] = 2;
		System.out.printf("%d\n", vertice);
	}
}
