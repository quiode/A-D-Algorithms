import java.util.Objects;

class Tuple implements Comparable<Tuple> {
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
	public int compareTo(Tuple o) {
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