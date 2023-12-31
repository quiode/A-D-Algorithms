import java.util.Arrays;
import java.util.Random;

public class Sort {
	private final static Random random = new Random();

	public static void bubble(int[] A) {
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A.length - 1; j++) {
				if (A[j] > A[j + 1]) {
					// switch variables
					int temp = A[j + 1];
					A[j + 1] = A[j];
					A[j] = temp;
				}
			}
		}
	}

	public static void selection(int[] A) {
		for (int i = 0; i < A.length; i++) {
			// calculate max 0-(n-i)
			int max = Integer.MIN_VALUE;
			int maxI = -1;
			for (int j = 0; j < A.length - i; j++) {
				if (A[j] > max) {
					max = A[j];
					maxI = j;
				}
			}

			// insert maximum at the end
			A[maxI] = A[A.length - i - 1];
			A[A.length - i - 1] = max;
		}
	}

	public static void insertion(int[] A) {
		for (int i = 1; i < A.length; i++) {
			// find position k where A[i] belongs
			int currVal = A[i];
			int k = 0;
			for (int j = i - 1; j >= 0; j--) {
				if (A[j] <= currVal) {
					k = j + 1;
					break;
				}
			}

			// move all values A[k]-A[i-1] up by one
			for (int j = i; j > k; j--) {
				A[j] = A[j - 1];
			}
			// set A[k] to the new value
			A[k] = currVal;
		}
	}

	public static void quick(int[] A) {
		quick(A, 0, A.length - 1);
	}

	private static void quick(int[] A, int l, int r) {
		if (l < r) {
			// get index of pivot element
			int k = split(A, l, r);
			// sort left half
			quick(A, l, k - 1);
			// sort right half
			quick(A, k + 1, r);
		}
	}

	private static int split(int[] A, int l, int r) {
		// get pivot
		int pI = l + random.nextInt(r - l + 1);
		int p = A[pI];
		// switch with last value
		A[pI] = A[r];
		A[r] = p;

		int i = l;
		int j = r - 1;
		while (j >= i) {
			// find elements which do not meet the condition
			while (i < r && A[i] <= p) {
				i++;
			}
			while (j >= l && A[j] > p) {
				j--;
			}

			// switch values
			if (i < j) {
				int temp = A[j];
				A[j] = A[i];
				A[i] = temp;
			}
		}

		// move pivot in the middle
		A[r] = A[i];
		A[i] = p;

		return i;
	}

	public static void merge(int[] A) {
		mergeSort(A, 0, A.length - 1);
	}

	private static void mergeSort(int[] A, int l, int r) {
		if (l < r) {
			int m = (l + r) / 2;
			// sort left half
			mergeSort(A, l, m);
			// sort right half
			mergeSort(A, m + 1, r);
			// merge both halves
			merge(A, l, m, r);
		}
	}

	private static void merge(int[] A, int l, int m, int r) {
		int[] B = new int[r - l + 1];
		int i = l;
		int j = m + 1;
		int k = 0;

		while (i <= m && j <= r) {
			if (A[i] < A[j]) {
				// get next element from left array
				B[k++] = A[i++];
			} else {
				// get next element from right array
				B[k++] = A[j++];
			}
		}

		// merge the rest of the arrays
		while (i <= m) {
			B[k++] = A[i++];
		}
		while (j <= r) {
			B[k++] = A[j++];
		}

		// save the sorted array
		System.arraycopy(B, 0, A, l, B.length);
	}

	public static void heap(int[] A) {
		// create heap
		Heap heap = new Heap(A.length);

		// insert elements
		for (int i = 0; i < A.length; i++) {
			heap.insert(A[i]);
		}

		// save new values to a
		for (int i = A.length - 1; i >= 0; i--) {
			A[i] = heap.extractMax();
		}
	}

	private static class Heap {
		private final int[] heap;
		private int lastElement = -1;

		public Heap(int size) {
			heap = new int[size];
		}

		public Heap(int[] values) {
			this(values.length);

			for (int v : values) {
				insert(v);
			}
		}

		public void insert(int v) {
			if (lastElement >= heap.length - 1) {
				throw new ArrayIndexOutOfBoundsException("Heap is full");
			}

			// insert v at the end of the array
			heap[++lastElement] = v;

			// move element up until it's smaller than it's parent
			int element = lastElement;
			int parent = element / 2;
			while (heap[parent] < heap[element] && element > 0) {
				int temp = heap[parent];
				heap[parent] = heap[element];
				heap[element] = temp;

				element = parent;
				parent = element / 2;
			}
		}

		public int extractMax() {
			if (lastElement < 0) {
				throw new ArrayIndexOutOfBoundsException("Heap is empty");
			}

			// switch last element with root
			int root = heap[0];
			heap[0] = heap[lastElement];
			heap[lastElement] = root;
			// remove last element
			heap[lastElement--] = 0;
			// sink new root
			if (lastElement >= 1) {
				if (lastElement >= 2) {
					int child1 = 1;
					int child2 = 2;
					int i = 0;

					// switch root with largest child
					while (child1 <= lastElement && (heap[i] < heap[child1] || heap[i] < heap[child2])) {
						int maxChild;
						if (heap[child1] > heap[child2]) {
							maxChild = child1;
						} else {
							maxChild = child2;
						}

						int iVal = heap[i];
						heap[i] = heap[maxChild];
						heap[maxChild] = iVal;
						i = maxChild;

						child1 = i * 2;
						child2 = child1 + 1 <= lastElement ? child1 + 1 : child1;
					}
				} else {
					if (heap[0] < heap[1]) {
						int temp = heap[1];
						heap[1] = heap[0];
						heap[0] = temp;
					}
				}
			}

			return root;
		}

		@Override
		public String toString() {
			String str = "";

			for (int i = 0; i <= lastElement; i++) {
				str += String.format("(%d;%d,%d),", heap[i], i * 2 <= lastElement ? heap[i * 2] : -1,
						i * 2 + 1 <= lastElement ? heap[i * 2 + 1] : -1);
			}

			return str;
		}
	}
}
