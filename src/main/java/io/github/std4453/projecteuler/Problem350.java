package io.github.std4453.projecteuler;

import io.github.std4453.projecteuler.utils.Primes;

import java.util.TreeMap;
import java.util.stream.LongStream;

import static io.github.std4453.projecteuler.utils.MathsHelper.pow;

/**
 *
 */
public class Problem350 {
	private static final long L = pow(10L, 6L);
	private static final long M = pow(10L, 12L);
	private static final long N = pow(10L, 18L);

	//	private static final long L = 10;
	//	private static final long M = 100;
	//	private static final long N = 3;

	private static final long MOD = pow(101L, 4L);

	private static long modMult(long a, long b) {
		return a * b % MOD;
	}

	private static long modAdd(long a, long b) {
		return (a + b) % MOD;
	}

	private static long modAbs(long n) {
		return n < 0 ? n % MOD + MOD : n % MOD;
	}

	public static void main(String[] args) {
		long maxTimes = M / L;

		int maxK = (int) Math.round(Math.log(maxTimes) / Math.log(2));
		long[] phiCache = buildPhiCache(maxK);
		System.out.println("Phi cache built.");

		TreeMap<Integer, Integer>[] factorized = buildFactorizedCache((int) maxTimes);
		System.out.println("Factorized cache built.");

		LongStream.rangeClosed(1, maxTimes)
				.map(i -> {
					long coeff = (M / i - L + 1) % MOD;
					long theta = factorized[(int) i]
							.values()
							.stream()
							.mapToLong(k -> phiCache[k])
							.reduce(Problem350::modMult)
							.orElse(phiCache[0]);
					return modMult(coeff, theta);
				}).reduce(Problem350::modAdd)
				.ifPresent(System.out::println);

		// Answer: 84664213
		// TODO: add explanations
	}

	@SuppressWarnings("unchecked")
	private static TreeMap<Integer, Integer>[] buildFactorizedCache(int _max) {
		final int max = _max + 1;

		int[] primes = Primes.sievePrimesInt(max).toIntArray();
		TreeMap<Integer, Integer>[] factorized = new TreeMap[max];
		for (int i = 0; i < max; ++i) factorized[i] = new TreeMap<>();

		for (int i = 0; i < primes.length; ++i) {
			int prime = primes[i];
			for (int n = prime, j = 1; n < max; ++j, n += prime) {
				TreeMap<Integer, Integer> parent = factorized[j];
				if (parent.containsKey(i))
					factorized[n].put(i, parent.get(i) + 1);
				else factorized[n].put(i, 1);
			}
		}

		return factorized;
	}

	private static long[] buildPhiCache(int maxK) {
		long[] cache = new long[maxK + 1];

		long[] powNCache = buildPowNCache(maxK + 1);
		System.out.println("PowN cache built.");
		cache[0] = 1;
		for (int i = 1; i <= maxK; ++i)
			cache[i] = modAbs(powNCache[i + 1] - 2 * powNCache[i] + powNCache[i - 1]);

		return cache;
	}

	private static long[] buildPowNCache(int maxBase) {
		long[] cache = new long[maxBase + 1];

		boolean[] nBase2Cache = buildNBase2Cache();
		System.out.println("NBase2 cache built.");
		cache[1] = 1;
		for (int i = 2; i <= maxBase; ++i) cache[i] = quickPowN(nBase2Cache, i);

		return cache;
	}

	private static boolean[] buildNBase2Cache() {
		int digits = (int) Math.round(Math.log(N) / Math.log(2));
		boolean[] cache = new boolean[digits + 1];
		for (int i = 0; i < digits; ++i) cache[i] = (N & (1L << i)) != 0;
		return cache;
	}

	private static long quickPowN(boolean[] nBase2Cache, int base) {
		long[] basePow2 = new long[nBase2Cache.length];
		long last = basePow2[0] = base;
		for (int i = 1; i < basePow2.length; ++i)
			last = basePow2[i] = modMult(last, last);
		long ans = 1;
		for (int i = 0; i < basePow2.length; ++i)
			if (nBase2Cache[i]) ans = modMult(ans, basePow2[i]);
		return ans;
	}
}
