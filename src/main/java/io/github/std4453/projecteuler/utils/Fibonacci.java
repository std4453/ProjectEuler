package io.github.std4453.projecteuler.utils;

/**
 *
 */
public class Fibonacci {
	// fib(n) = lambda * (phi ^ n - psi ^ n)
	private static final double PHI_DOUBLE = (1 + Math.sqrt(5)) / 2;
	private static final double PSI_DOUBLE = (1 - Math.sqrt(5)) / 2;
	private static final double LAMBDA_DOUBLE = 1 / Math.sqrt(5);
	private static final double ONE_DIVIDE_LAMBDA_DOUBLE = Math.sqrt(5);
	private static final double LN_PHI_DOUBLE = Math.log(PHI_DOUBLE);
	private static final double EPS = 1e-10;

	// so that fib(1) = 1, fib(2) = 2
	public static final int DEFAULT_OFFSET = 1;

	/**
	 * Get the (n + offset)th fibonacci number starting from
	 * {@code fib(0) = 0, fib(1) = 1}.<br />
	 * If {@code n + offset < 0}, no exception would be thrown.<br />
	 * This method takes {@code O(1)} time.<br />
	 * This method uses the closed-form formula of the fibonacci sequence and should be
	 * used in circumstances where high calculation speed and relatively low
	 * precision are required.
	 */
	public static double fibExp(int n, int offset) {
		return impl_fibExp(n + offset);
	}

	/**
	 * Get the nth fibonacci number starting from {@code fib(0) = 1, fib(1) = 1}.<br />
	 * If {@code n + offset < 0}, no exception would be thrown.<br />
	 * This method takes {@code O(1)} time.<br />
	 * This method uses the closed-form formula of the fibonacci sequence and should be
	 * used in circumstances where high calculation speed and relatively low
	 * precision are required.
	 */
	public static double fibExp(int n) {
		return fibExp(n, DEFAULT_OFFSET);
	}

	private static double impl_fibExp(int n) {
		return LAMBDA_DOUBLE * (Math.pow(PHI_DOUBLE, n) - Math.pow(PSI_DOUBLE, n));
	}

	/**
	 * Get the (n + offset)th fibonacci number starting from
	 * {@code fib(0) = 0, fib(1) = 1}.<br />
	 * If {@code strict} is {@code true}, then an {@link IllegalArgumentException}
	 * would be thrown on invalid input - i.e. {@code n + offset < 0} or
	 * {@code n + offset >= 47}, since {@code fib(47)} exceeds {@link
	 * Integer#MAX_VALUE}.<br />
	 * If {@code strict} if {@code false}, then:
	 * <ul>
	 * <li>If {@code n + offset < 0}, {@code 0} will be returned.</li>
	 * <li>If {@code n + offset >= 47}, the last 32 bits of {@code fib(n + offset)} will
	 * be returned.</li>
	 * </ul>
	 * This method takes {@code O(n + offset)} time.<br />
	 * This method returns the exact {@code int} value of {@code fib(n + offset)} by
	 * using the recursive formula of the fibonacci sequence and should therefore be
	 * used in circumstances where high precision and relatively lower calculation
	 * speed is required.
	 */
	public static int fibIterInt(int n, int offset, boolean strict) {
		return impl_fibIterInt(n + offset, strict);
	}

	/**
	 * Get the (n + offset)th fibonacci number starting from
	 * {@code fib(0) = 0, fib(1) = 1}.<br />
	 * An {@link IllegalArgumentException}
	 * would be thrown on invalid input - i.e. {@code n + offset < 0} or
	 * {@code n + offset >= 47}, since {@code fib(47)} exceeds {@link
	 * Integer#MAX_VALUE}.<br />
	 * {@code fibIterInt(n, offset)} is equivalent to {@code fibIterInt(n, offset,
	 * true)}.
	 * <br />This method takes {@code O(n)} time.<br />
	 * This method returns the exact {@code int} value of {@code fib(n + offset)} by
	 * using the recursive formula of the fibonacci sequence and should therefore be
	 * used in circumstances where high precision and relatively lower calculation
	 * speed is required.
	 */
	public static int fibIterInt(int n, int offset) {
		return fibIterInt(n, offset, true);
	}

