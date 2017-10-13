package io.github.std4453.projecteuler;

import java.util.stream.IntStream;

/**
 *
 */
public class Problem301 {
	private static final int MAX_BITS = 30;

	public static void main(String[] args) {
		int[] phi = new int[MAX_BITS];
		for (int i = 0; i < MAX_BITS; ++i) {
			phi[i] = 1;
			for (int j = i - 2; j >= 0; --j) phi[i] += phi[j];
		}
		System.out.println(IntStream.of(phi).sum() + 1);

		// Answer: 2178309
		// TODO: add explanations
	}
}
