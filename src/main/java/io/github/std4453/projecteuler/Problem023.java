package io.github.std4453.projecteuler;

import io.github.std4453.projecteuler.utils.IntFactorized;
import io.github.std4453.projecteuler.utils.NumberTheory;

import java.util.BitSet;
import java.util.stream.IntStream;

/**
 *
 */
public class Problem023 {
	private static final int MAX_INCLUSIVE = 28123;

	public static void main(String[] args) {
		IntFactorized[] factorized = NumberTheory.intFactorizeRange(MAX_INCLUSIVE);
		BitSet isAbundant = new BitSet(MAX_INCLUSIVE + 1);
		for (int i = 1; i <= MAX_INCLUSIVE; ++i)
			if (factorized[i].getSumOfDivisors() > (i << 1)) isAbundant.set(i);

		System.out.println(IntStream.rangeClosed(1, MAX_INCLUSIVE)
				.filter(n -> !IntStream.rangeClosed(1, n >> 1)
						.filter(i -> isAbundant.get(i) && isAbundant.get(n - i))
						.findFirst()
						.isPresent())
				.sum());

		// Answer: 4179871
		// TODO: add Explanations
	}
}
