package io.github.std4453.projecteuler;

import java.util.stream.IntStream;

import static io.github.std4453.projecteuler.utils.MathsHelper.pow;

/**
 * Problem #34: Digit factorials<br />
 * {@code 145} is a curious number, as {@code 1! + 4! + 5! = 1 + 24 + 120 = 145}.<br />
 * Find the sum of all numbers which are equal to the sum of the factorial of their
 * digits.<br />
 * <i>Note: as {@code 1! = 1} and {@code 2! = 2} are not sums they are not included.</i>
 */
public class Problem034 {
	private static final int MAX_N = 7;  // see explanation below

	// This problem is very similar to Problem #30.

	// If a is a n-digit number that can be written as the sum of the factorial of its
	// digits, we will have:
	// 10^(n - 1) = 1000...00 (n - 1 zeros) <= a <= 999...999 (n nines) and a <= n * 9!.
	// So 10^(n - 1) <= n * 9! -> n <= 7.
	// So a ranges from 10 to 9999999.

	private static int[] buildFacCache() {
		int[] facs = new int[10];
		facs[0] = facs[1] = 1;
		for (int i = 2; i < 10; ++i) facs[i] = facs[i - 1] * i;
		return facs;
	}

	private static boolean isFacSum(int n, int[] facCache) {
		int sum = 0;
		int nn = n;
		while (n > 0) {
			sum += facCache[n % 10];
			n /= 10;
		}
		return sum == nn;
	}

	public static void main(String[] args) {
		int[] facCache = buildFacCache();
		System.out.println(IntStream.range(10, pow(10, MAX_N))
				.filter(n -> isFacSum(n, facCache))
				.sum());

		// Answer: 40730
		// Complexity: O(MAX_N * 10^MAX_N)

		// TODO: add homework
	}
}
