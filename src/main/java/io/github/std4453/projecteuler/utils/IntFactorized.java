package io.github.std4453.projecteuler.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.github.std4453.projecteuler.utils.MathsHelper.pow;
import static io.github.std4453.projecteuler.utils.StreamUtils.limitUntil;

/**
 * {@code IntFactorized} represent an prime-factorized integer containing a sorted list
 * of prime factors, while providing several handy methods.
 */
public class IntFactorized implements Iterable<Map.Entry<Integer, Integer>> {
	private TreeMap<Integer, Integer> factors;

	/**
	 * Constructor that uses the content of the given {@link Map} as
	 * {@code this.factors}. In order to accelerate further operations, the given
	 * {@link Map} is converted into a {@link TreeMap}.
	 */
	public IntFactorized(Map<Integer, Integer> factors) {
		this.factors = new TreeMap<>(factors);
	}

	private IntFactorized(TreeMap<Integer, Integer> factors) {
		this.factors = factors;
	}

	/**
	 * Constructor that uses the given {@link TreeMap} as {@code this.factors} directly.
	 * <br />This method should be called only internally.
	 */
	private IntFactorized() {
		this(new TreeMap<>());
	}

	public IntFactorized(int n) {
		this();

		this.factorize(n);
	}

	/**
	 * The copying constructor of {@link IntFactorized}.
	 */
	public IntFactorized(IntFactorized toCopy) {
		this();

		this.factors.putAll(toCopy.factors);
	}

	/**
	 * Factorize the given number and save the results in {@link #factors this.factors}.
	 */
	private void factorize(final int num) {
		if (num == 1) return; // avoid bug

		new Runnable() {
			// Because n is modified inside lambdas, we need a object instance to
			// contain it, therefore we construct a anonymous Runnable here
			private int n = num;

			@Override
			public void run() {
				// Here the factorization algorithm behaves similarly to the answer of
				// Problem #3. Therefore see comments in Problem003 for more details.

				int sqrt = (int) Math.round(Math.sqrt(num));  // calculate max
				// NOTE THE "!" UNDER HERE!
				if (!limitUntil(Primes.intPrimesStream(),  // all primes
						p -> p > sqrt)  // less than or equal to sqrt(num)
						.filter(p -> num % p == 0)  // that can divide num
						.peek(p -> {  // calculate exponent
							int expo = 0;
							while (n % p == 0) {
								n /= p;
								++expo;
							}
							IntFactorized.this.factors.put(p, expo);  // put result
						})
						.filter(p -> n == 1)  // if remaining 1
						.findFirst()  // terminate the stream
						.isPresent()) {  // otherwise the remaining n is the last factor
					IntFactorized.this.factors.put(n, 1);  // put it
				}
			}
		}.run();
	}

	/**
	 * Return the number represented by this {@link IntFactorized}.<br />
	 * Note that the number is dynamically calculated using the factors, therefore
	 * applications should cache the result if is to be used many times.
	 */
	public int getNumber() {
		return this.stream().mapToInt(factor ->  // map factor to power
				pow(factor.getKey(), factor.getValue()))
				.reduce(1, (a, b) -> a * b);  // multiply them
	}

	/**
	 * Return the number of divisors of the number represented by this
	 * {@link IntFactorized}.<br />
	 * Note that the number is dynamically calculated using the factors, therefore
	 * applications should cache the result if is to be used many times.
	 */
	public int getNumberOfDivisors() {
		// given exponents k1, k2 ... kn, there are (k1 + 1)(k2 + 1)...(kn + 1) divisors
		return this.factors.values().stream().mapToInt(n -> n + 1)
				.reduce(1, (a, b) -> a * b);  // multiply them
	}

