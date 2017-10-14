package io.github.std4453.projecteuler;

import java.util.stream.LongStream;

/**
 * Problem #300: Integer sided equiangular hexagons<br />
 * Let H(n) be the number of distinct integer sided equiangular convex hexagons with
 * perimeter not exceeding n.<br />
 * Hexagons are distinct if and only if they are not congruent.<br />
 * You are given {@code H(6) = 1, H(12) = 10, H(100) = 31248}.<br />
 * Find H(55106).<br />
 * <img src="https://projecteuler.net/project/images/p600_equiangular_hexagons.png" />
 * <br /><i>Equiangular hexagons with perimeter not exceeding 12</i>
 */
public class Problem300 {
	private static final int N = 55106;

	// Let length the 6 edges of a hexagon in order be a, b, c, d, e and f,
	// we can infer that d - a = f - c = b - e.
	// Thus we can uniquely determine a integer sided equiangular hexagon with 4
	// positive integers 1 <= l1 < l2 < l3 and dl, so that
	// a = l1, b = l2, c = l3, d - a = f - c = b - e = l4,
	// and the perimeter is 2(l1 + l2 + l3) + 3l4.

	// Let K(n) be the distinct number of triplets of non-negative integer (l1, l2, l3)
	// so that l1 < l2 < l3 and l1 + l2 + l3 = n.
	// Calculating a bit by hand, we get that K(0) = 1, K(1) = 1, K(2) = 2, K(3) = 3,
	// K(4) = 4.
	// Actually, K(n) is the number of partitions of n into at most 3 parts, and
	// A001399 on OEIS (http://oeis.org/A001399), where we can find the recursive
	// formula of K(n):
	// K(n) = 1 + a(n - 2) + a(n - 3) - a(n - 5)
	// With this, we can calculate any K(n).

	// Let J(n) be the number of distinct integer sided equiangular convex hexagons
	// with perimeter of EXACTLY n.
	// So H(n) = ΣJ(i) for 6 <= i <= n,
	// and J(n) = ΣK((n - P(n)) / 2 - 3i) for 0 <= i <= floor((n - P(n)) / 6, where
	// P(n) = 6 for even n and 9 for odd n.
	// We can also infer that J(n) = J(n - 6) + K((n - P(n)) / 2).
	// With J(6) = 1, J(7) = 0, J(8) = 1, J(9) = 1, J(10) = 2, J(11) = 1,
	// we can calculate any J(n).
	// And in the end we can get any H(n).

	private static long[] calcK(int maxExclusive) {
		long[] Ks = new long[maxExclusive];
		Ks[0] = 1;
		Ks[1] = 1;
		Ks[2] = 2;
		Ks[3] = 3;
		Ks[4] = 4;
		for (int i = 5; i < maxExclusive; ++i)
			Ks[i] = 1 + Ks[i - 2] + Ks[i - 3] - Ks[i - 5];
		return Ks;
	}

	private static long[] calcJ(int maxExclusive) {
		long[] Ks = calcK(maxExclusive >> 1);
		long[] Js = new long[maxExclusive];
		Js[6] = 1;
		Js[7] = 0;
		Js[8] = 1;
		Js[9] = 1;
		Js[10] = 2;
		Js[11] = 1;
		for (int i = 12; i < maxExclusive; ++i)
			Js[i] = Js[i - 6] + Ks[(i - ((i & 1) == 0 ? 6 : 9)) >> 1];
		return Js;
	}

	private static long calcH(int n) {
		long[] Js = calcJ(n + 1);
		return LongStream.of(Js).sum();
	}

	public static void main(String[] args) {
		System.out.println(calcH(N));

		// Answer: 2668608479740672
		// Complexity: O(n)

		// TODO: add homework
	}
}
