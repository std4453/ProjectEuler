package io.github.std4453.projecteuler;

import io.github.std4453.projecteuler.utils.LongFactorized;

import java.util.function.Supplier;
import java.util.stream.LongStream;

/**
 *
 */
public class Problem015 {
	private static final int X = 20;
	private static final int Y = 20;

	public static void main(String[] args) {
		System.out.println(c(X + Y, X));

		// Answer: 137846528820
		// TODO: add explanations
	}

	private static long c(int n, int a) {
		Supplier<RuntimeException> orElse = () -> new RuntimeException("???");
		return LongFactorized.divide(
				LongStream.rangeClosed(n - a + 1, n)
						.mapToObj(LongFactorized::of)
						.reduce(LongFactorized::newMult)
						.orElseThrow(orElse),
				LongStream.rangeClosed(1, a)
						.mapToObj(LongFactorized::of)
						.reduce(LongFactorized::newMult)
						.orElseThrow(orElse))
				.getNumber();
	}
}
