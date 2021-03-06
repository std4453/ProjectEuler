package io.github.std4453.projecteuler;

import java.util.PrimitiveIterator;

import static io.github.std4453.projecteuler.utils.StreamUtils.asStream;
import static io.github.std4453.projecteuler.utils.StreamUtils.limitUntil;

/**
 * Problem #2: Even Fibonacci numbers<br />
 * Each new term in the Fibonacci sequence is generated by adding the previous two terms.
 * By starting with 1 and 2, the first 10 terms will be:<br />
 * {@code 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, ...}
 * By considering the terms in the Fibonacci sequence whose values do not exceed four
 * million, find the sum of the even-valued terms.
 */
public class Problem002 {
	private static final int MAX_INCLUSIVE = 4000000; // because it says "do not exceed"

	// The iterator whose nextInt() outputs all the fibonacci numbers sequentially
	private static class FibonacciIterator implements PrimitiveIterator.OfInt {
		private int a, b;

		FibonacciIterator(int first, int second) {
			this.a = (first << 1) - second;
			this.b = second - first;

			// we want first & second be also counted, therefore we initialize this.a
			// and this.b as the previous 2 numbers in the fibonacci sequence.

			// Thus, solve:
			// a + b = first
			// b + first = second
			// We get:
			// a = first * 2 - second     (bit-op used here)
			// b = second - first
		}

		@Override
		public int nextInt() {
			// calculate fibonacci and update a and b

			int c = this.a + this.b;
			this.a = this.b;
			this.b = c;
			return c;
		}

		@Override
		public boolean hasNext() {
			return true; // the fibonacci sequence is infinite
		}
	}

	public static void main(String[] args) {
		System.out.println(
				limitUntil(
						asStream(new FibonacciIterator(1, 2)), // all fibonacci numbers
						n -> n > MAX_INCLUSIVE) // less than of equal to MAX_INCLUSIVE
						.filter(n -> (n & 0x1) == 0) // that are even
						.sum()); // calculate the sum

		// Answer: 4613732
		// Complexity: O(log n)  (=the growing speed of fib(n))

		// Homework:
		// 1. How many fibonacci numbers are there below 4 million? Modify the program
		// using Stream.count()

		// 2. Can you solve 1. without using a computer program?
	}
}
