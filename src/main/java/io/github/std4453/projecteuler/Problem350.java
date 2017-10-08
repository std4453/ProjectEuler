package io.github.std4453.projecteuler;

import io.github.std4453.projecteuler.utils.Primes;

import java.util.Arrays;

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
		long start = System.currentTimeMillis();

		int maxTimes = (int) (M / L);

		long[] thetaCache = buildThetaCache(maxTimes);
		System.out.println("Theta cache built.");

		long sum = 0;
		for (int i = 1; i <= maxTimes; ++i)
			sum = modAdd(sum, modMult(modAbs(M / i - L + 1), thetaCache[i]));
		System.out.println(sum);

		long end = System.currentTimeMillis();
		System.out.printf("Time consumed: %dms\n", end - start);

		// Answer: 84664213
		// TODO: add explanations
	}

	private static long[] buildThetaCache(int max) {
		int maxK = (int) Math.round(Math.log(max) / Math.log(2));
		long[] phiCache = buildPhiCache(maxK);
		System.out.println("Phi cache built.");

		long[] cache = new long[max + 1];
		Arrays.fill(cache, phiCache[0]);
		int[] minPrimeFactor = new int[max + 1];
		int[] primes = Primes.sievePrimesInt(max + 1).toIntArray();
		for (int p : primes)
			for (int n = p, j = 1; n <= max; ++j, n += p) minPrimeFactor[n] = p;
		for (int i = 2; i <= max; ++i) {
			int j = i;
			int minP = minPrimeFactor[j];
			int expo = 0;
			for (; j % minP == 0; j /= minP) ++expo;
			cache[i] = modMult(modMult(cache[i], phiCache[expo]), cache[j]);
		}
		return cache;
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
