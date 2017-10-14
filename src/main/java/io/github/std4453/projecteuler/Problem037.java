package io.github.std4453.projecteuler;

import io.github.std4453.projecteuler.utils.Primes;

import java.util.Set;
import java.util.TreeSet;

import static io.github.std4453.projecteuler.utils.MathsHelper.pow;

/**
 * Problem #37: Truncatable primes<br />
 * The number {@code 3797} has an interesting property. Being prime itself, it is possible
 * to continuously remove digits from left to right, and remain prime at each stage:
 * {@code 3797, 797, 97}, and 7. Similarly we can work from right to left: {@code 3797,
 * 379, 37}, and 3.<br />
 * Find the sum of the only eleven primes that are both truncatable from left to right and
 * right to left.<br />
 * NOTE: {@code 2, 3, 5}, and 7 are not considered to be truncatable primes.<br />
 */
public class Problem037 {
	private static final int NUM = 11;
	private static final double EPS = 1e-6;

	private static Set<Integer> primes = new TreeSet<>();

	private static boolean isTruncatableLeftToRight(int n) {
		do {
			n /= 10;
			if (!primes.contains(n)) return false;
		} while (n >= 10);
		return true;
	}

	private static boolean isTruncatableRightToLeft(int n) {
		int log10 = (int) Math.round(Math.floor(Math.log10(n) + EPS));
		int pow10 = pow(10, log10);  // maximum power of 10 < n
		do {
			n = n % pow10;
			pow10 /= 10;
			if (!primes.contains(n)) return false;
		} while (n >= 10);
		return true;
	}

	public static void main(String[] args) {
		System.out.println(Primes.intPrimesStream()  // all primes
				.peek(primes::add)  // add to primes
				.skip(4)  // skip 2, 3, 5, 7
				.filter(Problem037::isTruncatableLeftToRight)  // ltr
				.filter(Problem037::isTruncatableRightToLeft)  // rtl
				.limit(NUM)  // all 11 primes
				.sum());  // calculate their sum

		// Answer: 748317
		// Complexity: Without a input there is no complexity.
		// Note that this program will run for several seconds before it outputs the
		// answer, please be patient.

		// TODO: add homework
	}
}
