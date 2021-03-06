package io.github.std4453.projecteuler;

import io.github.std4453.projecteuler.utils.IntFactorized;

import java.util.stream.IntStream;

import static io.github.std4453.projecteuler.utils.StreamUtils.calcAdjacent;

/**
 * Problem #12: Highly divisible triangular number<br />
 * The sequence of triangle numbers is generated by adding the natural numbers. So the 7th
 * triangle number would be {@code 1 + 2 + 3 + 4 + 5 + 6 + 7 = 28}. The first ten terms
 * would be:<br />
 * {@code 1, 3, 6, 10, 15, 21, 28, 36, 45, 55, ...}<br />
 * Let us list the factors of the first seven triangle numbers:<br />
 * <pre><code>
 * 1: 1
 * 3: 1,3
 * 6: 1,2,3,6
 * 10: 1,2,5,10
 * 15: 1,3,5,15
 * 21: 1,3,7,21
 * 28: 1,2,4,7,14,28
 * </code></pre>
 * We can see that 28 is the first triangle number to have over five divisors.<br />
 * What is the value of the first triangle number to have over five hundred divisors?
 */
public class Problem012 {
	private static final int DIVISORS = 500;

	// The only challenge of this problem is that factorizing a large number is
	// relatively slow, therefore we first factorize n and n + 1 so that calculating
	// factorized form of n * (n + 1) / 2 will be a lot faster.

	public static void main(String[] args) {
		IntFactorized two = IntFactorized.of(2);  // cache
		calcAdjacent(IntStream.iterate(1, n -> n + 1)  // stream of positive integers
						.mapToObj(IntFactorized::of),  // factorized
				IntFactorized::newMult)  // multiply to get n * (n + 1)
				.map(f -> IntFactorized.divide(f, two))  // -> n * (n + 1) / 2 = T(n)
				.filter(f -> f.getNumberOfDivisors() > DIVISORS)  // the first T(n)
				.findFirst()  // to have over 500 divisors
				.map(IntFactorized::getNumber)  // calc T(n)
				.ifPresent(System.out::println);  // and print the result

		// Answer: 76576500
		// Complexity: O(?)
		// (mathematical expectation of number containing n divisors is unknown)

		// Homework:
		// 1. Find n that T(n) is 76576500.

		// 2. We know that there is only one even number in n and n + 1, hence
		// construct the sequence a(n) that:
		// If n = 2 * k (k is integer), a(n) = k, else a(n) = n.
		// And let f(n) be the number of divisors of n.
		// Given that T(n) = a(n) * a(n + 1) and a(n) coprimes with a(n + 1), we have:
		// f(T(n)) = f(a(n)) * f(a(n + 1)).
		// Use a(n) and LongFactorized and write a program to find the first triangle
		// number that has more than 2000 divisors.
	}
}
