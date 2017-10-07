package io.github.std4453.projecteuler.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static io.github.std4453.projecteuler.utils.StreamUtils.limitUntil;

/**
 * {@code IntFactorized} represent an prime-factorized integer containing a sorted list
 * of prime factors, while providing several handy methods.
 */
public class IntFactorized implements Iterable<Map.Entry<Integer, Integer>> {
	private TreeMap<Integer, Integer> factors;

	private IntFactorized() {
		this.factors = new TreeMap<>();
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
				// Here the factorization algorithm behaves similar to the answer of
				// Problem #3. Therefore see comments in Problem003 for more details.

				int sqrt = (int) Math.round(Math.sqrt(num));  // calculate max
				// NOTE THE "!" UNDER HERE!
				if (!limitUntil(NumberTheory.intPrimesStream(),  // all primes
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
		return this.stream().flatMapToInt(factor -> {  // map factor to multipliers
			int key = factor.getKey();
			return IntStream.generate(() -> key).limit(factor.getValue());
		}).reduce(1, (a, b) -> a * b);  // multiple them
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
	 * Create a new {@link IntFactorized} to represent the <i>least common multiple</i>
	 * of {@code a} and {@code b} without changing the contents of them.
	 */
	public static IntFactorized newLCM(IntFactorized a, IntFactorized b) {
		IntFactorized result = new IntFactorized(a);
		result.lcm(b);
		return result;
	}
}
