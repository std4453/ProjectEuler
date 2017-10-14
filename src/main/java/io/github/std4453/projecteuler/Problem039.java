package io.github.std4453.projecteuler;

import io.github.std4453.projecteuler.utils.IntFactorized;
import io.github.std4453.projecteuler.utils.NumberTheory;

/**
 * Problem #39: Integer right triangles<br />
 * If p is the perimeter of a right angle triangle with integral length sides, {@code
 * {a,b,c}}, there are exactly three solutions for p = 120.<br />
 * {@code {20,48,52}, {24,45,51}, {30,40,50}}<br />
 * For which value of {@code p <= 1000}, is the number of solutions maximised?
 */
public class Problem039 {
	private static final int MAX_P = 1000;

	// Let a + b + c = p, a^2 + b^2 = c^2, a <= b < c, a, b, c > 0
	// So a^2 + b^2 = (p - a - b)^2
	// So p^2 - 2pa - 2pb + 2ab = 0
	// So ab - pa - pb + p^2 = (a - p)(b - p) = p^2 / 2
	// So p must be even. Since a, b < p, a - p, b - p < 0, so
	// (p - a)(p - b) = p^2 / 2
	// Since a <= b so p - b <= p - a so p - b <= p / sqrt(2)
	// Since a <= b < c so a + b < p * 2 / 3 so 2(p - a) >= p - a + p - b > p * 4 / 3
	// p - a > p * 2 / 3 so p - b < p * 3 / 4
	// Since a <= b < c so b < p / 2 so p - b > p / 2
	// So p / 2 < b < p * 3 / 4
	// And the number p so that p^2 / 2 has maximum number of factors within this range
	// is the number p with the most solutions.

	private static int numSolutions(int p, IntFactorized pFactorized) {
		IntFactorized k = IntFactorized.divide(  // p^2 / 2
				IntFactorized.newMult(pFactorized, pFactorized), IntFactorized.of(2));
		return (int) NumberTheory.intDivisorStream(k)  // divisors
				.filter(i -> i > p >> 1)  // lower bound
				.filter(i -> i < (p * 3) << 2)  // upper bound
				.count();  // number of remaining divisors = number of solutions
	}

	public static void main(String[] args) {
		// factorize consecutively to speed up
		IntFactorized[] factorized = NumberTheory.intFactorizeRange(MAX_P);
		int maxNumSolutions = 0, maxP = 0;
		for (int p = 2; p <= MAX_P; p += 2) {  // only evens are enumerated
			int numSolutions = numSolutions(p, factorized[p]);
			if (numSolutions > maxNumSolutions) {  // if numSolutions is new record
				maxNumSolutions = numSolutions;
				maxP = p;
			}
		}
		System.out.println(maxP);  // print the result

		// Answer: 840
		// Complexity: O(?)

		// TODO: add homework
	}
}
