package io.github.std4453.projecteuler;

import java.util.stream.IntStream;

/**
 * Problem #36: Double-base palindromes<br />
 * The decimal number, {@code 585 = 1001001001_2} (binary), is palindromic in both
 * bases.<br />
 * Find the sum of all numbers, less than one million, which are palindromic in base 10
 * and base 2.<br />
 * (Please note that the palindromic number, in either base, may not include leading
 * zeros.)
 */
public class Problem036 {
	private static final int MAX = 1000000;

	// @see Problem004
	private static boolean isPalindrome(int n, int radix) {
		String str = Integer.toString(n, radix);
		// head & tail are 2 pointers to characters
		for (int head = 0, tail = str.length() - 1; head < tail; ++head, --tail)
			if (str.charAt(head) != str.charAt(tail)) return false;  // fail-fast
		return true;  // otherwise palindrome
	}

	public static void main(String[] args) {
		System.out.println(IntStream.range(1, MAX)
				.filter(n -> isPalindrome(n, 10) && isPalindrome(n, 2))
				.sum());

		// Answer: 872187
		// Complexity: O(n * log(n))

		// TODO: add homework
	}
}
