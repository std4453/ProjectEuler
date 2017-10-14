package io.github.std4453.projecteuler;

/**
 * Problem #33: Digit cancelling fractions<br />
 * The fraction {@code 49/98} is a curious fraction, as an inexperienced mathematician in
 * attempting to simplify it may incorrectly believe that {@code 49/98} = {@code 4/8},
 * which is correct, is obtained by cancelling the 9s.<br />
 * We shall consider fractions like, {@code 30/50} = {@code 3/5}, to be trivial
 * examples.<br />
 * There are exactly four non-trivial examples of this type of fraction, less than one in
 * value, and containing two digits in the numerator and denominator.<br />
 * If the product of these four fractions is given in its lowest common terms, find the
 * value of the denominator.<br />
 */
public class Problem033 {
	// This is yet another purely mathematical problem.
	// From the description of the problem there is the following equation:
	// (10 * a + b) / (10 * b + c) = a / c, where a, b, c are positive 1-digit integers.
	// So: 10 * a * c + b * c = 10 * a * b + a * c
	// 10 * a * (b - c) = c * (b - a)
	// So b > a, b > c
	// Obviously c * (b - a) is a multiple of 10, and at least 1 of c and b - a contain
	// the factor 5.

	// If 5 | c, c can only be 5, and 2 * a * (b - 5) = b - a, so 9a = b * (2a - 1).
	// Since a < b, 2a - 1 < 9, so 3 | b. So:
	// b = 3, a = -1 wrong.
	// b = 6, a = 2, 26 / 65 = 2 / 5, correct.
	// b = 9, a = 1, 19 / 95 = 1 / 5, correct.

	// If 5 | b - a, b - a can only be 5, so 2 * a * (a + 5 - c) = c, so:
	// c = (2a^2 + 10a) / (2a + 1)
	// a = 1, b = 6, c = 4, 16 / 64 = 1 / 4, correct.
	// a = 2, b = 7, c = 28 / 5, wrong.
	// a = 3, b = 8, c = 48 / 7, wrong.
	// a = 4, b = 9, c = 8, 49 / 98 = 4 / 8, correct.

	// So the 4 fractions are 26 / 65, 19 / 95, 16 / 64 and 49 / 98.
	// 2 / 5 * 1 / 5 * 1 / 4 * 4 / 8 = 1 / 100, thus the denominator is 100.

	public static void main(String[] args) {
		System.out.println(100);

		// Answer: 100
		// Complexity: Without a input there is no complexity.

		// TODO: add homework
	}
}
