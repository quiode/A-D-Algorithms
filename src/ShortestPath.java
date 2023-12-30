import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

public class ShortestPath {
	public static int[][] dfs(Graph g) {
		// initialize
		boolean[] marked = new boolean[g.n];
		int[] pre = new int[g.n];
		int[] post = new int[g.n];
		int T = 1;

		List<List<Integer>> adj = g.getAdj();
		// calculate pre/post
		for (int i = 0; i < adj.size(); i++) {
			if (!marked[i]) {
				T = visit(i, g, marked, pre, post, T);
			}
		}

		int[][] result = { pre, post };
		return result;
	}

	private static int visit(int u, Graph g, boolean[] marked, int[] pre, int[] post, int T) {
		pre[u] = T++;
		marked[u] = true;

		List<List<Integer>> adj = g.getAdj();
		// visit each neighbour
		for (int i = 0; i < adj.get(u).size(); i++) {
			int v = adj.get(u).get(i);
			if (!marked[v]) {
				T = visit(v, g, marked, pre, post, T);
			}
		}
		post[u] = T++;

		return T;
	}

	public static int[] bfs(Graph g, int start) {
		// initialize
		Queue<Integer> q = new LinkedList<>();
		int[] enter = new int[g.n];
		int[] leave = new int[g.n];
		int[] d = new int[g.n];
		int T = 1;

		q.add(start);
		enter[start] = T++;
		d[start] = 0;

		// visit each neighbour of the next element in the queue
		while (!q.isEmpty()) {
			int v = q.poll();
			leave[v] = T++;

			List<List<Integer>> adj = g.getAdj();
			for (int i = 0; i < adj.get(v).size(); i++) {
				int u = adj.get(v).get(i);

				if (enter[u] == 0) {
					q.add(u);
					d[u] = d[v] + 1;
					enter[u] = T++;
				}
			}
		}

		return d;
	}

	public static int[] dijkstra(Graph g, int start) {
		// initialize
		int[] d = new int[g.n];
		for (int i = 0; i < d.length; i++) {
			d[i] = Integer.MAX_VALUE;
		}
		boolean[] marked = new boolean[g.n];
		PriorityQueue<Tuple> heap = new PriorityQueue<>();

		// add first node
		heap.add(Tuple.of(start, 0));
		d[start] = 0;

		List<List<Integer>> adj = g.getAdj();
		List<List<Integer>> weights = g.getWeights();

		// calculate all distances
		while (!heap.isEmpty()) {
			int v = heap.poll().node;
			marked[v] = true;

			// go through all neighbours of v
			for (int i = 0; i < adj.get(v).size(); i++) {
				int u = adj.get(v).get(i);

				// only update distance if it really is better
				if (!marked[u] && (d[u] > d[v] + weights.get(v).get(u))) {
					heap.remove(Tuple.of(u, d[u]));
					d[u] = d[v] + weights.get(v).get(u);
					heap.add(Tuple.of(u, d[u]));
				}
			}
		}

		return d;
	}

	private static class Tuple implements Comparable<Tuple> {
		final int node;
		final int distance;

		public Tuple(int node, int distance) {
			this.node = node;
			this.distance = distance;
		}

		public static Tuple of(int node, int distance) {
			return new Tuple(node, distance);
		}

		@Override
		public int compareTo(ShortestPath.Tuple o) {
			return distance - o.distance;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Tuple) {
				Tuple tuple = (Tuple) obj;
				return node == tuple.node && distance == tuple.distance;
			} else {
				return false;
			}
		}

		@Override
		public int hashCode() {
			return Objects.hash(node, distance);
		}
	}

	public static int[] bellmanFord(Graph g, int start) {
		int[] d = new int[g.n];
		for (int i = 0; i < d.length; i++) {
			d[i] = Integer.MAX_VALUE;
		}

		// set values for the start
		d[start] = 0;

		List<List<Integer>> adj = g.getAdj();
		List<List<Integer>> weights = g.getWeights();

		// repeat n times
		for (int i = 0; i < g.n; i++) {
			// go through all nodes (and their edges)
			for (int u = 0; u < g.n; u++) {
				// go through all edges
				for (int j = 0; j < adj.get(u).size(); j++) {
					int v = adj.get(u).get(j);
					// update distance
					d[v] = Math.min(d[v], d[u] + weights.get(u).get(v));
				}
			}
		}

		return d;
	}

	public static int[][] floydWashall(Graph g) {
		int[][][] dp = new int[g.n + 1][g.n][g.n];

		List<List<Integer>> adj = g.getAdj();
		List<List<Integer>> weights = g.getWeights();

		// base case dp[u][v][0]
		for (int u = 0; u < g.n; u++) {
			for (int v = 0; v < g.n; v++) {
				if (u == v) {
					dp[0][u][v] = 0;
				} else if (adj.get(u).contains(v)) {
					dp[0][u][v] = weights.get(u).get(v);
				} else {
					dp[0][u][v] = Integer.MAX_VALUE;
				}
			}
		}

		// recursion
		for (int i = 1; i <= g.n; i++) {
			for (int u = 0; u < g.n; u++) {
				for (int v = 0; v < g.n; v++) {
					dp[i][u][v] = Math.min(dp[i - 1][u][v], safeAdd(dp[i - 1][u][i], dp[i - 1][i][v]));
				}
			}
		}

		return dp[g.n];
	}

	private static int safeAdd(int a, int b) {
		if (a == Integer.MAX_VALUE || b == Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		} else {
			return a + b;
		}
	}

	public static int[][] johnson(Graph g) {
		// 1. Add z
		List<Integer> zNeighbours = new ArrayList<>(g.n);
		List<Integer> zWeights = new ArrayList<>(g.n);

		for (int i = 0; i < g.n; i++) {
			zNeighbours.add(i);
			zWeights.add(0);
		}

		// create new graph
		Graph zGraph = g.addNode(zNeighbours, zWeights);

		// 2. Execute Bellman Ford
		int[] d = bellmanFord(zGraph, g.n);

		// update weights in previous graph
		List<List<Integer>> weights = g.getWeights();
		List<List<Integer>> adj = g.getAdj();

		for (int u = 0; u < adj.size(); u++) {
			for (int j = 0; j < adj.get(u).size(); j++) {
				int v = adj.get(u).get(j);

				weights.get(u).set(v, weights.get(u).get(v) + d[u] - d[v]);
			}
		}

		// create new graph
		g = new Graph(adj, weights);

		// 3. Execute Dijkstra on the new graph (for each node)
		int[][] distances = new int[g.n][g.n];
		for (int i = 0; i < distances.length; i++) {
			distances[i] = dijkstra(g, i);
		}

		return distances;
	}
}
