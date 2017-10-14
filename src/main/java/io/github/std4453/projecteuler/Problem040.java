package io.github.std4453.projecteuler;

import io.github.std4453.projecteuler.utils.IntArrayList;

import java.util.stream.IntStream;

import static io.github.std4453.projecteuler.utils.MathsHelper.pow;

/**
 * Problem #40: Champernowne's constant<br />
 * An irrational decimal fraction is created by concatenating the positive integers:
 * <br />{@code 0.123456789101112131415161718192021...}<br />
 * It can be seen that the 12th digit of the fractional part is 1.<br />
 * If {@code d_n} represents the nth digit of the fractional part, find the value of the
 * following expression.<br />
 * {@code d_1 × d_10 × d_100 × d_1000 × d_10000 × d_100000 × d_1000000}
 */
public class Problem040 {
	private static final int[] INDICES = {1, 10, 100, 1000, 10000, 100000, 1000000};

	// This is a very easy problem wrapped up with some complex ideas.
	// d_n is equal to: Write all positive integers in a row and find the nth digit -
	// there's nothing to do with irrationality.

	// All n-digit numbers have altogether 9 * 10^(n - 1) * n digits.
	// All 1-digit to n-digit numbers have altogether (10^n * (9n - 1) + 1) / 9 digits
	// Define phi(k) as the maximum number n that (10^n * (9n - 1) + 1) / 9 < k
	// Intuitively, d_k belongs to a number with phi(k) + 1 digits.
	// Define psi(k) as (10^phi(k) * (9 * phi(k) - 1) + 1) / 9.
	// Define a \ b as the integral division, as 4 \ 3 = 1, and 6 \ 3 = 2.
	// So the (phi(k) + 1)-digit number that d_k belongs to the
	// ((k - psi(k) - 1) \ (phi(k) + 1) + 1)th (phi(k) + 1)-digit number.
	// So d_k belongs to 10^phi(k) + (k - psi(k) - 1) \ (phi(k) + 1) and d_k is its
	// (k - psi(k) - (k - psi(k) - 1) \ (phi(k) + 1) * phi(k) + 1)th digit.
	// Let gamma(k) = (k - psi(k) - 1) \ (phi(k) + 1)
	// Let miu(k) = 10^phi(k) + gamma(k) and
	// theta(k) = phi(k) + 1 - (k - psi(k) - gamma(k) * (phi(k) + 1))
	// d_k = miu(k) % 10^(theta(k) + 1) / 10^theta(k)

	private static int[] buildPsiCache(int max) {
		IntArrayList psis = new IntArrayList();
		for (int n = 0, psi = 0; psi < max; ++n,
				psi = (pow(10, n) * (9 * n - 1) + 1) / 9)
			psis.add(psi);
		return psis.toIntArray();
	}

	private static int phi(int k, int[] psiCache) {
		for (int n = 0; n < psiCache.length - 1; ++n)
			if (psiCache[n + 1] >= k) return n;  // since psi(k) < k
		return psiCache.length - 1;
	}

	private static int gamma(int k, int phi, int[] psiCache) {
		return (k - psiCache[phi] - 1) / (phi + 1);
	}

	private static int miu(int k, int phi, int gamma, int[] psiCache) {
		return pow(10, phi) + gamma;
	}

	private static int theta(int k, int phi, int gamma, int[] psiCache) {
		return phi + 1 - (k - psiCache[phi] - gamma * (phi + 1));
	}

	private static int d(int k, int[] psiCache) {
		int phi = phi(k, psiCache);
		int gamma = gamma(k, phi, psiCache);
		int miu = miu(k, phi, gamma, psiCache);
		int theta = theta(k, phi, gamma, psiCache);
		int pow10 = pow(10, theta);
		return miu % (pow10 * 10) / pow10;
	}

	public static void main(String[] args) {
		int maxK = IntStream.of(INDICES).max().orElse(0);  // get max k
		int[] psiCache = buildPsiCache(maxK);  // cache psi
		IntStream.of(INDICES)  // get k
				.map(index -> d(index, psiCache))  // map to d(k)
				.reduce((a, b) -> a * b)  // multiply them together
				.ifPresent(System.out::println);  // print the result

		// Answer: 210
		// Complexity: O(INDICES.length * log(max(INDICES)))

		// TODO: add homework
	}
}
