package io.github.std4453.projecteuler;

/**
 * Problem #6: Sum square difference<br />
 * The sum of the squares of the first ten natural numbers is,<br />
 * {@code 1^2 + 22 ^+ ... + 10^2 = 385}<br />
 * The square of the sum of the first ten natural numbers is,<br />
 * {@code (1 + 2 + ... + 10)^2 = 55^2 = 3025}<br />
 * Hence the difference between the sum of the squares of the first ten natural numbers
 * and the square of the sum is 3025 − 385 = 2640.<br />
 * Find the difference between the sum of the squares of the first one hundred natural
 * numbers and the square of the sum.
 */
public class Problem006 {
	private static final long N = 100;

	// This is more a maths problem than a programming problem.
	// Using some middle-school maths we can know,
	// 1^2 + 2^2 + ... + n^2 = n(n + 1)(2n + 1) / 6
	// and,
	// 1 + 2 + 3 + ... + n = n(n + 1) / 2
	// Therefore difference between the sum of the squares and the square of the sum is:
	//   (n(n + 1) / 2)^2 - n(n + 1)(2n + 1) / 6
	// = 1 / 12 * n(n + 1) * (n(n + 1) * 3 - (2n + 1) * 2)
	// = 1 / 12 * n(n + 1) * (3 * n^2 - n - 2)
	// = n(n + 1)((3n - 1)n - 2) / 12

	// (My aim here is to achieve the minimum number of multiplications, using the
	// initial form of the equation is also ok)

	public static void main(String[] args) {
		System.out.println(N * (N + 1) * ((3 * N - 1) * N - 2) / 12);

		// Answer: 25164150
		// Complexity: O(1)  XD

		// Homework:
		// Calculate 1^3 + 2^3 + 3^3 + ... + n^3
		// You will be amazed by the result.
	}
}
