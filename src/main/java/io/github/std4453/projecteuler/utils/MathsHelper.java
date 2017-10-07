package io.github.std4453.projecteuler.utils;

/**
 *
 */
public class MathsHelper {
	/**
	 * Calculate {@code base^pow}, all in {@code ints}.
	 */
	public static int pow(int base, int expo) {
		int product = 1;
		for (int i = 0; i < expo; ++i) product *= base;
		return product;
	}

	/**
	 * Calculate {@code base^pow}, all in {@code longs}.
	 */
	public static long pow(long base, long expo) {
		long product = 1;
		for (long i = 0; i < expo; ++i) product *= base;
		return product;
	}
}
