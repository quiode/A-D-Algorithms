import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

public class MST {
	public static int[] Prim(Graph g, int start) {
		int[] d = new int[g.n];
		for (int i = 0; i < d.length; i++) {
			d[i] = Integer.MAX_VALUE;
		}
		boolean[] marked = new boolean[g.n];
		int[] predecessors = new int[g.n];
		PriorityQueue<Tuple> heap = new PriorityQueue<>();

		d[start] = 0;
		heap.add(Tuple.of(start, 0));

		List<List<Integer>> adj = g.getAdj();
		List<List<Integer>> weights = g.getWeights();

		while (!heap.isEmpty()) {
			int v = heap.poll().node;
			marked[v] = true;

			for (int i = 0; i < adj.get(v).size(); i++) {
				int u = adj.get(v).get(i);

				if (!marked[u] && weights.get(u).get(v) < d[u]) {
					heap.remove(Tuple.of(u, d[u]));
					d[u] = weights.get(u).get(v);
					heap.add(Tuple.of(u, d[u]));
					predecessors[u] = v;
				}
			}
		}

		return predecessors;
	}

	public static int[] Boruvka(Graph g) {
		// TODO
		return null;
	}

	public static List<Edge> Kruskal(Graph g) {
		List<Edge> edges = new ArrayList<>(g.m);

		List<List<Integer>> adj = g.getAdj();
		List<List<Integer>> weights = g.getWeights();

		for (int u = 0; u < adj.size(); u++) {
			for (int i = 0; i < adj.get(u).size(); i++) {
				int v = adj.get(u).get(i);

				edges.add(Edge.of(u, v, weights.get(u).get(v)));
			}
		}

		Collections.sort(edges);

		UnionFind unionFind = new UnionFind(g.n);

		List<Edge> selectedEdges = new LinkedList<>();

		for (Edge edge : edges) {
			if (!unionFind.same(edge.u, edge.v)) {
				unionFind.union(edge.u, edge.v);
				selectedEdges.add(edge);
			}
		}

		return selectedEdges;
	}

	private static class Edge implements Comparable<Edge> {
		final int u;
		final int v;
		final int weight;

		public Edge(int u, int v, int weight) {
			this.u = u;
			this.v = v;
			this.weight = weight;
		}

		public static Edge of(int u, int v, int weight) {
			return new Edge(u, v, weight);
		}

		@Override
		public int hashCode() {
			return Objects.hash(u, v, weight);
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Edge) {
				Edge e = (Edge) obj;
				return u == e.u && v == e.v && weight == e.weight;
			} else {
				return false;
			}
		}

		@Override
		public int compareTo(Edge o) {
			return weight - o.weight;
		}
	}

	private static class UnionFind {
		final int[] rep;
		final LinkedList<Integer>[] members;

		public UnionFind(int n) {
			rep = new int[n];
			members = new LinkedList[n];

			for (int i = 0; i < n; i++) {
				rep[i] = i;
				members[i] = new LinkedList<>();
			}
		}

		public boolean same(int u, int v) {
			return rep[u] == rep[v];
		}

		public void union(int u, int v) {
			if (rep[u] != rep[v]) {
				if (members[rep[u]].size() > members[rep[v]].size()) {
					int temp = v;
					v = u;
					u = temp;
				}

				// members u < members v
				int repU = rep[u];
				for (int member : members[repU]) {
					rep[member] = rep[v];
					members[rep[v]].add(member);
				}
				members[repU].clear();
			}
		}
	}
}
