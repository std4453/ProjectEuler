package io.github.std4453.projecteuler;

import io.github.std4453.projecteuler.utils.IntFactorized;
import io.github.std4453.projecteuler.utils.NumberTheory;

import java.util.stream.IntStream;

/**
 *
 */
public class Problem021 {
	private static final int MAX_EXCLUSIVE = 10000;

	public static void main(String[] args) {
		IntFactorized[] factorized = NumberTheory.intFactorizeRange(MAX_EXCLUSIVE - 1);
		int[] sums = new int[MAX_EXCLUSIVE];
		for (int i = 1; i < MAX_EXCLUSIVE; ++i)  // since it's "divisors lower than n"
			sums[i] = factorized[i].getSumOfDivisors() - factorized[i].getNumber();

		System.out.println(IntStream.range(1, MAX_EXCLUSIVE)
				.filter(i -> sums[i] < MAX_EXCLUSIVE)
				.filter(i -> sums[i] != i)
				.filter(i -> sums[sums[i]] == i)
				.sum());

		// Answer: 31626
		// TODO: add explanations
	}
}
