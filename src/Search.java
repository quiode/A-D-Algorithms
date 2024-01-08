
public class Search {
	public static int linear(int[] A, int x) {
		for (int i = 0; i < A.length; i++) {
			if (A[i] == x)
				return i;
		}

		return -1;
	}

	public static int binary(int[] A, int x) {
		return binary(A, x, 0, A.length - 1);
	}

	private static int binary(int[] A, int x, int l, int r) {
		if (l > r)
			return -1;

		int m = (l + r) / 2;
		if (A[m] == x) {
			return m;
		} else if (A[m] < x) {
			return binary(A, x, m + 1, r);
		} else {
			return binary(A, x, l, m - 1);
		}
	}
}
