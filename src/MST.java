import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

public class MST {
	public static int[] Prim(Graph g, int start) {
	}

	public static int[] Boruvka(Graph g) {
		// TODO
		return null;
	}

	public static List<Edge> Kruskal(Graph g) {
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
}
