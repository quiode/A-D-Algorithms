import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Graph {
	/**
	 * Amount of nodes
	 */
	public final int n;
	/*
	 * Amount of edges
	 */
	public final int m;

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
		n = 0;
		m = 0;
	}

	public Graph(int[][] adj, int[][] weights) {
		this.adj = new ArrayList<>(adj.length);
		this.weights = new ArrayList<>(adj.length);

		int edges = 0;
		for (int i = 0; i < adj.length; i++) {
			this.adj.set(0, new ArrayList<>(adj[i].length));
			this.weights.set(0, new ArrayList<>(adj[i].length));
			for (int j = 0; j < adj[i].length; j++) {
				edges++;
				this.adj.get(i).add(adj[i][j]);
				this.weights.get(i).add(weights[i][j]);
			}
		}

		n = this.adj.size();
		m = edges;
	}

	public Graph(Graph g) {
		adj = new ArrayList<>(g.adj.size());
		weights = new ArrayList<>(g.adj.size());

		for (int i = 0; i < g.adj.size(); i++) {
			adj.add(new ArrayList<>(g.adj.get(i)));
			weights.add(new ArrayList<>(g.weights.get(i)));
		}

		n = g.n;
		m = g.m;
	}

	public Graph(List<List<Integer>> adj, List<List<Integer>> weights) {
		this.adj = adj.stream().map(list -> list.stream().collect(Collectors.toList())).collect(Collectors.toList());
		this.weights = weights.stream().map(list -> list.stream().collect(Collectors.toList()))
				.collect(Collectors.toList());

		n = this.adj.size();
		m = this.weights.stream().map(Collection::size).reduce(0, (a, b) -> a + b);
	}

	public Graph addNode(List<Integer> adj, List<Integer> weights) {
		List<List<Integer>> newAdj = getAdj();
		newAdj.add(new ArrayList<>(adj));
		List<List<Integer>> newWeights = getWeights();
		newWeights.add(new ArrayList<>(weights));

		return new Graph(newAdj, newWeights);
	}

	public List<List<Integer>> getAdj() {
		return adj.stream().map(list -> list.stream().collect(Collectors.toList())).collect(Collectors.toList());
	}

	public List<List<Integer>> getWeights() {
		return weights.stream().map(list -> list.stream().collect(Collectors.toList())).collect(Collectors.toList());
	}
}
