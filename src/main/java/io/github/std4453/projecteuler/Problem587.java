package io.github.std4453.projecteuler;

import java.util.stream.LongStream;

/**
 *
 */
public class Problem587 {
	private static final double PERCENTAGE = 0.1;

	private static double all() {
		return 1 - Math.PI / 4;
	}

	private static double s(long n) {
		double d = n * n + 4;
		double x = n * (n + 2 - 2 * Math.sqrt(n)) / d;
		double m = (2 - x) * x;
		double s1 = m / n;
		double cos = Math.sqrt(m);
		double sin = 1 - x;
		double theta = Math.asin(sin);
		return s1 - (theta - cos * sin) / 2;
	}

	public static void main(String[] args) {
		double all = all();
		double target = all * PERCENTAGE / 100;
		System.out.println(all);
		LongStream.iterate(1, n -> n + 1)
				.filter(n -> s(n * 2) < target)
				.findFirst()
				.ifPresent(System.out::println);

		// Answer: 2240
		// TODO: add explanations
	}
}
