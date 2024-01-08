import java.util.Random;

public class Sort {
	private static final Random random = new Random();

	public static void bubble(int[] A) {
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A.length - 1; j++) {
				if (A[j] > A[j + 1]) {
					int temp = A[j + 1];
					A[j + 1] = A[j];
					A[j] = temp;
				}
			}
		}
	}

	public static void selection(int[] A) {
		for (int i = A.length - 1; i >= 0; i--) {
			int max = i;

			for (int j = 0; j < i; j++) {
				if (A[j] > A[max]) {
					max = j;
				}
			}

			// switch A[i] with A[max]
			int temp = A[i];
			A[i] = A[max];
			A[max] = temp;
		}
	}

	public static void insertion(int[] A) {
		for (int i = 0; i < A.length; i++) {
			// binary search
			int l = 0;
			int r = i;
			while (l <= r) {
				int m = (l + r) / 2;
				if (A[m] >= A[i]) {
					r = m - 1;
				} else {
					l = m + 1;
				}
			}

			int v = A[i];
			int k = l;

			// move all elements to the right
			for (int j = i - 1; j >= k; j--) {
				A[j + 1] = A[j];
			}

			A[k] = v;
		}
	}

	public static void quick(int[] A) {
		quick(A, 0, A.length - 1);
	}

	private static void quick(int[] A, int l, int r) {
		if (l < r) {
			int p = split(A, l, r);
			quick(A, l, p - 1);
			quick(A, p + 1, r);
		}
	}

	private static int split(int[] A, int l, int r) {
		int k = l + random.nextInt((r - l + 1));
		int v = A[r];
		A[r] = A[k];
		A[k] = v;

		int i = l;
		int j = r - 1;

		while (i <= j) {
			while (i < r && A[i] <= A[r]) {
				i++;
			}
			while (j >= l && A[j] > A[r]) {
				j--;
			}

			if (i < j) {
				int temp = A[j];
				A[j] = A[i];
				A[i] = temp;
			}
		}

		int p = A[r];
		A[r] = A[i];
		A[i] = p;

		return i;
	}

	public static void merge(int[] A) {
		merge(A, 0, A.length - 1);
	}

	private static void merge(int[] A, int l, int r) {
		if (l < r) {
			int m = (l + r) / 2;
			merge(A, l, m);
			merge(A, m + 1, r);
			merge(A, m, l, r);
		}
	}

	private static void merge(int[] A, int m, int l, int r) {
		int[] B = new int[r - l + 1];
		int i = l;
		int j = m + 1;
		int k = 0;

		while (i <= m && j <= r) {
			if (A[i] <= A[j]) {
				B[k++] = A[i++];
			} else {
				B[k++] = A[j++];
			}
		}

		while (i <= m) {
			B[k++] = A[i++];
		}
		while (j <= r) {
			B[k++] = A[j++];
		}

		System.arraycopy(B, 0, A, l, B.length);
	}

	public static void heap(int[] A) {
		Heap heap = new Heap(A.length);

		for (int i : A) {
			heap.insert(i);
		}

		for (int i = A.length - 1; i >= 0; i--) {
			A[i] = heap.extractMax();
		}
	}

	private static class Heap {
		private final int[] heap;
		private int i;

		public Heap() {
			this(0);
		}

		public Heap(int n) {
			heap = new int[n];
			i = -1;
		}

		public void insert(int e) {
			if (i >= heap.length - 1) {
				throw new IllegalStateException("Heap is full");
			}

			heap[++i] = e;
			int cur = i;
			int p = i / 2;
			while (heap[p] < heap[cur]) {
				int temp = heap[p];
				heap[p] = heap[cur];
				heap[cur] = temp;

				cur = p;
				p = cur / 2;
			}
		}

		public int extractMax() {
			if (i < 0) {
				throw new IllegalStateException("Heap is empty");
			}

			int max = heap[0];
			heap[0] = heap[i--];

			int cur = 0;
			if (i >= 1) {
				if (i >= 2) {
					int c1 = 1;
					int c2 = 2;

					while (heap[c1] > heap[cur] || heap[c2] > heap[cur]) {
						int cMax;
						if (heap[c1] > heap[c2]) {
							cMax = c1;
						} else {
							cMax = c2;
						}

						int temp = heap[cur];
						heap[cur] = heap[cMax];
						heap[cMax] = temp;

						cur = cMax;
						if (cur * 2 <= i) {
							c1 = cur * 2;
							c2 = cur * 2 + 1 <= i ? cur * 2 + 1 : c1;
						} else {
							break;
						}
					}
				} else {
					if (heap[1] > heap[0]) {
						int temp = heap[0];
						heap[0] = heap[1];
						heap[1] = temp;
					}
				}
			}

			return max;
		}
	}
}
