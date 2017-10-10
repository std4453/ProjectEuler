package io.github.std4453.projecteuler;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Problem #26: Reciprocal cycles<br />
 * A unit fraction contains 1 in the numerator. The decimal representation of the unit
 * fractions with denominators 2 to 10 are given:<br />
 * <pre><code>
 * 1/2	= 	0.5
 * 1/3	= 	0.(3)
 * 1/4	= 	0.25
 * 1/5	= 	0.2
 * 1/6	= 	0.1(6)
 * 1/7	= 	0.(142857)
 * 1/8	= 	0.125
 * 1/9	= 	0.(1)
 * 1/10	= 	0.1
 * </code></pre>
 * Where 0.1(6) means 0.166666..., and has a 1-digit recurring cycle. It can be seen that
 * 1/7 has a 6-digit recurring cycle.<br />
 * Find the value of d < 1000 for which 1/d contains the longest recurring cycle in its
 * decimal fraction part.<br />
 */
public class Problem026 {
	private static final int MAX = 1000;

	// As a rational number, 1 / n can always be described as:
	// 1 / n = 0.a(b),
	// where 10^k < a < 10^(k + 1) and 10^l < b < 10^(l + 1), k & l are positive integer.
	// Intuitively, a have k digits and b have l.
	// With a and b, we can write 1 / n in the following form:
	// 1 / n = a / 10^k + b / 10^k * (1 / 10^l + 1 / 10^(2 * l) + 1 / 10^(3 * l) + ...)
	// Using some middle-school mathematics, we can get:
	// 1 / n = (a * (10^l - 1) + b) / (10^k * (10^l - 1))
	// So 10^k * (10^l - 1) = n * (a * (10^l - 1) + b)
	// Let p = n / m so that m = 2^i * 5^j, p != 0 (mod 2), p != 0 (mod 5)
	// If p = 1, 1 / n has no recurring cycle.
	// If p != 1, there must be: 10^l - 1 = 0 (mod p)
	// And that the smallest l that satisfies this relation is the length of the
	// recurring cycle of 1 / n. (Proof: Homework)

	public static void main(String[] args) {
		Map<Integer, BigInteger> lMap = new HashMap<>();  // map l to 10^l - 1
		int longest = 0, maxD = 0;  // longest
		for (int d = 2; d < MAX; ++d) {
			// since d / 2 & d / 5 < d, it must have been tried before
			if (d % 2 == 0 || d % 5 == 0) continue;
			BigInteger biD = BigInteger.valueOf(d);  // biD = BigInteger of d
			for (int l = 1; ; ++l) {  // find smallest l
				// cache 10^l - 1 to improve performance
				if (!lMap.containsKey(l))  // put 10^l - 1 if absent
					lMap.put(l, BigInteger.TEN.pow(l).subtract(BigInteger.ONE));
				if (lMap.get(l).mod(biD).equals(BigInteger.ZERO)) {
					if (l > longest) {
						longest = l;
						maxD = d;
					}
					break;
				}
			}
		}
		System.out.println(maxD);  // print the result

		// Answer: 983
		// Complexity: O(n^2 * log(n))

		// Homework:
		// Prove that the smallest l that satisfies 10^l - 1 = 0 (mod p) for
		// p = n / m so that m = 2^i * 5^j, p != 0 (mod 2), p != 0 (mod 5) is the
		// length of the recurring cycle of 1 / n.
	}
}
