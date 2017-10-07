package io.github.std4453.projecteuler.utils;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static io.github.std4453.projecteuler.utils.StreamUtils.asStream;

/**
 *
 */
public class NumberTheory {
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

	private static class IntDivisorIterator implements PrimitiveIterator.OfInt {
		private int size;
		private int[] max;
		private int[] bases;
		private int[] exponents;

		IntDivisorIterator(int n) {
			this(new IntFactorized(n));
		}

		IntDivisorIterator(IntFactorized factorized) {
			this.size = factorized.size();
			this.max = new int[this.size];
			this.bases = new int[this.size];
			this.exponents = new int[this.size];
			int index = 0;
			for (Map.Entry<Integer, Integer> factor : factorized) {
				this.bases[index] = factor.getKey();
				this.max[index] = factor.getValue();
				++index;
			}
		}

		@Override
		public int nextInt() {
			int first = this.firstNotMax();
			if (first == -1) throw new NoSuchElementException();
			++this.exponents[first];
			for (int i = 0; i < first; ++i) this.exponents[i] = 0;

			int product = 1;
			for (int i = 0; i < this.size; ++i)
				product *= MathsHelper.pow(this.bases[i], this.exponents[i]);
			return product;
		}

		@Override
		public boolean hasNext() {
			return this.firstNotMax() != -1;
		}

		private int firstNotMax() {
			for (int i = 0; i < this.size; ++i)
				if (this.exponents[i] < this.max[i]) return i;
			return -1;
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
		return asStream(new IntDivisorIterator(n));
	}

	/**
	 * Return a {@link IntStream} representing all the divisors of the given
	 * {@link IntFactorized}.<br />
	 * The returned {@link IntStream} is not ordered.
	 */
	public static IntStream intDivisorStream(IntFactorized factorized) {
		return asStream(new IntDivisorIterator(factorized));
	}
}