	/**
	 * Get the nth fibonacci number starting from
	 * {@code fib(0) = 1, fib(1) = 1}.<br />
	 * If {@code strict} is {@code true}, then an {@link IllegalArgumentException}
	 * would be thrown on invalid input - i.e. {@code n < 0} or {@code n >= 46}, where
	 * {@code fib(46)} exceeds {@link Integer#MAX_VALUE}.<br />
	 * If {@code strict} if {@code false}, then:
	 * <ul>
	 * <li>If {@code n < 0}, {@code 0} will be returned.</li>
	 * <li>If {@code n >= 46}, the last 32 bits of {@code fib(n)} will
	 * be returned.</li>
	 * </ul>
	 * This method takes {@code O(n)} time.<br />
	 * This method returns the exact {@code int} value of {@code fib(n + offset)} by
	 * using the recursive formula of the fibonacci sequence and should therefore be
	 * used in circumstances where high precision and relatively lower calculation
	 * speed is required.
	 */
	public static int fibIterInt(int n, boolean strict) {
		return fibIterInt(n, DEFAULT_OFFSET, strict);
	}

	/**
	 * Get the nth fibonacci number starting from
	 * {@code fib(0) = 1, fib(1) = 1}.<br />
	 * An {@link IllegalArgumentException}
	 * would be thrown on invalid input - i.e. {@code n < 0} or {@code n >= 46}, where
	 * {@code fib(46)} exceeds {@link Integer#MAX_VALUE}.<br />
	 * This method takes {@code O(n)} time.<br />
	 * This method returns the exact {@code int} value of {@code fib(n + offset)} by
	 * using the recursive formula of the fibonacci sequence and should therefore be
	 * used in circumstances where high precision and relatively lower calculation
	 * speed is required.
	 */
	public static int fibIterInt(int n) {
		return fibIterInt(n, true);
	}

	private static int impl_fibIterInt(int n, boolean strict) {
		// special cases
		if (strict) {
			if (n < 0)
				throw new IllegalArgumentException("fib(n) has no definition!");
			if (n >= 47)
				throw new IllegalArgumentException("fib(47) exceeds Integer.MAX_VALUE!");
		}
		if (n == 0) return 0;
		if (n <= 2) return 1;

		// iterate
		int a = 1, b = 1;
		for (n -= 2; n > 0; --n) {
			int c = a + b;
			a = b;
			b = c;
		}
		return b;
	}

	/**
	 * Get the (n + offset)th fibonacci number starting from
	 * {@code fib(0) = 0, fib(1) = 1}.<br />
	 * If {@code strict} is {@code true}, then an {@link IllegalArgumentException}
	 * would be thrown on invalid input - i.e. {@code n + offset < 0} or
	 * {@code n + offset >= 93}, since {@code fib(93)} exceeds {@link
	 * Long#MAX_VALUE}.<br />
	 * If {@code strict} if {@code false}, then:
	 * <ul>
	 * <li>If {@code n + offset < 0}, {@code 0} will be returned.</li>
	 * <li>If {@code n + offset >= 93}, the last 64 bits of {@code fib(n + offset)} will
	 * be returned.</li>
	 * </ul>
	 * This method takes {@code O(n + offset)} time.<br />
	 * This method returns the exact {@code long} value of {@code fib(n + offset)} by
	 * using the recursive formula of the fibonacci sequence and should therefore be
	 * used in circumstances where high precision and relatively lower calculation
	 * speed is required.
	 */
	public static long fibIterLong(int n, int offset, boolean strict) {
		return impl_fibIterLong(n + offset, strict);
	}

	/**
	 * Get the (n + offset)th fibonacci number starting from
	 * {@code fib(0) = 0, fib(1) = 1}.<br />
	 * An {@link IllegalArgumentException}
	 * would be thrown on invalid input - i.e. {@code n + offset < 0} or
	 * {@code n + offset >= 93}, since {@code fib(93)} exceeds {@link
	 * Integer#MAX_VALUE}.<br />
	 * {@code fibIterInt(n, offset)} is equivalent to {@code fibIterInt(n, offset,
	 * true)}.
	 * <br />This method takes {@code O(n)} time.<br />
	 * This method returns the exact {@code long} value of {@code fib(n + offset)} by
	 * using the recursive formula of the fibonacci sequence and should therefore be
	 * used in circumstances where high precision and relatively lower calculation
	 * speed is required.
	 */
	public static long fibIterLong(int n, int offset) {
		return fibIterLong(n, offset, true);
	}

