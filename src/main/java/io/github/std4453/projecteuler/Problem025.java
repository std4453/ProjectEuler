package io.github.std4453.projecteuler;

/**
 * Problem #25: 1000-digit Fibonacci number<br />
 * The Fibonacci sequence is defined by the recurrence relation:<br />
 * {@code Fn = Fn−1 + Fn−2, where F1 = 1 and F2 = 1}.<br />
 * Hence the first 12 terms will be:<br />
 * <pre><code>
 * F1 = 1
 * F2 = 1
 * F3 = 2
 * F4 = 3
 * F5 = 5
 * F6 = 8
 * F7 = 13
 * F8 = 21
 * F9 = 34
 * F10 = 55
 * F11 = 89
 * F12 = 144
 * </code></pre>
 * The 12th term, F12, is the first term to contain three digits.<br />
 * What is the index of the first term in the Fibonacci sequence to contain 1000 digits?
 */
public class Problem025 {
	private static final int digits = 1000;

	// As described on Wikipedia:
	// https://en.wikipedia.org/wiki/Fibonacci_number#Closed-form_expression
	// fib(n) = (((1 + sqrt(5)) / 2)^n - ((1 - sqrt(5)) / 2)^n) / sqrt(5)
	// fib(n) have 1000 digits means that lg(fib(n)) >= 1000 - 1 = 999
	// Obviously n must be very large.
	// provided that abs((1 - sqrt(5)) / 2) < 1 hence abs(((1 - sqrt(5)) / 2))^n
	// is always less than 1 and quickly approaches 0, we can omit it from the equation
	// and get:
	//   lg(fib(n))
	// = lg(((1 + sqrt(5)) / 2)^n / sqrt(5))
	// = n * lg((1 + sqrt(5)) / 2) - lg(sqrt(5)) >= 999
	// So:
	// n >= (999 + lg(sqrt(5))) / lg((1 + sqrt(5)) / 2)
	// Since we want the minimum index n to have 1000 digits, n is simply:
	// n = ceil((999 + lg(sqrt(5))) / lg((1 + sqrt(5)) / 2))

	public static void main(String[] args) {
		// use Math.round() to convert double representing integer to long accurately
		System.out.println(Math.round(Math.ceil((digits - 1d + Math.log10(5) / 2) /
				Math.log10((1 + Math.sqrt(5)) / 2))));

		// Answer: 4782
		// Complexity: O(1)  XD

		// Homework:
		// 1. Prove that
		// fib(n) = (((1 + sqrt(5)) / 2)^n - ((1 - sqrt(5)) / 2)^n) / sqrt(5)
		// without looking in Wikipedia.

		// 2. Write a program to iterate through the fibonacci sequence and find the
		// required n.
		// Your challenge is to store and operate on numbers as long as 1000 digits.
		// (tips: try BigInteger)
	}
}
