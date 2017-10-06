package io.github.std4453.projecteuler;

import java.util.stream.IntStream;

/**
 * Problem #1: Multiples of 3 and 5<br />
 * If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3,
 * 5, 6 and 9. The sum of these multiples is 23.<br />
 * Find the sum of all the multiples of 3 or 5 below 1000.
 */
public class Problem001 {
	private static final int MAX_EXCLUSIVE = 1000; // because it says "below 1000"

	public static void main(String[] args) {
		System.out.println(IntStream.range(1, MAX_EXCLUSIVE) // all integers
				.filter(n -> n % 3 == 0 || n % 5 == 0) // that can be divided by 3 or 5
				.sum()); // calculate the sum

		// Answer: 233168
		// Complexity: O(n)

		// Homework:
		// 1. Why can't I write:
		// System.out.println(IntStream.iterate(1, n -> n + 1)
		//         .filter(n -> n % 3 == 0 || n % 5 == 0)
		//         .filter(n -> n < MAX_EXCLUSIVE)
		//         .sum());
		// The program will never terminate.

		// 2. Rewrite the program using StreamUtils.limitUntil()
	}
}
