package io.github.std4453.projecteuler.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.github.std4453.projecteuler.utils.StreamUtils.limitUntil;

/**
 * {@code long} version of {@link IntFactorized}.<br />
 * Documentations are copied directly from {@link IntFactorized}.
 */
public class LongFactorized implements Iterable<Map.Entry<Long, Integer>> {
	private TreeMap<Long, Integer> factors;

	private LongFactorized(TreeMap<Long, Integer> factors) {
		this.factors = factors;
	}

	private LongFactorized() {
		this(new TreeMap<>());
	}

	public LongFactorized(long n) {
		this();

		this.factorize(n);
	}

	/**
	 * The copying constructor of {@link LongFactorized}.
	 */
	public LongFactorized(LongFactorized toCopy) {
		this();

		this.factors.putAll(toCopy.factors);
	}

	/**
	 * Factorize the given number and save the results in {@link #factors this.factors}.
	 */
	private void factorize(final long num) {
		if (num == 1) return; // avoid bug

		new Runnable() {
			// Because n is modified inside lambdas, we need a object instance to
			// contain it, therefore we construct a anonymous Runnable here
			private long n = num;

			@Override
			public void run() {
				// Here the factorization algorithm behaves similarly to the answer of
				// Problem #3. Therefore see comments in Problem003 for more details.

				long sqrt = Math.round(Math.sqrt(num));  // calculate max
				// NOTE THE "!" UNDER HERE!
				if (!limitUntil(Primes.longPrimesStream(),  // all primes
						p -> p > sqrt)  // less than or equal to sqrt(num)
						.filter(p -> num % p == 0)  // that can divide num
						.peek(p -> {  // calculate exponent
							int expo = 0;
							while (n % p == 0) {
								n /= p;
								++expo;
							}
							LongFactorized.this.factors.put(p, expo);  // put result
						})
						.filter(p -> n == 1)  // if remaining 1
						.findFirst()  // terminate the stream
						.isPresent()) {  // otherwise the remaining n is the last factor
					LongFactorized.this.factors.put(n, 1);  // put it
				}
			}
		}.run();
	}

	/**
	 * Return the number represented by this {@link LongFactorized}.<br />
	 * Note that the number is dynamically calculated using the factors, therefore
	 * applications should cache the result if is to be used many times.
	 */
	public long getNumber() {
		return this.stream().mapToLong(factor ->  // map factor to power
				MathsHelper.pow(factor.getKey(), factor.getValue()))
				.reduce(1, (a, b) -> a * b);  // multiply them
	}

	/**
	 * Return the number of divisors of the number represented by this
	 * {@link LongFactorized}.<br />
	 * Note that the number is dynamically calculated using the factors, therefore
	 * applications should cache the result if is to be used many times.
	 */
	public long getNumberOfDivisors() {
		// given exponents k1, k2 ... kn, there are (k1 + 1)(k2 + 1)...(kn + 1) divisors
		return this.stream().mapToLong(factor -> factor.getValue() + 1)
				.reduce(1, (a, b) -> a * b);  // multiply them
	}

	/**
	 * Update {@code this} to represent the <i>least common multiple</i> of {@code
	 * this} and the given {@link LongFactorized} {@code n}.<br />
	 * This method modifies the content of {@code this}.
	 *
	 * @see #merge(LongFactorized, BiFunction)
	 */
	public void lcm(LongFactorized n) {
		this.merge(n, (current, newComing) ->
				current == null ? newComing : Math.max(current, newComing));
	}

	/**
	 * Update {@code this} to represent {@link #getNumber() this.getNumber} {@code *}
	 * {@link #getNumber() n.getNumber()}.<br />
	 * This method modifies the content of {@code this}.
	 *
	 * @see #merge(LongFactorized, BiFunction)
	 */
	public void mult(LongFactorized n) {
		this.merge(n, (current, newComing) ->
				current == null ? newComing : current + newComing);
	}

	/**
	 * Merge a given non-null {@link LongFactorized} {@code n} into {@code this} using a
	 * given merging {@link BiFunction function} {@code fn}.<br />
	 * This method modifies the content of {@code this}.<br />
	 *
	 * @param fn
	 * 		{@code (current, newComing) -> WHATEVER}. Note that {@code current} and
	 * 		{@code newComing} are all {@link Integer} instead of {@code int}.
	 *
	 * @throws NullPointerException
	 * 		If either {@code fn} or {@code n} is {@code null}.
	 */
	private void merge(LongFactorized n, BiFunction<Integer, Integer, Integer> fn) {
		Objects.requireNonNull(n);
		Objects.requireNonNull(fn);
		TreeMap<Long, Integer> factors = this.factors;  // shortcut
		n.stream().forEach(factor -> {  // traverse
			Long base = factor.getKey();  // use Long instead of long to avoid
			Integer expo = factor.getValue();  // unnecessary boxing & unboxing
			factors.put(base, fn.apply(factors.get(base), expo));  // calculate fn
		});
	}

	/**
	 * Return the amount of prime factors this {@link LongFactorized} contains;
	 */
	public int size() {
		return this.factors.size();
	}

	@Override
	public String toString() {
		// convert factors to form of p1^k2*p2^k2...
		return this.stream()
				.map(factor -> factor.getKey() + "^" + factor.getValue())
				.collect(Collectors.joining("*"));
	}

	@Override
	public Iterator<Map.Entry<Long, Integer>> iterator() {
		return this.factors.entrySet().iterator();
	}

	public Stream<Map.Entry<Long, Integer>> stream() {
		return this.factors.entrySet().stream();
	}

	public Stream<Integer> expoStream() {
		return this.factors.values().stream();
	}

	/**
	 * Creates a new {@link LongFactorized} representing the given integer.<br />
	 * {@code n} must be positive, otherwise {@code null} is returned.
	 */
	public static LongFactorized of(long n) {
		if (n <= 0) return null;
		return new LongFactorized(n);
	}

	/**
	 * Create a new {@link LongFactorized} to represent the <i>least common multiple</i>
	 * of {@code a} and {@code b} without changing the contents of them.
	 */
	public static LongFactorized newLCM(LongFactorized a, LongFactorized b) {
		Objects.requireNonNull(a);
		Objects.requireNonNull(b);
		LongFactorized result = new LongFactorized(a);
		result.lcm(b);
		return result;
	}

	/**
	 * Create a new {@link LongFactorized} to represent the multiplication
	 * of {@code a} and {@code b} without changing the contents of them.
	 */
	public static LongFactorized newMult(LongFactorized a, LongFactorized b) {
		Objects.requireNonNull(a);
		Objects.requireNonNull(b);
		LongFactorized result = new LongFactorized(a);
		result.mult(b);
		return result;
	}

	/**
	 * Create a new {@link LongFactorized} to represent the division of {@code a} and
	 * {@code b} without changing the contents of them.<br />
	 * If {@code a} is not dividable by {@code b}, {@code null} is returned.
	 */
	public static LongFactorized divide(LongFactorized a, LongFactorized b) {
		Objects.requireNonNull(a);
		Objects.requireNonNull(b);

		TreeMap<Long, Integer> divided = new TreeMap<>(a.factors);
		for (Map.Entry<Long, Integer> factor : b) {
			Long base = factor.getKey();
			int expo = factor.getValue();
			if (!divided.containsKey(base) || expo > divided.get(base)) return null;
			divided.put(base, divided.get(base) - expo);
		}

		return new LongFactorized(divided);
	}
}
