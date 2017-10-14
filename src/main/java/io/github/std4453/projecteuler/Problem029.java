package io.github.std4453.projecteuler;

import static io.github.std4453.projecteuler.utils.MathsHelper.pow;

/**
 * Problem #29: Distinct powers<br />
 * Consider all integer combinations of {@code a^b} for {@code 2 <= a <= 5} and
 * {@code 2 <= b <= 5}:<br />
 * <pre><code>
 * 2^2 = 4, 2^3 = 8, 2^4 = 16, 2^5 = 32
 * 3^2 = 9, 3^3 = 27, 3^4 = 81, 3^5 = 243
 * 4^2 = 16, 4^3 = 64, 4^4 = 256, 4^5 = 1024
 * 5^2 = 25, 5^3 = 125, 5^4 = 625, 5^5 = 3125
 * </code></pre>
 * If they are then placed in numerical order, with any repeats removed, we get the
 * following sequence of 15 distinct terms:<br />
 * {@code 4, 8, 9, 16, 25, 27, 32, 64, 81, 125, 243, 256, 625, 1024, 3125}
 * How many distinct terms are in the sequence generated by {@code a^b} for {@code 2 <= a
 * <= 100} and {@code 2 <= b <= 100}?
 */
public class Problem029 {
	private static final int MAX = 100;
	private static final double EPS = 1e-6;
	private static final int MAX_M =
			(int) Math.round(Math.floor(Math.log(MAX) / Math.log(2) + EPS));

	// If a^b = c^d, there must be a minimum integer n, so that a = n^k and b = n^l and
	// k * b = l * d.
	// Let phi(a) be this minimum integer n so that a = n^k and psi(a) be k. a^b can be
	// expressed as c^d with a > c if and only if there exists i < psi(a) so that
	// psi(a) * b / i is integer and psi(a) * b / i < MAX.
	// Note that in this equation phi(a) is not used, which means that for any a that
	// has the same psi(a), the number of a^b that can be written as c^d is also the
	// same, defined as theta(psi(a)).
	// Let m be the maximum integer for a given integer n, so that n^m < MAX.
	// The number of (n^i)^b that can be written as (n^j)^d and i > j is
	// gamma(m) = Σtheta(i) for 2 <= i <= m.

	// Since gamma(m) is the result of a summation, pre-calculating gamma(m) in a row
	// is more efficient.
	private static int[] buildGammaCache() {
		int[] gammas = new int[MAX_M + 1];  // index ranges from 2 to MAX_M
		// used[i * j] means m = i and expo = j is enumerated
		boolean[] used = new boolean[MAX_M * MAX + 1];
		for (int m = 1; m <= MAX_M; ++m) {
			gammas[m] = gammas[m - 1];  // gamma(m) = gamma(m - 1) + theta(m)
			// calculate theta(m) by enumerating expo
			for (int expo = 2, i = m << 1; expo <= MAX; ++expo, i += m)
				if (used[i]) ++gammas[m];
				else used[i] = true;
		}
		return gammas;
	}

	private static int duplicates(int n, int[] gammaCache, boolean[] visited) {
		int m, i;  // find maximum m that n^m <= MAX
		for (m = 1, i = n; i <= MAX; i *= n, ++m) visited[i] = true;
		return gammaCache[m - 1];  // now i = n^m > MAX, subtract m by 1 and get gamma(m)
	}

	public static void main(String[] args) {
		int[] gammaCache = buildGammaCache();

		int total = pow(MAX - 1, 2);
		boolean[] visited = new boolean[MAX + 1];  // a in [2, MAX]
		for (int n = 2; n <= MAX; ++n)
			if (!visited[n]) total -= duplicates(n, gammaCache, visited);

		System.out.println(total);

		// Answer: 9183
		// Complexity: O(n * log(n))

		// TODO: add homework
	}
}
