package io.github.std4453.projecteuler;

import java.util.stream.IntStream;

/**
 *
 */
public class Problem004 {
	private static final int DIGITS = 4;
	private static final int RADIX = 10;
	private static final int MAX_EXCLUSIVE;
	private static final int MIN_INCLUSIVE;

	// Here a static block is used to initiate MAX_EXCLUSIVE and MIN_INCLUSIVE.
	// MAX_EXCLUSIVE = RADIX ^ DIGITS, MIN_INCLUSIVE = RADIX ^ (DIGITS - 1)
	static {
		int n = 1;
		for (int i = 0; i < DIGITS; ++i) n *= RADIX;
		MAX_EXCLUSIVE = n;
		MIN_INCLUSIVE = n / RADIX;
	}

	public static void main(String[] args) {
		IntStream.range(MIN_INCLUSIVE, MAX_EXCLUSIVE)  // multiplier #1
				.flatMap(n -> IntStream.range(n, MAX_EXCLUSIVE)  // multiplier #2
						.map(i -> i * n))  // multiply them
				.filter(Problem004::isPalindrome)  // which are palindrome
				.max()  // get the maximum
				.ifPresent(System.out::println);  // print the answer

		// Answer: 906609
		// Complexity: O(digits * digits ^ (radix * 2))  (seems a bit slow...)

		// Homework: (for myself)
		// TODO: find a better algorithm
	}

	// This is a very traditional way to detect whether a number is a palindrome:
	// convert it into a string and do string palindrome detect.
	private static boolean isPalindrome(int n) {
		String str = Integer.toString(n, RADIX);  // number -> string
		// head & tail are 2 pointers to characters
		for (int head = 0, tail = str.length() - 1; head < tail; ++head, --tail)
			if (str.charAt(head) != str.charAt(tail)) return false;  // fail-fast
		return true;  // otherwise palindrome
	}
}
