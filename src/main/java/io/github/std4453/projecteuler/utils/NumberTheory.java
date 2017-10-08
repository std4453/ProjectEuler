package io.github.std4453.projecteuler.utils;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.TreeMap;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

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

	/**
	 * {@code long} version of {@link #intDivisorStream(int)}.
	 *
	 * @see #intDivisorStream(int)
	 */
	public static LongStream longDivisorStream(long n) {
		return asStream(new LongDivisorIterator(n));
	}

	/**
	 * {@code long} version of {@link #intDivisorStream(IntFactorized)}.
	 *
	 * @see #intDivisorStream(IntFactorized)
	 */
	public static LongStream longDivisorStream(LongFactorized factorized) {
		return asStream(new LongDivisorIterator(factorized));
	}

	static class IntDivisorIterator implements PrimitiveIterator.OfInt {
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

	static class LongDivisorIterator implements PrimitiveIterator.OfLong {
		private int size;
		private int[] max;
		private long[] bases;
		private int[] exponents;

		LongDivisorIterator(long n) {
			this(new LongFactorized(n));
		}

		LongDivisorIterator(LongFactorized factorized) {
			this.size = factorized.size();
			this.max = new int[this.size];
			this.bases = new long[this.size];
			this.exponents = new int[this.size];
			int index = 0;
			for (Map.Entry<Long, Integer> factor : factorized) {
				this.bases[index] = factor.getKey();
				this.max[index] = factor.getValue();
				++index;
			}
		}

		@Override
		public long nextLong() {
			int first = this.firstNotMax();
			if (first == -1) throw new NoSuchElementException();
			++this.exponents[first];
			for (int i = 0; i < first; ++i) this.exponents[i] = 0;

			long product = 1;
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
	 * Factorize the list integers from 1 to {@code maxInclusive} as an array of
	 * {@link IntFactorized}.<br />
	 * This is faster than {@code IntStream.rangeClosed(1, maxInclusive).map
	 * (IntFactorized::new)} and should be taken into account under similar
	 * circumstances.<br />
	 * Note that the returned array of {@link IntFactorized} has length of {@code
	 * maxInclusive + 1} and the element at index 0 is {@code null}. To get the
	 * {@link IntFactorized} instance of integer {@code i}, use {@code factorized[i]}.
	 */
	@SuppressWarnings("unchecked")
	public static IntFactorized[] intFactorizeRange(int maxInclusive) {
		Map<Integer, Integer>[] factorsMap = new Map[maxInclusive + 1];
		for (int i = 1; i <= maxInclusive; ++i) factorsMap[i] = new TreeMap<>();

		IntArrayList primes = Primes.sievePrimesInt(maxInclusive + 1);
		primes.forEach((IntConsumer) p -> {
			for (int i = p, j = 1; i <= maxInclusive; i += p, ++j)
				factorsMap[i].put(p, factorsMap[j].getOrDefault(p, 0) + 1);
		});

		IntFactorized[] factorized = new IntFactorized[maxInclusive + 1];
		for (int i = 1; i <= maxInclusive; ++i)
			factorized[i] = new IntFactorized(factorsMap[i]);
		return factorized;
	}

	/**
	 * {@code long} version of {@link #intFactorizeRange(int)}.<br />
	 * Note that since this method requires {@code O(n)} space and applying for amount
	 * of memory that can only be described by a {@code long} is unrealistic, this
	 * method still takes an {@code int maxInclusive} instead of a {@code long
	 * maxInclusive} as input. Therefore, it should be used only to save the effort (and
	 * time) converting the return value of {@link #intFactorizeRange(int)} from {@link
	 * IntFactorized} to {@link LongFactorized}.
	 */
	@SuppressWarnings("unchecked")
	public static LongFactorized[] longFactorizeRange(int maxInclusive) {
		Map<Long, Integer>[] factorsMap = new Map[maxInclusive + 1];
		for (int i = 1; i <= maxInclusive; ++i) factorsMap[i] = new TreeMap<>();

		IntArrayList primes = Primes.sievePrimesInt(maxInclusive + 1);
		primes.forEach((IntConsumer) p -> {
			for (int i = p, j = 1; i <= maxInclusive; i += p, ++j)
				factorsMap[i].put((long) p, factorsMap[j].getOrDefault(p, 0) + 1);
		});

		LongFactorized[] factorized = new LongFactorized[maxInclusive + 1];
		for (int i = 1; i <= maxInclusive; ++i)
			factorized[i] = new LongFactorized(factorsMap[i]);
		return factorized;
	}
}
