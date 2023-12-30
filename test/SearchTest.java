import static org.junit.Assert.*;

import org.junit.Test;

public class SearchTest {

	@Test
	public void testLinearSearch() {
		int[] A = { 1, 2, 3, 4, 5, 6, 7 };
		assertEquals(3, Search.linear(A, 4));
		assertEquals(1, Search.linear(A, 2));
		assertEquals(0, Search.linear(A, 1));
		assertEquals(-1, Search.linear(A, 0));
	}

	@Test
	public void testBinarySearch() {
		int[] A = { 1, 2, 3, 4, 5, 6, 7 };
		assertEquals(3, Search.binary(A, 4));
		assertEquals(1, Search.binary(A, 2));
		assertEquals(0, Search.binary(A, 1));
		assertEquals(-1, Search.binary(A, 0));
	}

}
