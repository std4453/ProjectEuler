package io.github.std4453.projecteuler;

import io.github.std4453.projecteuler.utils.IntFactorized;

import java.util.stream.IntStream;

import static io.github.std4453.projecteuler.utils.StreamUtils.calcAdjacent;

/**
 *
 */
public class Problem012 {
	private static final int DIVISORS = 500;

	public static void main(String[] args) {
		IntFactorized two = IntFactorized.of(2);
		calcAdjacent(IntStream.iterate(1, n -> n + 1)
						.mapToObj(IntFactorized::of),
				IntFactorized::newMult)
				.map(f -> IntFactorized.divide(f, two))
				.filter(f -> f.getNumberOfDivisors() > DIVISORS)
				.findFirst()
				.map(IntFactorized::getNumber)
				.ifPresent(System.out::println);

		// Answer: 76576500
		// TODO: add explanations
	}
}
