package io.github.std4453.projecteuler;

import io.github.std4453.projecteuler.utils.IntArrayList;

/**
 *
 */
public class Problem024 {
	private static final int NUMBERS = 10;
	private static final int INDEX = 1000000;

	private static int[] buildFacCache() {
		int[] facs = new int[NUMBERS];
		facs[1] = 1;
		for (int i = 2; i < NUMBERS; ++i)
			facs[i] = facs[i - 1] * i;
		return facs;
	}

	public static void main(String[] args) {
		int[] facs = buildFacCache();
		IntArrayList digits = new IntArrayList(NUMBERS);
		for (int i = 0; i < NUMBERS; ++i) digits.add(i);

		int index = INDEX - 1;  // convert from lingual ordinal to programmatic index
		StringBuilder ans = new StringBuilder();
		for (int i = NUMBERS - 1; i >= 1; --i) {
			int ind = index / facs[i];
			int n = digits.removeAt(ind);
			ans.append(n);
			index = index % facs[i];
		}
		ans.append(digits.getInt(0));

		System.out.println(ans.toString());

		// Answer: 2783915460
		// TODO: add explanations
	}
}
