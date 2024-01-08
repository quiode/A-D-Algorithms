import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class MST {
	public static int[] Prim(Graph g, int start) {
		PriorityQueue<Tuple> Q = new PriorityQueue<>();
		int[] d = new int[g.n];
		boolean[] seen = new boolean[g.n];
		int[] pre = new int[g.n];

		for (int i = 0; i < d.length; i++) {
			d[i] = Integer.MAX_VALUE;
		}

		d[start] = 0;
		seen[start] = true;
		Q.add(Tuple.of(start, 0));

		List<List<Integer>> adj = g.getAdj();
		List<List<Integer>> weights = g.getWeights();
		while (!Q.isEmpty()) {
			int u = Q.remove().node;
			seen[u] = true;

			for (int i = 0; i < adj.get(u).size(); i++) {
				int v = adj.get(u).get(i);
				int c = weights.get(u).get(i);

				if (!seen[v] && d[v] > c) {
					Q.remove(Tuple.of(v, d[v]));
					d[v] = c;
					Q.add(Tuple.of(v, d[v]));
					pre[v] = u;
				}
			}
		}

		return pre;
	}

	public static int[] Boruvka(Graph g) {
		// TODO
		return null;
	}

	public static List<Graph.Edge> Kruskal(Graph g) {
		List<Graph.Edge> edges = g.getEdges().stream().sorted().collect(Collectors.toList());

		List<Graph.Edge> chosenEdges = new LinkedList<>();

		UnionFind unionFind = new UnionFind(g.n);
		for (Graph.Edge edge : edges) {
			if (!unionFind.same(edge.u, edge.v)) {
				unionFind.union(edge.u, edge.v);
				chosenEdges.add(edge);
			}
		}

		return chosenEdges;
	}

	private static class UnionFind {
		private final int[] rep;
		private final List<Integer>[] members;

		public UnionFind() {
			this(0);
		}

		public UnionFind(int n) {
			rep = new int[n];
			members = new List[n];

			for (int i = 0; i < rep.length; i++) {
				rep[i] = i;
			}

			for (int i = 0; i < members.length; i++) {
				members[i] = new LinkedList<>();
				members[i].add(i);
			}
		}

		public boolean same(int u, int v) {
			return rep[u] == rep[v];
		}

		public void union(int u, int v) {
			if (rep[u] != rep[v]) {
				if (members[rep[v]].size() < members[rep[u]].size()) {
					int t = v;
					v = u;
					u = t;
				}

				int repU = rep[u];
				for (int member : members[repU]) {
					members[rep[v]].add(member);
					rep[member] = rep[v];
				}
				members[repU].clear();
			}
		}
	}
}
