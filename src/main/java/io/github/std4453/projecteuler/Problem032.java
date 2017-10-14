package io.github.std4453.projecteuler;

import java.util.stream.IntStream;

/**
 * Problem #32: Pandigital products<br />
 * We shall say that an n-digit number is pandigital if it makes use of all the digits 1
 * to n exactly once; for example, the 5-digit number, {@code 15234}, is 1 through 5
 * pandigital.<br />
 * The product 7254 is unusual, as the identity, {@code 39 × 186 = 7254}, containing
 * multiplicand, multiplier, and product is 1 through 9 pandigital.<br />
 * Find the sum of all products whose multiplicand/multiplier/product identity can be
 * written as a 1 through 9 pandigital.<br />
 * <i>HINT: Some products can be obtained in more than one way so be sure to only
 * include it once in your sum.</i>
 */
public class Problem032 {
	// Let a be multiplicand and b be multiplier and c be product.
	// a * b = c -> log(a) + log(b) = log(c) -> log(a * b * c) = 2log(c).
	// Since a, b and c contain all the digits from 1 to 9, a * b * c contains at most
	// 9 digits and at least 8 digits, so 7 < log(a * b * c) < 8 and 7/2 < log(c) < 4
	// Therefore c has 4 digits.
	// Assume that a < b, 1000 <= a * b < 10000 -> a < 100

	// If a digit of n is already used, return false, else mark all digits of n as used
	// and return true.
	private static boolean checkDigits(int n, boolean[] used) {
		while (n != 0) {
			int digit = n % 10;
			if (used[digit]) return false;
			used[digit] = true;
			n /= 10;
		}
		return true;
	}

	// Return true if and only if all digits from 1 to 9 are used.
	private static boolean allUsed(boolean[] used) {
		for (int i = 1; i < 10; ++i) if (!used[i]) return false;
		return true;
	}

	private static boolean isPandigital(int a, int b) {
		boolean[] used = new boolean[10];
		return checkDigits(a, used) && checkDigits(b, used) &&
				checkDigits(a * b, used) && allUsed(used);
	}

	public static void main(String[] args) {
		System.out.println(IntStream.range(1, 100)  // enumerate a
				.flatMap(a -> IntStream.rangeClosed(999 / a + 1, 9999 / a)  // enumerate b
						.filter(b -> isPandigital(a, b))  // (a, b, c) are pandigital
						.map(b -> a * b))  // map b -> c
				.distinct()  // remove same c
				.sum());  // calculate the sum

		// Answer: 45228
		// Complexity: Without a input there is no complexity.

		// TODO: add homework
	}
}
