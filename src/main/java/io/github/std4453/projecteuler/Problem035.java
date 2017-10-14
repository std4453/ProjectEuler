package io.github.std4453.projecteuler;

import io.github.std4453.projecteuler.utils.Primes;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;

import static io.github.std4453.projecteuler.utils.MathsHelper.pow;

/**
 * Problem #35: Circular primes<br />
 * The number, {@code 197}, is called a circular prime because all rotations of the
 * digits: {@code 197, 971}, and {@code 719}, are themselves prime.<br />
 * There are thirteen such primes below 100: {@code 2, 3, 5, 7, 11, 13, 17, 31, 37, 71,
 * 73, 79}, and {@code 97}.<br />
 * How many circular primes are there below one million?
 */
public class Problem035 {
	private static final int MAX = 1000000;
	private static final double EPS = 1e-6;

	private static Set<Integer> primes = new TreeSet<>();

	// generate all primes under MAX
	private static void generatePrimes() {
		primes.addAll(Primes.sievePrimesInt(MAX));
	}

	private static boolean isCircularPrime(int n) {
		// num of digits
		int digits = (int) Math.round(Math.floor(Math.log10(n) + EPS)) + 1;
		int pow10 = pow(10, digits - 1);  // maximum power of 10 < n
		if (!primes.contains(n)) return false;  // not circular prime
		int nn = n;  // copy of n
		do {  // circulate n
			n = (n % pow10) * 10 + (n / pow10);
			if (!primes.contains(n)) return false;  // not circular prime
		} while (n != nn);  // stop when a cycle has formed
		return true;  // is circular prime
	}

	public static void main(String[] args) {
		generatePrimes();  // generate all primes under MAX
		System.out.println(IntStream.range(1, MAX)
				.filter(Problem035::isCircularPrime)
				.count());

		// Answer: 55
		// Complexity: O(n * log(n)^2)

		// TODO: add homework
	}
}
