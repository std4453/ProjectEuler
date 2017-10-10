package io.github.std4453.projecteuler;

import java.math.BigInteger;

/**
 * Problem #16: Power digit sum<br />
 * {@code 2^15 = 32768} and the sum of its digits is {@code 3 + 2 + 7 + 6 + 8 = 26}.<br />
 * What is the sum of the digits of the number 2^1000?
 */
public class Problem016 {
	private static final int EXPO = 1000;

	// Straightforward indeed.

	public static void main(String[] args) {
		BigInteger num = BigInteger.ZERO.setBit(EXPO);  // 1 << 1000
		String str = num.toString();  // to string
		int sum = 0;
		for (int i = 0, length = str.length(); i < length; ++i)
			sum += str.charAt(i) - '0';  // calculate sum of digits
		System.out.println(sum);  // print result

		// Answer: 1366
		// Complexity: O(log(n))

		// Homework:
		// 1. Finish homework of Problem #13.

		// 2. See http://oeis.org/A001370
	}
}
