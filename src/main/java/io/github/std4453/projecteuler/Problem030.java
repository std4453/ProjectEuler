package io.github.std4453.projecteuler;

import java.util.stream.IntStream;

import static io.github.std4453.projecteuler.utils.MathsHelper.pow;

/**
 * Problem #30: Digit fifth powers<br />
 * Surprisingly there are only three numbers that can be written as the sum of fourth
 * powers of their digits:<br />
 * <pre><code>
 * 1634 = 1^4 + 6^4 + 3^4 + 4^4
 * 8208 = 8^4 + 2^4 + 0^4 + 8^4
 * 9474 = 9^4 + 4^4 + 7^4 + 4^4
 * </code></pre>
 * As {@code 1 = 1^4} is not a sum it is not included.<br />
 * The sum of these numbers is {@code 1634 + 8208 + 9474 = 19316}.<br />
 * Find the sum of all the numbers that can be written as the sum of fifth powers of their
 * digits.
 */
public class Problem030 {
	private static final int POW = 5;
	private static final int MAX_N = 6;  // see explanation below

	// If a is a n-digit number that can be written as the sum of fifth powers of its
	// digits, we will have:
	// 10^(n - 1) = 1000...00 (n - 1 zeros) <= a <= 999...999 (n nines) and a <= n * 9^5.
	// So 10^(n - 1) <= n * 9^5 -> n <= 6.
	// So a ranges from 10 to 999999.

	private static int[] buildPowCache() {
		int[] cache = new int[10];  // 0~9
		cache[1] = 1;
		for (int i = 2; i < 10; ++i) cache[i] = pow(i, POW);
		return cache;
	}

	private static boolean isPowSum(int n, int[] powCache) {
		int sum = 0;
		int nn = n;  // copy of n
		while (n != 0) {
			sum += powCache[n % 10];
			n /= 10;
		}
		return sum == nn;
	}

	public static void main(String[] args) {
		int[] powCache = buildPowCache();
		System.out.println(IntStream.range(10, pow(10, MAX_N))
				.filter(n -> isPowSum(n, powCache))
				.sum());

		// Answer: 443839
		// Complexity: O(MAX_N * 10^MAX_N)

		// TODO: add homework
	}
}
