package io.github.std4453.projecteuler.utils;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
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
}
