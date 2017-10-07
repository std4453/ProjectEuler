package io.github.std4453.projecteuler.utils;

import java.util.stream.IntStream;

import static io.github.std4453.projecteuler.utils.StreamUtils.asStream;

/**
 *
 */
public class NumberTheory {

	/**
	 * Factorize the given {@code int n} and return result as {@link IntFactorized}.
	 *
	 * @see IntFactorized
	 */
	public static IntFactorized factorize(int n) {
		return new IntFactorized(n);
	}

	/**
	 * Return a {@link IntStream} representing all the divisors of the given {@code int
	 * n}.<br />
	 * The returned {@link IntStream} is not ordered.
	 */
	public static IntStream intDivisorStream(int n) {
		return asStream(new Primes.IntDivisorIterator(n));
	}

	/**
	 * Return a {@link IntStream} representing all the divisors of the given
	 * {@link IntFactorized}.<br />
	 * The returned {@link IntStream} is not ordered.
	 */
	public static IntStream intDivisorStream(IntFactorized factorized) {
		return asStream(new Primes.IntDivisorIterator(factorized));
	}
}
