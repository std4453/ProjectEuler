package io.github.std4453.projecteuler;

import java.math.BigInteger;

/**
 *
 */
public class Problem016 {
	private static final int EXPO = 1000;

	public static void main(String[] args) {
		BigInteger num = BigInteger.ZERO.setBit(EXPO);  // 1 << 1000
		String str = num.toString();
		int sum = 0;
		for (int i = 0, length = str.length(); i < length; ++i)
			sum += str.charAt(i) - '0';
		System.out.println(sum);

		// Answer: 1366
		// TODO: add explanations
	}
}
