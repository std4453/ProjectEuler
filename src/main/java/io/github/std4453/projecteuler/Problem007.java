package io.github.std4453.projecteuler;

import io.github.std4453.projecteuler.utils.Primes;

/**
 * Problem #7: 10001st prime<br />
 * By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see that the 6th
 * prime is 13.<br />
 * What is the 10 001st prime number?
 */
public class Problem007 {
	private static final int N = 10001;

	public static void main(String[] args) {
		Primes.intPrimesStream()  // all primes
				.skip(N - 1)  // the Nth
				.findFirst()  // find it
				.ifPresent(System.out::println);

		// Answer: 104743
		// Complexity: O(n)

		// Homework... No, have a day free!
	}
}
