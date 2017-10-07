package io.github.std4453.projecteuler;

import java.math.BigInteger;

/**
 *
 */
public class Problem020 {
	private static final int N = 100;

	public static void main(String[] args) {
		BigInteger integer = BigInteger.ONE;
		for (int i = 2; i <= 100; ++i) integer = integer.multiply(BigInteger.valueOf(i));
		String str = integer.toString();
		int sum = 0;
		for (int i = 0, length = str.length(); i < length; ++i)
			sum += str.charAt(i) - '0';
		System.out.println(sum);

		// Answer: 648
		// TODO: add explanations
	}
}
