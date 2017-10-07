package io.github.std4453.projecteuler;

import java.util.BitSet;

/**
 *
 */
public class Problem014 {
	private static final int MAX = 1000000;

	public static void main(String[] args) {
		int maxNumber = 0, maxLength = 0;
		BitSet bitSet = new BitSet(MAX * 10);
		for (int i = MAX - 1; i >= 1; --i)
			if (!bitSet.get(i)) {
				long k = i;
				int length = 0;
				while (k != 1) {
					if (k < MAX) bitSet.set((int) k);
					k = k % 2 == 0 ? (k >> 1) : (k * 3 + 1);
					++length;
				}
				if (length > maxLength) {
					maxLength = length;
					maxNumber = i;
				}
			}
		System.out.println(maxNumber);

		// Answer: 837799
		// TODO: add explanations
	}
}