	/**
	 * Return the sum of divisors of the number represented by this
	 * {@link IntFactorized}.<br />
	 * Note that the number is dynamically calculated using the factors, therefore
	 * applications should cache the result if is to be used many times.
	 */
	public int getSumOfDivisors() {
		// if n = p1^k1 * p2^k2 * ... * pn^kn, sum of its divisors would be
		// (1 + p1^1 + p1^2 + ... + p1^k1) * (1 + p2^1 + p2^2 + ... + p2^k2) *
		// ... * (1 + pn^1 + pn^2 + ... + p1^kn)
		// and that (1 + p1^1 + p1^2 + ... + p1^k1) = (p1^(k1 + 1) - 1) / (p1 - 1)
		return this.stream().mapToInt(factor ->
				(pow(factor.getKey(), factor.getValue() + 1) - 1) / (factor.getKey() - 1))
				.reduce(1, (a, b) -> a * b);
	}

	/**
	 * Update {@code this} to represent the <i>least common multiple</i> of {@code
	 * this} and the given {@link IntFactorized} {@code n}.<br />
	 * This method modifies the content of {@code this}.
	 *
	 * @see #merge(IntFactorized, BiFunction)
	 */
	public void lcm(IntFactorized n) {
		this.merge(n, (current, newComing) ->
				current == null ? newComing : Math.max(current, newComing));
	}

	/**
	 * Update {@code this} to represent {@link #getNumber() this.getNumber} {@code *}
	 * {@link #getNumber() n.getNumber()}.<br />
	 * This method modifies the content of {@code this}.
	 *
	 * @see #merge(IntFactorized, BiFunction)
	 */
	public void mult(IntFactorized n) {
		this.merge(n, (current, newComing) ->
				current == null ? newComing : current + newComing);
	}

	/**
	 * Merge a given non-null {@link IntFactorized} {@code n} into {@code this} using a
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
	private void merge(IntFactorized n, BiFunction<Integer, Integer, Integer> fn) {
		Objects.requireNonNull(n);
		Objects.requireNonNull(fn);
		TreeMap<Integer, Integer> factors = this.factors;  // shortcut
		n.stream().forEach(factor -> {  // traverse
			Integer base = factor.getKey();  // use Integer instead of int to avoid
			Integer expo = factor.getValue();  // unnecessary boxing & unboxing
			factors.put(base, fn.apply(factors.get(base), expo));  // calculate fn
		});
	}

	/**
	 * Return the amount of prime factors this {@link IntFactorized} contains;
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
	public Iterator<Map.Entry<Integer, Integer>> iterator() {
		return this.factors.entrySet().iterator();
	}

	public Stream<Map.Entry<Integer, Integer>> stream() {
		return this.factors.entrySet().stream();
	}

	/**
	 * Creates a new {@link IntFactorized} representing the given integer.<br />
	 * {@code n} must be positive, otherwise {@code null} is returned.
	 */
	public static IntFactorized of(int n) {
		if (n <= 0) return null;
		return new IntFactorized(n);
	}

	/**
	 * Create a new {@link IntFactorized} to represent the <i>least common multiple</i>
	 * of {@code a} and {@code b} without changing the contents of them.
	 */
	public static IntFactorized newLCM(IntFactorized a, IntFactorized b) {
		Objects.requireNonNull(a);
		Objects.requireNonNull(b);
		IntFactorized result = new IntFactorized(a);
		result.lcm(b);
		return result;
	}

	/**
	 * Create a new {@link IntFactorized} to represent the multiplication
	 * of {@code a} and {@code b} without changing the contents of them.
	 */
	public static IntFactorized newMult(IntFactorized a, IntFactorized b) {
		Objects.requireNonNull(a);
		Objects.requireNonNull(b);
		IntFactorized result = new IntFactorized(a);
		result.mult(b);
		return result;
	}

	/**
	 * Create a new {@link IntFactorized} to represent the division of {@code a} and
	 * {@code b} without changing the contents of them.<br />
	 * If {@code a} is not dividable by {@code b}, {@code null} is returned.
	 */
	public static IntFactorized divide(IntFactorized a, IntFactorized b) {
		Objects.requireNonNull(a);
		Objects.requireNonNull(b);

		TreeMap<Integer, Integer> divided = new TreeMap<>(a.factors);
		for (Map.Entry<Integer, Integer> factor : b) {
			Integer base = factor.getKey();
			int expo = factor.getValue();
			if (!divided.containsKey(base) || expo > divided.get(base)) return null;
			divided.put(base, divided.get(base) - expo);
		}

		return new IntFactorized(divided);
	}
}