	/**
	 * Get the nth fibonacci number starting from
	 * {@code fib(0) = 1, fib(1) = 1}.<br />
	 * If {@code strict} is {@code true}, then an {@link IllegalArgumentException}
	 * would be thrown on invalid input - i.e. {@code n < 0} or {@code n >= 92}, where
	 * {@code fib(92)} exceeds {@link Integer#MAX_VALUE}.<br />
	 * If {@code strict} if {@code false}, then:
	 * <ul>
	 * <li>If {@code n < 0}, {@code 0} will be returned.</li>
	 * <li>If {@code n >= 92}, the last 64 bits of {@code fib(n)} will
	 * be returned.</li>
	 * </ul>
	 * This method takes {@code O(n)} time.<br />
	 * This method returns the exact {@code long} value of {@code fib(n + offset)} by
	 * using the recursive formula of the fibonacci sequence and should therefore be
	 * used in circumstances where high precision and relatively lower calculation
	 * speed is required.
	 */
	public static long fibIterLong(int n, boolean strict) {
		return fibIterLong(n, DEFAULT_OFFSET, strict);
	}

	/**
	 * Get the nth fibonacci number starting from
	 * {@code fib(0) = 1, fib(1) = 1}.<br />
	 * An {@link IllegalArgumentException}
	 * would be thrown on invalid input - i.e. {@code n < 0} or {@code n >= 92}, where
	 * {@code fib(92)} exceeds {@link Integer#MAX_VALUE}.<br />
	 * This method takes {@code O(n)} time.<br />
	 * This method returns the exact {@code int} value of {@code fib(n + offset)} by
	 * using the recursive formula of the fibonacci sequence and should therefore be
	 * used in circumstances where high precision and relatively lower calculation
	 * speed is required.
	 */
	public static long fibIterLong(int n) {
		return fibIterLong(n, true);
	}

	private static long impl_fibIterLong(int n, boolean strict) {
		// special cases
		if (strict) {
			if (n < 0)
				throw new IllegalArgumentException("fib(n) has no definition!");
			if (n >= 93)
				throw new IllegalArgumentException("fib(93) exceeds Long.MAX_VALUE!");
		}
		if (n == 0) return 0;
		if (n <= 2) return 1;

		// iterate
		long a = 1, b = 1;
		for (n -= 2; n > 0; --n) {
			long c = a + b;
			a = b;
			b = c;
		}
		return b;
	}

	/**
	 * Generate an int array containing {@code fib(offset)} to {@code fib(max + offset)},
	 * where {@code fib(0) = 0, fib(1) = 1}.<br />
	 * If {@code offset < 0} or {@code max < 0} or {@code max + offset >= 47}, an
	 * {@link IllegalArgumentException} would be thrown.<br />
	 * Note that the returned {@code int[]} has length {@code max + 1}.<br />
	 * This method takes {@code O(max + offset)} time, and should be used to generate
	 * an sequence of fibonacci numbers instead of using {@link #fibIterInt(int, int)}.
	 */
	public static int[] fibRangeInt(int max, int offset) {
		if (offset < 0 || max < 0)
			throw new IllegalArgumentException("Negative index not allowed!");
		if (offset + max >= 47)
			throw new IllegalArgumentException("fib(47) exceeds Integer.MAX_VALUE!");
		return impl_fibRangeInt(max + offset, offset);
	}

	/**
	 * Generate an int array containing {@code fib(0)} to {@code fib(max)},
	 * where {@code fib(0) = 0, fib(1) = 1}.<br />
	 * If {@code max < 0} or {@code max >= 46}, an
	 * {@link IllegalArgumentException} would be thrown.<br />
	 * Note that the returned {@code int[]} has length {@code max + 1}.<br />
	 * This method takes {@code O(max)} time, and should be used to generate
	 * an sequence of fibonacci numbers instead of using {@link #fibIterInt(int)}.
	 */
	public static int[] fibRangeInt(int max) {
		if (max >= 46)
			throw new IllegalArgumentException("fib(46) exceeds Integer.MAX_VALUE!");
		return fibRangeInt(max, DEFAULT_OFFSET);
	}

	private static int[] impl_fibRangeInt(int max, int offset) {
		int[] ans = new int[max - offset + 1];
		if (offset <= 0) ans[0 - offset] = 0;
		if (offset <= 1) ans[1 - offset] = 1;
		if (offset <= 2) ans[2 - offset] = 1;
		int a = 1, b = 1;
		for (int i = 3; i <= max; ++i) {
			int c = a + b;
			if (i >= offset) ans[i - offset] = c;
			a = b;
			b = c;
		}
		return ans;
	}

