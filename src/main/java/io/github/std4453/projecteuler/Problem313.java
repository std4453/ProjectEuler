package io.github.std4453.projecteuler;

import io.github.std4453.projecteuler.utils.Primes;

import static io.github.std4453.projecteuler.utils.MathsHelper.pow;

/**
 * Problem #313: Sliding game<br />
 * In a sliding game a counter may slide horizontally or vertically into an empty space.
 * The objective of the game is to move the red counter from the top left corner of a grid
 * to the bottom right corner; the space always starts in the bottom right corner. For
 * example, the following sequence of pictures show how the game can be completed in five
 * moves on a 2 by 2 grid.<br />
 * <img src="https://projecteuler.net/project/images/p313_sliding_game_1.gif" />
 * Let S(m,n) represent the minimum number of moves to complete the game on an m by n
 * grid. For example, it can be verified that S(5,4) = 25.<br />
 * <img src="https://projecteuler.net/project/images/p313_sliding_game_2.gif" />
 * There are exactly 5482 grids for which S(m,n) = p^2, where p < 100 is prime.<br />
 * How many grids does S(m,n) = p^2, where p < 106 is prime?<br />
 */
public class Problem313 {
	private static final int MAX = pow(10, 6);

	// As a fairly easy problem, I will not spend a lot of time of explaining how the
	// problem was solved, therefore the following theorem is left for the reader to
	// prove:
	// Theorem 1: For integer m > n >= 2
	// S(m, n) = m + n + 6 * n + 5 * (m - n) - 13
	// And S(n, n) = 8 * n - 11

	// Since: n^2 = 0 / 1 / 4 (mod 8)
	// And: S(n, n) = 8 * n - 11 = 5 (mod 8)
	// Therefore: S(n, n) != p^2
	// Since: n = ((m + n) - (m - n)) / 2
	// Therefore: S(m, n) = 4 * (m + n) + 2 * (m - n) - 13
	// Since: m + n = m - n (mod 2)
	// If m + n = 0 (mod 2),
	// S(m, n) = 0 + 0 - 13 = 3 (mod 8) != p^2
	// Therefore: m + n = 1 (mod2)
	// If m - n = 4 * k + 3 (k is integer & k > 0)
	// S(m, n) = 4 + 6 - 13 = 5 (mod 8) != p^2
	// Therefore: m - n = 4 * k + 1
	// m = n + 4 * k + 1
	// S(m, n) = 8 * n + 16 * k + 4 + 8 * k + 2 - 13 = 24 * k + 8 * n - 7
	// Since: S(m, n) = p^2
	// Therefore: (p^2 + 7) / 8 = 3 * k + n
	// If p = 2, (p^2 + 7) / 8 is not integer.
	// If p = 3, (p^2 + 7) / 8 = 2 = 3 * 0 + 2, m = 3, n = 2
	// If p > 3, p = 1 / 5 (mod 6) so (p^2 +7) / 8 = 1 (mod 3)
	// Since n > 1, n & k are integers, there must be:
	// k in [0, ((p^2 + 7) / 8 - 4) / 3], which has (p^2 - 1) / 24 elements
	// Therefore for a given prime p > 3, there are (p^2 - 1) / 24 integer pairs (m, n)
	// which satisfies a) m > n b) S(m, n) = p^2.
	// So for an integer MAX, if all the primes below MAX are:
	// 2, 3, p1, p2, ... pn,
	// there are:
	// sigma((pi^2 - 1) / 24, 1, n) * 2 + 2
	// integer pairs (m, n) so that sqrt(S(m, n)) is prime.

	public static void main(String[] args) {
		System.out.println(Primes.sievePrimesLong(MAX)  // all primes below max
				.longStream()  // use long because the final answer exceeds 2^31 - 1
				.skip(2)  // skip 2 & 3
				.map(n -> (n * n - 1) / 24)  // number of pairs (m, n) where m > n
				.sum() * 2 + 2);  // times 2 plus 2 and we get the answer

		// Answer: 2057774861813004
		// Answer: O(n * log(n) * log(log(n)))  // that of the sieve algorithm

		// Homework:
		// Prove Theorem 1.
		// (tips: first construct a way to complete the game in S(m, n) moves and than
		// prove it to be the smallest of all possibilities)
	}
}
