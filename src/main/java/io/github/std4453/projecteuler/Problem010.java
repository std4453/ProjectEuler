package io.github.std4453.projecteuler;

import io.github.std4453.projecteuler.utils.Primes;

/**
 * Problem #10: Summation of primes<br />
 * The sum of the primes below 10 is {@code 2 + 3 + 5 + 7 = 17}.<br />
 * Find the sum of all the primes below two million.
 */
public class Problem010 {
	private static final int MAX = 2000000;

	// This is yet another "primes" problem, testing the best speed you can arrive with
	// prime generation algorithms. For that, I chose the Sieve of Eratosthenes. Maybe
	// I or maybe you can choose another algorithm and reach even higher speed.

	public static void main(String[] args) {
		System.out.println(Primes.sievePrimesInt(MAX).intStream().asLongStream().sum());

		// Answer: 142913828922
		// Complexity: O(n * log(n) * log(log(n)))  (that of Primes.sievePrimesInt())
	}
}
