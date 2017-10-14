package io.github.std4453.projecteuler;

import java.util.Arrays;

/**
 * Problem #31: Coin sums<br />
 * In England the currency is made up of pound, £, and pence, p, and there are eight coins
 * in general circulation:<br />
 * {@code 1p, 2p, 5p, 10p, 20p, 50p, £1 (100p) and £2 (200p)}.<br />
 * It is possible to make £2 in the following way:<br />
 * {@code 1×£1 + 1×50p + 2×20p + 1×5p + 1×2p + 3×1p}<br />
 * How many different ways can £2 be made using any number of coins?
 */
public class Problem031 {
	private static final int N = 200;  // £2 in cents
	private static final int[] VALUES = {1, 2, 5, 10, 20, 50, 100, 200};  // in cents

	// Define a(k, n) be the ways to make n cents using coins of VALUES[0] to
	// VALUES[k]. We can easily write out the recursive equation of a(n, k) as:
	// a(k, n) = 0 for n < 0 or k <= 0
	// a(0, n) = 1 (here we take advantage of that VALUES[0] = 1)
	// a(k, 0) = 1
	// a(k, n) = a(k - 1, n) + a(k, n - VALUES[k]) for k >= 1
	// Using this, we can find any a(n, k).

	private static int[][] calcA() {
		int[][] a = new int[VALUES.length][];
		a[0] = new int[N + 1];
		Arrays.fill(a[0], 1);
		for (int k = 1; k < VALUES.length; ++k) {
			a[k] = new int[N + 1];
			a[k][0] = 1;
			for (int n = 1; n <= N; ++n)
				a[k][n] = a[k - 1][n] + (n >= VALUES[k] ? a[k][n - VALUES[k]] : 0);
		}
		return a;
	}

	public static void main(String[] args) {
		System.out.println(calcA()[VALUES.length - 1][N]);

		// Answer: 73682
		// Complexity: O(N * VALUES.length)

		// Homework:
		// 1. If we expand a(7, 200) a bit, it might look like:
		//   a(7, 200)
		// = a(6, 200) + a(7, 0)
		// = 1 + a(5, 200) + a(5, 100) + a(6, 0)
		// = ...
		// We can see here that not all a(k, n) are covered in this equation, like
		// a(7, 199) or a(6, 149) or a(5, 99), however they are all calculated in
		// calcA(), which is a complete waste.
		// Your task is to minimize the number of elements calculated in the array of
		// a, skip calculating the elements that are not accessed like a[7][199], to
		// improve the performance. Perhaps you can even throw the array away.
		// Obviously, this doesn't mean that you should use the recursive equation
		// directly, which calculates some values like a(5, 100) repeatedly and causes
		// extreme performance lost. What I recommend is that while you calculate the
		// final result recursively, cache the elements that are already calculated so
		// that you can access the value directly the next time.
		// Actually, this way of optimization is called "Dynamic Programming with
		// Memorization". For more descriptions, you can see
		// https://en.wikipedia.org/wiki/Dynamic_programming.
		// Write a program to implement this and output the correct result.

		// 2. If you haven't finished Homework 1. yet, go finish it.
		// I expect your program to execute smoothly and soundly for N = 200, however,
		// change N to big numbers like N = 100000 and run it again, does it still work?
		// If it works, congratulations, you don't have to read on.
		// If it doesn't, it the error message something about "Stack Overflow"?
		// For a ordinary brain, the program you've written in Homework 1. might look
		// like this:
		// private static int a(int k, int n) {
		//     if (n < 0 || k < 0) return 0;
		//     if (n == 0 || k == 0) return 1;
		//     if (alreadyCached(k, n)) return getCached(k, n);
		//     return a(k - 1, n) + a(k, n - VALUES[k]);
		// }
		// It calls a() recursively th get the correct result.
		// However, if you evaluate a(1, 30000), the stacktrace will look like:
		// a(1, 30000) -> a(1, 29998) -> a(1, 29996) -> ... -> a(1, 2) -> a(1, 0)
		// Which exceeds the default stack depth on a JVM of about 8000 and an
		// StackOverflowError is thrown and your program collapses.
		// My question is that: How do you modify your program to avoid the stack
		// overflow without changing the depth of the stack itself? This would be very
		// useful skill while dealing with the evaluation of some highly-recursive
		// functions.
	}
}
