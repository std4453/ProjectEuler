package io.github.std4453.projecteuler.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 *
 */
public class NumberTheory {
	/**
	 * Algorithm copied from https://stackoverflow.com/questions/43760641/java-8-streams-and-the-sieve-of-eratosthenes
	 * since a mutable field is required in this case, we use a wrapper.
	 */
	private static class IntPrimeStreamWrapper {
		private List<Integer> resolvedPrimes = new ArrayList<>();
		private IntPredicate isPrime = x -> {
			for (Integer prime : resolvedPrimes)
				if (x % prime == 0) return false;
			return true;
		};
		private IntStream primes = IntStream.iterate(2, i -> i + 1)
				.filter(i -> isPrime.test(i))
				.peek(resolvedPrimes::add);

		IntStream getPrimes() {
			return primes;
		}
	}

	/**
	 * {@code long} version of {@link IntPrimeStreamWrapper}.
	 *
	 * @see IntPrimeStreamWrapper
	 */
	private static class LongPrimeStreamWrapper {
		private List<Long> resolvedPrimes = new ArrayList<>();
		private LongPredicate isPrime = x -> {
			for (Long prime : resolvedPrimes)
				if (x % prime == 0) return false;
			return true;
		};
		private LongStream primes = LongStream.iterate(2, i -> i + 1)
				.filter(i -> isPrime.test(i))
				.peek(resolvedPrimes::add);

		LongStream getPrimes() {
			return primes;
		}
	}

	/**
	 * Construct a new {@link IntStream} containing all the primes.
	 */
	public static IntStream intPrimesStream() {
		return new IntPrimeStreamWrapper().getPrimes();
	}

	/**
	 * Construct a new {@link LongStream} containing all the primes.
	 */
	public static LongStream longPrimesStream() {
		return new LongPrimeStreamWrapper().getPrimes();
	}

	public static boolean isPrime(long n) {
		return false;
		// TODO: implement a fast algorithm
	}

	public static IntFactorized factorize(int n) {
		return new IntFactorized(n);
	}
}
