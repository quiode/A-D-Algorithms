
public class Search {
	public static int linear(int[] A, int x) {
		int i = -1;

		for (int j = 0; j < A.length; j++) {
			if (A[j] == x) {
				i = j;
				break;
			}
		}

		return i;
	}

	public static int binary(int[] A, int x) {
		return binary(A, 0, A.length - 1, x);
	}

	private static int binary(int[] A, int l, int r, int x) {
		if (l > r) {
			return -1;
		}

		int m = (l + r) / 2;
		if (A[m] == x) {
			return m;
		} else if (A[m] < x) {
			return binary(A, m + 1, r, x);
		} else {
			return binary(A, l, m - 1, x);
		}
	}
}
