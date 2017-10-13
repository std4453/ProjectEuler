package io.github.std4453.projecteuler;

import io.github.std4453.projecteuler.utils.IntArrayList;
import io.github.std4453.projecteuler.utils.Primes;

import java.util.Set;
import java.util.TreeSet;

import static io.github.std4453.projecteuler.utils.StreamUtils.limitUntil;

/**
 * Euler discovered the remarkable quadratic formula:<br />
 * {@code n^2 + n + 41}<br />
 * It turns out that the formula will produce 40 primes for the consecutive integer
 * values {@code 0 <= n <= 39}. However, when {@code n = 40},
 * {@code 40^2 + 40 + 41 = 40 * (40+1) + 41} is divisible by 41, and certainly when
 * {@code n = 41}, {@code 41^2 + 41 + 41} is clearly divisible by 41.<br />
 * The incredible formula {@code n^2 − 79 * n + 1601} was discovered, which produces 80
 * primes for the consecutive values {@code 0 <= n <= 79}. The product of the
 * coefficients, −79 and 1601, is −126479.<br />
 * Considering quadratics of the form:<br />
 * {@code n^2 + a * n + b}, where {@code |a| < 1000} and {@code |b| <= 1000}<br />
 * where {@code |n|} is the modulus/absolute value of {@code n}<br />
 * e.g. {@code |11| = 11} and {@code |−4| = 4}<br />
 * Find the product of the coefficients, {@code a} and {@code b}, for the quadratic
 * expression that produces the maximum number of primes for consecutive values of
 * {@code n}, starting with {@code n = 0}.
 */
public class Problem027 {
	private static final int MAX = 1000;
	private static final int MAX_PRIMES = 79;

	// Since for n = 0, n^2 + a * n + b = b is prime, b must be prime and b > 0.
	// If b = 2, 2^2 + a * 2 + b is prime therefore a = -2, however then 1^2 + a * 1 +
	// b is 1 and not prime, therefore a and bb are odd.
	// We can make more constraints on the value of a:
	// 1 + a + b >= 3 so a >= 2 - b
	// 4 + 2a + b >= 3 so a >= -(b + 1) / 2
	// Bearing this in mind, we enumerate all the possible pairs of (a, b) to find the
	// required answer.

	// We use the following way to speed up checking whether a number is prime:
	// Generate and store in a TreeSet all primes under an estimated maximum that
	// n^2 + a * n + b can get. (Since n^2 - 79n + 1601 can generate 80 primes, we take
	// that for |a| < 1000 & |b| <= 1000 there can be at most 79 primes, therefore the
	// maximum will be about 79^2 = 6241.)
	// And set maxPrime to be maximum prime generated initially.
	// On our way enumerating all the possible pairs of (a, b), we will get many values
	// of n^2 + a * n + b, for every one of them, say k, we follow the following steps:
	// 1. If k < 2, k is certainly non-prime.
	// 2. If k <= maxPrime, whether k is prime is equal to whether k is in primes.
	// 3. If k > maxPrime, generate all primes equal to or under k, store them in primes,
	// and update maxPrime. Whether k is prime is equal to whether k is in primes.

	private static Set<Integer> primes = new TreeSet<>();
	private static int maxPrime;

	// max here is inclusive
	private static void generatePrimes(int max) {
		// max in sievePrimesInt() is exclusive
		IntArrayList generated = Primes.sievePrimesInt(max + 1);
		primes.clear();
		primes.addAll(generated);
		maxPrime = generated.getInt(generated.size() - 1);
	}

	private static boolean isPrime(int k) {
		if (k < 2) return false;
		if (k <= maxPrime) return primes.contains(k);

		// k > maxPrime
		// We now generate all primes equal to or under k, and if k is non-prime,
		// maxPrime would be less than k, therefore we don't check k <= maxPrime
		// after generatePrimes()
		generatePrimes(k);
		return primes.contains(k);
	}

	private static void initialGeneratePrimes() {
		generatePrimes(MAX_PRIMES * MAX_PRIMES);
	}

	// get the number of primes that n^2 + a * n + b can generate with consecutive n
	private static int numPrimes(int a, int b) {
		int n;
		for (n = 1; isPrime((n + a) * n + b); ++n) ;  // skip n = 0 since b is prime
		return n;
	}

	// return {numPrimes(a, b), a * b} for (a, b) with given b and maximum numPrimes(a, b)
	private static int[] iterateA(int b) {
		int maxNumPrimes = -1, maxAMultB = 0;
		int start = Math.min(2 - b, -(b + 1) / 2);  // find upper bound of a
		for (int a = start; a < MAX; a += 2) {  // iterate through possible a
			int numPrimes = numPrimes(a, b);
			if (numPrimes > maxNumPrimes) {
				maxNumPrimes = numPrimes;
				maxAMultB = a * b;
			}
		}
		return new int[]{maxNumPrimes, maxAMultB};
	}

	public static void main(String[] args) {
		initialGeneratePrimes();
		limitUntil(Primes.intPrimesStream(),
				n -> n > MAX)
				.skip(1)  // skip 2
				.mapToObj(Problem027::iterateA)
				.reduce((a1, a2) -> a1[0] > a2[0] ? a1 : a2)
				.map(a -> a[1])
				.ifPresent(System.out::println);

		// Answer: -59231
		// Complexity: O(n^2)

		// TODO: add homework
	}
}
