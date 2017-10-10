package io.github.std4453.projecteuler;

/**
 * Problem #14: Longest Collatz sequence<br />
 * The following iterative sequence is defined for the set of positive integers:<br />
 * {@code n → n/2} (n is even)<br />
 * {@code n → 3n + 1} (n is odd)<br />
 * Using the rule above and starting with 13, we generate the following sequence:<br />
 * {@code 13 → 40 → 20 → 10 → 5 → 16 → 8 → 4 → 2 → 1}<br />
 * It can be seen that this sequence (starting at 13 and finishing at 1) contains 10
 * terms. Although it has not been proved yet (Collatz Problem), it is thought that all
 * starting numbers finish at 1.<br />
 * Which starting number, under one million, produces the longest chain? <br />
 * <i>NOTE: Once the chain starts the terms are allowed to go above one million.</i>
 */
public class Problem014 {
	private static final int MAX = 1000000;

	// The Collatz sequence has a very clear definition and little in maths can be done
	// to simplify this problem, therefore it is more a programmatic problem.

	// I admit that my implementation is a bit ugly, trying to strike the best balance
	// between time and space consumption.
	// My solution takes advantage of one fact: If a number exists in a Collatz chain
	// and is not the starting number, then the number is *visited*. If a chain starts
	// from a *visited* number, than it's certainly not the longest chain.
	// Therefore, we start from MAX - 1, where the chain should be statistically
	// longer, iterating and recording *visited* numbers.

	public static void main(String[] args) {
		int maxNumber = 0, maxLength = 0;
		boolean[] visited = new boolean[MAX];

		for (int i = MAX - 1; i >= 1; --i)  // traverse backwards
			if (!visited[i]) {
				int length = 0;  // length of the Collatz chain starting from i

				for (long k = i; k != 1; ++length,  // iterate
						k = (k & 1L) == 0 ? (k >> 1) : (k * 3 + 1))
					if (k < MAX) visited[(int) k] = true;  // mark k as visited

				if (length > maxLength) {  // update max
					maxLength = length;
					maxNumber = i;
				}
			}
		System.out.println(maxNumber);  // print the result

		// Answer: 837799
		// Complexity: O(?)

		// Homework:
		// Prove that for all positive integer n, Collatz^k(n) will finally end up
		// becoming 1.
		// (Just kidding!)
	}
}