	/**
	 * Generate an long array containing {@code fib(offset)} to {@code fib(max + offset)},
	 * where {@code fib(0) = 0, fib(1) = 1}.<br />
	 * If {@code offset < 0} or {@code max < 0} or {@code max + offset >= 93}, an
	 * {@link IllegalArgumentException} would be thrown.<br />
	 * Note that the returned {@code long[]} has length {@code max + 1}.<br />
	 * This method takes {@code O(max + offset)} time, and should be used to generate
	 * an sequence of fibonacci numbers instead of using {@link #fibIterLong(int, int)}.
	 */
	public static long[] fibRangeLong(int max, int offset) {
		if (offset < 0 || max < 0)
			throw new IllegalArgumentException("Negative index not allowed!");
		if (offset + max >= 93)
			throw new IllegalArgumentException("fib(93) exceeds Long.MAX_VALUE!");
		return impl_fibRangeLong(max + offset, offset);
	}

	/**
	 * Generate an long array containing {@code fib(0)} to {@code fib(max)},
	 * where {@code fib(0) = 0, fib(1) = 1}.<br />
	 * If {@code max < 0} or {@code max >= 92}, an
	 * {@link IllegalArgumentException} would be thrown.<br />
	 * Note that the returned {@code int[]} has length {@code max + 1}.<br />
	 * This method takes {@code O(max)} time, and should be used to generate
	 * an sequence of fibonacci numbers instead of using {@link #fibIterLong(int)}.
	 */
	public static long[] fibRangeLong(int max) {
		if (max >= 92)
			throw new IllegalArgumentException("fib(92) exceeds Long.MAX_VALUE!");
		return fibRangeLong(max, DEFAULT_OFFSET);
	}

	private static long[] impl_fibRangeLong(int max, int offset) {
		long[] ans = new long[max - offset + 1];
		if (offset <= 0) ans[0 - offset] = 0;
		if (offset <= 1) ans[1 - offset] = 1;
		if (offset <= 2) ans[2 - offset] = 1;
		long a = 1, b = 1;
		for (int i = 3; i <= max; ++i) {
			long c = a + b;
			if (i >= offset) ans[i - offset] = c;
			a = b;
			b = c;
		}
		return ans;
	}

	/**
	 * Return maximum integer {@code k} that {@code fibIterLong(k, offset) <= n}.<br />
	 * That is to say, {@code fibIterLong(k, offset) <= n < fibIterLong(k + 1, offset)}.
	 * <br />If {@code n} is smaller than 1, {@code 0} is returned.<br />
	 * The return value is verified to be correct for every {@code (double) i}
	 * that integer {@code i} is in {@code [0, Integer.MAX_VALUE]}. For larger
	 * values, the return value of this method should be the correct result +/- 1.<br />
	 * This method takes O(1) time.
	 */
	public static int getMaxFibIndex(double n, int offset) {
		if (n + EPS < 1) return 0;

		// fib(k) = lambda * (phi^k - psi^k), therefore there is always:
		// n - lambda * abs(psi^k) < lambda * phi^k < n + lambda * abs(psi^k)
		// and:
		// n / lambda - abs(psi^k) < phi^k < n / lambda + abs(psi^k)
		// Since abs(psi) < 1, we have abs(psi^k) <= abs(psi) for all positive integer k.
		// So, given that psi < 0, we have:
		// n / lambda + psi < phi^k < n / lambda - psi
		// log(phi, n / lambda + psi) < k < log(phi, n / lambda - psi)
		// Since we want largest k, we take floor(log(phi, n / lambda - psi) as our
		// final value.

		return (int) Math.round(Math.floor(
				Math.log(ONE_DIVIDE_LAMBDA_DOUBLE * n - PSI_DOUBLE) /
						LN_PHI_DOUBLE - offset + EPS));
	}

	/**
	 * Return maximum integer {@code k} that {@code fibIterLong(k) <= n}.<br />
	 * That is to say, {@code fibIterLong(k) <= n < fibIterLong(k + 1)}.
	 * <br />If {@code n} is smaller than 1, {@code 0} is returned.<br />
	 * The return value is verified to be correct for every {@code (double) i}
	 * that integer {@code i} is in {@code [0, Integer.MAX_VALUE]}. For larger
	 * values, the return value of this method should be the correct result +/- 1.<br />
	 * This method takes O(1) time.
	 */
	public static int getMaxFibIndex(double n) {
		return getMaxFibIndex(n, DEFAULT_OFFSET);
	}
}
