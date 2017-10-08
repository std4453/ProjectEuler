package io.github.std4453.projecteuler.utils;

import java.util.PrimitiveIterator;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 *
 */
public class Primes {
	// TODO: improve speed of intPrimesStream() and longPrimesStream()

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

	/**
	 * Algorithm copied from https://stackoverflow.com/questions/43760641/java-8-streams-and-the-sieve-of-eratosthenes
	 * since a mutable field is required in this case, we use a wrapper.
	 */
	private static class IntPrimeStreamWrapper {
		private IntArrayList resolvedPrimes = new IntArrayList();
		private IntPredicate isPrime = x -> {
			PrimitiveIterator.OfInt iterator = resolvedPrimes.intIterator();
			while (iterator.hasNext()) {
				int prime = iterator.next();
				if (x % prime == 0) return false;
			}
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
		private LongArrayList resolvedPrimes = new LongArrayList();
		private LongPredicate isPrime = x -> {
			PrimitiveIterator.OfLong iterator = resolvedPrimes.longIterator();
			while (iterator.hasNext()) {
				long prime = iterator.next();
				if (x % prime == 0) return false;
			}
			return true;
		};
		private LongStream primes = LongStream.iterate(2, i -> i + 1)
				.filter(i -> isPrime.test(i))
				.peek(resolvedPrimes::add);

		LongStream getPrimes() {
			return primes;
		}
	}

	public static boolean isPrime(long n) {
		return false;
		// TODO: implement a fast algorithm
	}

	/**
	 * Generate a {@link IntArrayList} containing all the primes under {@code max}.<br />
	 * The program uses the <a href="https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes"><i>Sieve
	 * of Eratosthenes</i></a> to generate the demanded primes, with complexity {@code
	 * O(n * log(n) * log(log(n)))}.<br />
	 * This method requires spacial complexity {@code O(n)}.
	 */
	public static IntArrayList sievePrimesInt(int max) {
		IntArrayList primes = new IntArrayList();
		boolean[] notPrime = new boolean[max];
		notPrime[0] = notPrime[1] = true;
		for (int i = 0; i < max; ++i)
			if (!notPrime[i]) {
				for (int j = i * 2; j < max; j += i) notPrime[j] = true;
				primes.add(i);
			}
		return primes;
	}

	/**
	 * The {@code long} version of {@link #sievePrimesInt(int)}.<br />
	 * Note that since this method requires {@code O(n)} space and applying for amount
	 * of memory that can only be described by a {@code long} is unrealistic, this
	 * method still takes an {@code int max} instead of a {@code long max} as input.
	 * Therefore, it should be used only to save the effort (and time) converting the
	 * return value of {@link #sievePrimesInt(int)} from {@link IntArrayList} to
	 * {@link LongArrayList}.
	 *
	 * @see #sievePrimesInt(int)
	 */
	public static LongArrayList sievePrimesLong(int max) {
		LongArrayList primes = new LongArrayList();
		boolean[] notPrime = new boolean[max];
		notPrime[0] = notPrime[1] = true;
		for (int i = 0; i < max; ++i)
			if (!notPrime[i]) {
				for (int j = i * 2; j < max; j += i) notPrime[j] = true;
				primes.add((long) i);
			}
		return primes;
	}
}
