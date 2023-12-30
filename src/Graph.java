import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Graph {
	/**
	 * adj[i][j] gets the j-th neighbour of node i.
	 */
	private final List<List<Integer>> adj;
	/**
	 * weights[i][j] gets the weight of the j-th neighbour of node i.
	 */
	private final List<List<Integer>> weights;

	public Graph() {
		adj = new ArrayList<>();
		weights = new ArrayList<>();
	}

	public Graph(int[][] adj, int[][] weights) {
		this.adj = new ArrayList<>(adj.length);
		this.weights = new ArrayList<>(adj.length);

		for (int i = 0; i < adj.length; i++) {
			this.adj.set(0, new ArrayList<>(adj[i].length));
			this.weights.set(0, new ArrayList<>(adj[i].length));
			for (int j = 0; j < adj[i].length; j++) {
				this.adj.get(i).add(adj[i][j]);
				this.weights.get(i).add(weights[i][j]);
			}
		}
	}

	public Graph(Graph g) {
		adj = new ArrayList<>(g.adj.size());
		weights = new ArrayList<>(g.adj.size());

		for (int i = 0; i < g.adj.size(); i++) {
			adj.add(new ArrayList<>(g.adj.get(i)));
			weights.add(new ArrayList<>(g.weights.get(i)));
		}
	}

	public List<List<Integer>> getAdj() {
		return adj.stream().map(list -> list.stream().collect(Collectors.toList())).collect(Collectors.toList());
	}

	public List<List<Integer>> getWeights() {
		return weights.stream().map(list -> list.stream().collect(Collectors.toList())).collect(Collectors.toList());
	}
}
