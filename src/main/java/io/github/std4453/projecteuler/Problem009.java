package io.github.std4453.projecteuler;

import io.github.std4453.projecteuler.utils.IntFactorized;
import io.github.std4453.projecteuler.utils.NumberTheory;

import static io.github.std4453.projecteuler.utils.IntFactorized.*;

/**
 * Problem #9: Special Pythagorean triplet<br />
 * A Pythagorean triplet is a set of three natural numbers, {@code a < b < c}, for which,
 * <br />{@code a2 + b2 = c2}<br />
 * For example, {@code 32 + 42 = 9 + 16 = 25 = 52}.
 * There exists exactly one Pythagorean triplet for which a + b + c = 1000.
 * Find the product abc.
 */
public class Problem009 {
	private static final int SUM = 1000;

	// a + b + c = SUM
	// a^2 + b^2 = c^2
	// We can get:
	//   a^2 + b^2
	// = (SUM - a - b)^2
	// = SUM^2 + a^2 + b^2 + 2 * a * b - 2 * SUM * a - 2 * SUM * b
	// Hence:
	//   a * b - a * SUM - b * SUM + SUM^2
	// = (a - SUM)(b - SUM)
	// = SUM^2 / 2
	// So first generate all the divisors of SUM^2 / 2, and then check which ones
	// are gives the correct a, b and c - that is the answer.

	public static void main(String[] args) {
		// SUM^2 / 2 is no integer, therefore no Pythagorean triplet
		if (SUM % 2 != 0) {
			System.out.println("No answer found!");
			return;
		}

		// Since a & b < SUM so a - SUM & b - SUM < 0 so if i divides SUM^2 / 2, then
		// a = -i + SUM, b = -(SUM^2 / 2 / i) + SUM.
		// We want a < b to avoid duplicates, therefore -i < -(SUM^2 / 2 / i) and
		// i >= sqrt(SUM^2 / 2).
		// Since a & b > 0 therefore i & (SUM^2 / 2 / i) < -SUM.
		// Since i + (SUM^2 / 2 / i) >= sqrt(2) * SUM > SUM therefore c = SUM - a - b
		// = i + SUM^2 / 2 / i - SUM > 0. There's no need to filter it.

		// Here we use sumFactorized and nFactorized to speed up factorization of n a bit.

		int n = SUM * SUM / 2;
		int sqrt = (int) Math.round(Math.sqrt(n));
		IntFactorized sumFactorized = of(SUM);
		IntFactorized nFactorized = divide(
				newMult(sumFactorized, sumFactorized), of(2));
		NumberTheory.intDivisorStream(nFactorized)
				.filter(i -> i >= sqrt)  // a < b
				.filter(i -> i < SUM)  // a > 0
				.filter(i -> n / i < SUM)  // b > 0
				.map(i -> {  // calculate result
					int a = -i + SUM;
					int b = -n / i + SUM;
					int c = SUM - a - b;
					return a * b * c;
				}).forEach(System.out::println);  // print the result

		// Answer: 31875000
		// Complexity: about O(sum ^ 2)  (Mainly factorization time)
	}
}
