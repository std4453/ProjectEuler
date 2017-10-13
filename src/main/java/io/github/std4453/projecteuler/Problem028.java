package io.github.std4453.projecteuler;

/**
 * Problem #28: Number spiral diagonals<br />
 * Starting with the number 1 and moving to the right in a clockwise direction a 5 by 5
 * spiral is formed as follows:<br />
 * <b>21</b> 22 23 24 <b>25</b>
 * 20  <b>7</b>  8  <b>9</b> 10
 * 19  6  <b>1</b>  2 11
 * 18  <b>5</b>  4  <b>3</b> 12
 * <b>17</b> 16 15 14 <b>13</b>
 * It can be verified that the sum of the numbers on the diagonals is 101.<br />
 * What is the sum of the numbers on the diagonals in a 1001 by 1001 spiral formed in the
 * same way?
 */
public class Problem028 {
	private static final int LENGTH = 1001;

	// I tend to see this problem as a purely mathematical problem.
	// Let us give each number n a "direction" pointing at n = 1, it will look like
	// this in a 5x5 matrix:
	// (up = ^, down = |, left = <, right = >)
	// > > > > >
	// ^ > > > |
	// ^ ^ > | |
	// ^ ^ < < |
	// ^ < < < <
	// On the spiral route, if k adjacent numbers have the same direction, we say that
	// they form an "edge of length k". Now observe the sequence representing the
	// lengths of the edges:
	// 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, ...
	// The pattern is simple to spot.
	// And if we combine edge length with their directions, we get:
	// 1>, 1|, 2<, 2^, 3>, 3|, 4<, 4^, 5>, 5|, ...
	// Name a sub-sequence here of "(2n-1)|, 2n<, 2n^, (2n+1)>" the "nth cycle".
	// The nth cycle starts from number (2n - 1)^2 + 1.

	// Now put the matrix into a 2-dimensional Cartesian space, giving each number a
	// coordinate, 1 being (0, 0). Using the pattern we've spotted, we can say:
	// Let n(x, y) to be the number at position (x, y).
	// (x, y) belongs to the min(|x|, |y|)th cycle.
	// Let k = min(|x|, |y|).
	// The kth cycle starts from coordinate (k, k - 1).
	// If x = k and y != k, n(x, y) = (2k - 1)^2 + 1 + ((k - 1) - y) = 4k^2 -3k +1 - y
	// If y = -k, n(x, y) = 4k^2 - 3k + 1 - (-k) + (k - x) = 4k^2 - k + 1 - x
	// If x = -k, n(x, y) = 4k^2 - k + 1 - (-k) + (y - (-k)) = 4k^2 + k + 1 + y
	// If y = k, n(x, y) = 4k^2 + k + 1 + k + (x - (-k)) = k^2 + 3k + 1 + x

	// So when i > 0, n(i, i) = 4i^2 + 4i + 1
	//                n(i, -i) = 4i^2 - 2i + 1
	//                n(-i, -i) = 4i^2 + 1
	//                n(-i, i) = 4i^2 + 2i + 1
	// Let N(i) = n(i, i) + n(i, -i) + n(-i, -i) + n(-i, i) = 16i^2 + 4i + 4
	// The sum of the numbers on the diagonals in a 2n + 1 by 2n + 1 spiral is:
	// S(2n + 1) = 1 + ΣN(i) for 1 <= i <= n
	//           = 1 + 4 * n + 4 * (1 + n) * n / 2 + 16 * n * (n + 1) * (2n + 1) / 6
	//           = 16/3 * n^3 + 10 * n^2 + 26/3 * n + 1
	// Now given that 2n + 1 = 1001, we get:
	// S(2n + 1) = 16/3 * 500^3 + 10 * 500^2 + 26/3 * 500 + 1 = 669171001

	@SuppressWarnings("NumericOverflow")
	public static void main(String[] args) {
		int n = (LENGTH - 1) >> 1;
		System.out.println((((n << 4) + 30) * n + 26) * n / 3 + 1);

		// Answer: 669171001
		// Complexity: O(1)

		// TODO: add homework
	}
}
