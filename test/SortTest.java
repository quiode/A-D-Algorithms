import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.Test;
import org.junit.jupiter.api.Timeout;

@Timeout(5)
public class SortTest {
	private final Random random = new Random(23452345);

	/**
	 * Creates an array for testing with size n
	 */
	private int[] testArray(int n) {
		int[] A = new int[n];

		for (int i = 0; i < A.length; i++) {
			A[i] = random.nextInt(100);
		}

		return A;
	}

	private void testSortMethod(Consumer<int[]> sort) {
		// test for 10 arrays
		for (int i = 0; i < 10; i++) {
			// create arrays
			int[] A = testArray(100);
			int[] sortedA = A.clone();
			Arrays.sort(sortedA);

			// test sorting algorithm
			sort.accept(A);
			assertArrayEquals(sortedA, A);
		}
	}

	@Test
	public void testBubble() {
		testSortMethod(Sort::bubble);
	}

	@Test
	public void testInsertion() {
		testSortMethod(Sort::insertion);
	}

	@Test
	public void testSelection() {
		testSortMethod(Sort::selection);
	}

	@Test
	public void testQuick() {
		testSortMethod(Sort::quick);
	}

	@Test
	public void testMege() {
		testSortMethod(Sort::merge);
	}

	@Test
	public void testHeap() {
		testSortMethod(Sort::heap);
	}

	@Test
	public void speedTest() {
		// default variables
		int rounds = 1000;
		int size = 1000;

		// test merge sort
		long start = System.currentTimeMillis();
		for (int i = 0; i < rounds; i++) {
			int[] A = testArray(size);
			Sort.merge(A);
		}
		long mergeTime = System.currentTimeMillis() - start;
		// test quick sort
		start = System.currentTimeMillis();
		for (int i = 0; i < rounds; i++) {
			int[] A = testArray(size);
			Sort.quick(A);
		}
		long quickTime = System.currentTimeMillis() - start;
		// test heap sort
		start = System.currentTimeMillis();
		for (int i = 0; i < rounds; i++) {
			int[] A = testArray(size);
			Sort.heap(A);
		}
		long heapTime = System.currentTimeMillis() - start;
		// test insertion sort
		start = System.currentTimeMillis();
		for (int i = 0; i < rounds; i++) {
			int[] A = testArray(size);
			Sort.insertion(A);
		}
		long insertionTime = System.currentTimeMillis() - start;
		// test selection sort
		start = System.currentTimeMillis();
		for (int i = 0; i < rounds; i++) {
			int[] A = testArray(size);
			Sort.selection(A);
		}
		long selectionTime = System.currentTimeMillis() - start;
		// test bubble sort
		start = System.currentTimeMillis();
		for (int i = 0; i < rounds; i++) {
			int[] A = testArray(size);
			Sort.bubble(A);
		}
		long bubbleTime = System.currentTimeMillis() - start;

		// Print times:
		System.out.println("Bubble Sort: " + bubbleTime);
		System.out.println("Insertion Sort: " + insertionTime);
		System.out.println("Selection Sort: " + selectionTime);
		System.out.println("Merge Sort: " + mergeTime);
		System.out.println("Quick Sort: " + quickTime);
		System.out.println("Heap Sort: " + heapTime);
	}

}
