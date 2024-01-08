import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

public class ShortestPath {
	public static int[][] dfs(Graph g) {
		boolean[] visited = new boolean[g.n];
		int[] pre = new int[g.n];
		int[] post = new int[g.n];
		int T = 1;

		for (int i = 0; i < g.n; i++) {
			if (!visited[i]) {
				T = visit(i, g, visited, pre, post, T);
			}
		}

		int[][] returnV = { pre, post };
		return returnV;
	}

	private static int visit(int u, Graph g, boolean[] marked, int[] pre, int[] post, int T) {
		pre[u] = T++;
		marked[u] = true;

		List<List<Integer>> adj = g.getAdj();
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
		Queue<Integer> Q = new LinkedList<>();
		int[] d = new int[g.n];
		int[] enter = new int[g.n];
		int[] leave = new int[g.n];
		int T = 1;

		for (int i = 0; i < g.n; i++) {
			enter[i] = -1;
			leave[i] = -1;
		}

		Q.add(start);
		d[start] = 0;
		enter[start] = T++;

		List<List<Integer>> adj = g.getAdj();
		while (!Q.isEmpty()) {
			int u = Q.remove();
			leave[u] = T++;

			for (int i = 0; i < adj.get(u).size(); i++) {
				int v = adj.get(u).get(i);

				if (enter[v] == -1) {
					Q.add(v);
					enter[v] = T++;
					d[v] = d[u + 1];
				}
			}
		}

		return d;
	}

	public static int[] dijkstra(Graph g, int start) {
		PriorityQueue<Tuple> Q = new PriorityQueue<>();
		int[] d = new int[g.n];
		boolean[] seen = new boolean[g.n];

		for (int i = 0; i < d.length; i++) {
			d[i] = Integer.MAX_VALUE;
		}

		d[start] = 0;
		seen[start] = true;
		Q.add(Tuple.of(start, 0));

		List<List<Integer>> adj = g.getAdj();
		List<List<Integer>> weight = g.getWeights();
		while (!Q.isEmpty()) {
			int u = Q.remove().node;
			seen[u] = true;

			for (int i = 0; i < adj.get(u).size(); i++) {
				int v = adj.get(u).get(i);
				int c = weight.get(u).get(i);

				if (!seen[v] && d[v] > d[u] + c) {
					Q.remove(Tuple.of(v, d[v]));
					d[v] = d[u] + c;
					Q.add(Tuple.of(v, d[v]));
				}
			}
		}

		return d;
	}

	public static int[] bellmanFord(Graph g, int start) {
		int[] d = new int[g.n];

		for (int i = 0; i < d.length; i++) {
			d[i] = Integer.MAX_VALUE;
		}
		d[start] = 0;

		List<List<Integer>> adj = g.getAdj();
		List<List<Integer>> weights = g.getWeights();
		for (int i = 0; i < g.n; i++) {
			for (int u = 0; u < g.n; u++) {
				for (int j = 0; j < adj.get(u).size(); j++) {
					int v = adj.get(u).get(j);
					int c = weights.get(u).get(j);

					d[v] = Math.min(d[v], d[u] + c);
				}
			}
		}

		return d;
	}

	public static int[][] floydWashall(Graph g) {
		int[][][] dp = new int[g.n][g.n][g.n];

		List<List<Integer>> adj = g.getAdj();
		List<List<Integer>> weights = g.getWeights();
		for (int i = 0; i < g.n; i++) {
			for (int u = 0; u < g.n; u++) {
				for (int v = 0; v < g.n; v++) {
					if (i == 0) {
						// Base cases
						if (u == v) {
							dp[i][u][v] = 0;
						} else {
							int index = adj.get(u).indexOf(v);
							if (index != -1) {
								dp[i][u][v] = weights.get(u).get(index);
							} else {
								dp[i][u][v] = Integer.MAX_VALUE;
							}
						}
					} else {
						dp[i][u][v] = Math.min(dp[i - 1][u][v], safeAdd(dp[i - 1][u][i], dp[i - 1][i][v]));
					}
				}
			}
		}

		return dp[g.n - 1];
	}

	private static int safeAdd(int a, int b) {
		if (a == Integer.MAX_VALUE || b == Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		} else {
			return a + b;
		}
	}

	public static int[][] johnson(Graph g) {
		// create new graph with z
		List<Integer> newAdj = new ArrayList<>(g.n);
		List<Integer> newWeights = new ArrayList<>(g.n);
		for (int i = 0; i < g.n; i++) {
			newAdj.add(i);
			newWeights.add(0);
		}

		Graph newG = g.addNode(newAdj, newWeights);
		// execute bellman ford on the new graph
		int[] d = bellmanFord(newG, g.n);

		// update weights
		List<List<Integer>> adj = g.getAdj();
		List<List<Integer>> weights = g.getWeights();

		for (int u = 0; u < g.n; u++) {
			for (int i = 0; i < adj.get(u).size(); i++) {
				int v = adj.get(u).get(i);

				weights.get(u).set(i, weights.get(u).get(i) + d[u] - d[v]);
			}
		}

		Graph updatedG = new Graph(adj, weights);
		// execute dijkstra for every node
		int[][] distances = new int[g.n][g.n];

		for (int i = 0; i < g.n; i++) {
			distances[i] = dijkstra(updatedG, i);
		}

		return distances;
	}
}
